package magentodemo.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import utilities.RandomData;

@Data
@Accessors(fluent = true)
public class Account extends BaseScenario {
	private Customer customer;
	private String password;

	public Account withDefaults() {
		if (needsPopulation) {
			this.customer = new Customer().withMinDefaults();
			this.password = RandomData.getRandomPassword();
			needsPopulation = false;
		}
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

		return customer.firstname() + " " + customer.lastname()
				+ "\n" + address;
	}

	public String getShippingAddressAsString() {

		Address address = getShippingAddress();
		if (address == null) {
			return "You have not set a default shipping address.";
		}

		return customer.firstname() + " " + customer.lastname()
				+ "\n" + address;
	}
}
