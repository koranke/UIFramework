package saucedemo.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import saucedemo.SauceDemoSite;
import saucedemo.domain.Product;
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
		if (scenarioState.getProducts() == null || scenarioState.getProducts().isEmpty()) {
			site.cartPage().listCartItems().assertIsNotVisible();
		} else {
			for (Product product : scenarioState.getProducts()) {
				site.cartPage().listCartItems().usingLabelName().withRow(product.getName()).labelName().assertText(product.getName());
				site.cartPage().listCartItems().labelDescription().assertText(product.getDescription());
				site.cartPage().listCartItems().labelPrice().assertText(String.format("$%.2f", product.getPrice()));
				site.cartPage().listCartItems().labelQuantity().assertText("1");
			}
		}
	}
}
