package magentodemo;

import general.TestBase;
import io.restassured.response.Response;
import magentodemo.api.CustomerApi;
import magentodemo.domain.Account;
import magentodemo.domain.Customer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CustomerAccountTests extends TestBase {
	private Account defaultAccount;

	@BeforeClass
	protected void setup() {
		Customer defaultCustomer = new Customer()
				.email("E3nNGbgzpp@test.com")
				.firstname("Theodora")
				.lastname("Watsical");

		defaultAccount = new Account()
				.customer(defaultCustomer)
				.password("LXQkph8754==");

		Response response = CustomerApi.tryGetToken(defaultAccount.getAuthBody());
		if (response.statusCode() == 200) {
			defaultAccount = CustomerApi.getMe(response.body().as(String.class));
		} else {
			defaultAccount = CustomerApi.create(defaultAccount);
		}
		defaultAccount.password("LXQkph8754==");
	}

	@Test
	public void testOpenCreateNewCustomerAccountPage() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.homePage().open();
		site.homePage().panelNavigation().linkCreateAccount().click();
		site.newCustomerPage().assertIsOpen();
	}

	@Test
	public void testCreateNewCustomerAccount() {
		MagentoDemoSite site = new MagentoDemoSite();
		Account customer = site.newCustomerPage().open().createRandomAccount();
		site.myAccountPage().assertIsOpen();
		site.myAccountPage().labelContactInfo().assertText(customer.getContactInfo());
		site.myAccountPage().labelBillingAddress().assertText(customer.getBillingAddressAsString());
	}

	@Test
	public void testViewAccount() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.myAccountPage().open(defaultAccount.getAuthBody());
		site.myAccountPage().labelContactInfo().assertText(defaultAccount.getContactInfo());
		site.myAccountPage().labelBillingAddress().assertText(defaultAccount.getBillingAddressAsString());
		site.myAccountPage().labelShippingAddress().assertText(defaultAccount.getShippingAddressAsString());
	}

}
