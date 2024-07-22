package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import magentodemo.api.MagentoApi;
import utilities.RandomData;

@Data
@Accessors(fluent = true)
public class Account extends BaseScenario {
	private Customer customer;
	private String password;
	private transient boolean includeBillingAddress;
	private transient boolean includeShippingAddress;

	public Account withDefaults() {
		if (needsPopulation) {
			this.customer = new Customer().withMinDefaults();
			this.password = RandomData.getRandomPassword();
			if (includeBillingAddress) {
				this.customer.withAddress(new Address()
						.firstname(customer().firstname())
						.lastname(customer().lastname())
						.withDefaults().defaultBilling(true));
			}
			if (includeShippingAddress) {
				this.customer.withAddress(new Address()
						.firstname(customer().firstname())
						.lastname(customer().lastname())
						.withDefaults().defaultShipping(true));
			}
			needsPopulation = false;
		}
		return this;
	}

	public Account create() {
		withDefaults();
		MagentoApi.customer().create(this);
		return this;
	}

	public AuthBody getAuthBody() {
		AuthBody authBody = new AuthBody();
		authBody.username(customer.email());
		authBody.password(password);
		return authBody;
	}

	public String getContactInfo() {
		return customer.firstname() + " " + customer.lastname() + "\n" + customer.email();
	}

	public Address getBillingAddress() {
		if (customer.addresses == null || customer.addresses().isEmpty()) {
			return null;
		}
		return customer.addresses().stream().filter(Address::defaultBilling).findFirst().orElse(null);
	}

	public Address getShippingAddress() {
		if (customer.addresses().isEmpty()) {
			return null;
		}
		return customer.addresses().stream().filter(Address::defaultShipping).findFirst().orElse(null);
	}

	public String getBillingAddressAsString() {
		Address address = getBillingAddress();
		if (address == null) {
			return "You have not set a default billing address.";
		}

		return address.toString();
	}

	public String getShippingAddressAsString() {
		Address address = getShippingAddress();
		if (address == null) {
			return "You have not set a default shipping address.";
		}

		return address.toString();
	}
}
