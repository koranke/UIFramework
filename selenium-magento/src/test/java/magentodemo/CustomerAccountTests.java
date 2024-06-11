package magentodemo;

import general.TestBase;
import magentodemo.domain.Customer;
import org.testng.annotations.Test;

public class CustomerAccountTests extends TestBase {

	@Test
	public void testOpenCreateNewCustomerAccount() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.homePage().open();
		site.homePage().panelNavigation().linkCreateAccount().click();
		site.newCustomerPage().assertIsOpen();
	}

	@Test
	public void testCreateNewCustomerAccount() {
		MagentoDemoSite site = new MagentoDemoSite();
		Customer customer = site.newCustomerPage().open().createRandomAccount();
	}
}
