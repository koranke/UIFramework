package saucedemo.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import saucedemo.SauceDemoSite;
import saucedemo.general.ProductsHelper;
import saucedemo.general.ScenarioState;

public class CartSteps {
	private SauceDemoSite site;
	private ScenarioState scenarioState;

	public CartSteps(SauceDemoSite site, ScenarioState scenarioState) {
		this.site = site;
		this.scenarioState = scenarioState;
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

	@And("The cart should show the correct products")
	public void theCartShouldShowTheCorrectProducts() {
		ProductsHelper.verifyProductsInCart(scenarioState.getProducts(), site.cartPage());
	}
}
