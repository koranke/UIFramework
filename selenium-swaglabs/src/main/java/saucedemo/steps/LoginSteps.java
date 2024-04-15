package saucedemo.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import saucedemo.SauceDemoSite;

public class LoginSteps {
	private final SauceDemoSite site = new SauceDemoSite();

	@Given("I am on the login page")
	public void iAmOnTheLoginPage() {
		site.loginPage().goTo();
	}

	@When("I login with valid credentials")
	public void iLoginWithValidCredentials() {
		site.loginPage().signIn("standard_user", "secret_sauce");
	}

	@Then("I should see the Products page")
	public void iShouldSeeTheProductsPage() {
		site.productsPage().assertIsOpen();
	}

	@When("I login with invalid credentials")
	public void iLoginWithInvalidCredentials() {
		site.loginPage().signIn("standard_user", "bad_password");
	}

	@Then("I should see an error message that contains {string}")
	public void iShouldSeeAnErrorMessage(String message) {
		site.loginPage().labelError().assertTextContains(message);
	}

	@When("I login with invalid credentials {string} {string}")
	public void iLoginWithInvalidCredentialsUsernamePassword(String username, String password) {
		site.loginPage().signIn(username, password);
	}
}
