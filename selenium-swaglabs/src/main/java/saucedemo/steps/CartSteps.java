package saucedemo.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import saucedemo.SauceDemoSite;

public class CartSteps {
	private SauceDemoSite site;

	public CartSteps(SauceDemoSite site) {
		this.site = site;
	}

	@Then("I should see the cart page")
	public void iShouldSeeTheCartPage() {
		site.cartPage().assertIsOpen();
	}

	@And("The cart should show {int} product(s)")
	public void theCartShouldShowNoProducts(int productCount) {
		if (productCount == 0) {
			site.cartPage().listCartItems().assertIsNotVisible();
		} else {
			site.cartPage().listCartItems().assertRowCount(productCount);
		}
	}
}
