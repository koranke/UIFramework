package swaglabs.regulartests;

import general.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucedemo.SauceDemoSite;

import java.util.ArrayList;
import java.util.List;

@Test
public class LoginTests extends TestBase {

	public void testLoginWithValidCredentials() {
		SauceDemoSite site = new SauceDemoSite();
		site.loginPage().signIn("standard_user", "secret_sauce");
		site.productsPage().assertIsOpen();
	}


	@DataProvider(name = "InvalidCredentialScenarios")
	public Object[][] getInvalidCredentialSc() {
		List<Object[]> data = new ArrayList<>();

		data.add(new Object[]{"standard_user", "bad_password", "Username and password do not match any user in this service"});
		data.add(new Object[]{"standard_user", "", "Password is required"});

		return data.toArray(new Object[][]{});
	}

	@Test(dataProvider = "InvalidCredentialScenarios")
	public void testLoginWithInvalidCredentials(String userName, String password, String errorMessage) {
		SauceDemoSite site = new SauceDemoSite();
		site.loginPage().signIn(userName, password);
		site.loginPage().labelError().assertTextContains(errorMessage);
	}
}
