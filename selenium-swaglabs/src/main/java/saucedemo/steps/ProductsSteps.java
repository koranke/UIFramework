package saucedemo.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import saucedemo.SauceDemoSite;
import saucedemo.domain.Product;
import saucedemo.enums.SortingDirection;
import saucedemo.general.ScenarioState;
import saucedemo.pages.productsPage.ListProducts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductsSteps {
	private SauceDemoSite site;
	private ScenarioState scenarioState;
	private int productIndex = 1;

	public ProductsSteps(SauceDemoSite site, ScenarioState scenarioState) {
		this.site = site;
		this.scenarioState = scenarioState;
	}

	@Given("I am on the products page")
	public void iAmOnTheProductsPage() {
		site.productsPage().open();
	}

	@When("I sort products by {word} {sortingDirection}")
	public void iSortProductsBySortDirection(String sortingField, SortingDirection sortingDirection) {
		String sortingOption = sortingField;
		if (sortingDirection == SortingDirection.ASCENDING) {
			sortingOption += sortingField.equals("Name") ? " (A to Z)" : " (low to high)";
		} else {
			sortingOption += sortingField.equals("Name") ? " (Z to A)" : " (high to low)";
		}

		site.productsPage().comboBoxSort().setText(sortingOption);
	}

	@Then("I should see all products sorted by {word} {sortingDirection}")
	public void iShouldSeeAllProductsSortedSortingDirectionBySortingField(String sortingField, SortingDirection sortingDirection) {
		ListProducts products = site.productsPage().listProducts();
		switch (sortingField) {
			case "Name":
				products.usingLabelName();
				List<String> productNames = site.productsPage().listProducts().getAllLabels();
				if (sortingDirection == SortingDirection.DESCENDING) {
					assertThat(productNames).isSortedAccordingTo(Comparator.reverseOrder());
				} else {
					assertThat(productNames).isSorted();
				}
				break;
			case "Price":
				products.usingLabelPrice();
				List<Double> productPrices = site.productsPage().listProducts().getAllLabels().stream()
						.map(price -> Double.parseDouble(price.replace("$", "")))
						.toList();
				if (sortingDirection == SortingDirection.DESCENDING) {
					assertThat(productPrices).isSortedAccordingTo((a, b) -> Double.compare(b, a));
				} else {
					assertThat(productPrices).isSorted();
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown sorting field: " + sortingField);
		}
	}

	@ParameterType("ascending|descending")
	public SortingDirection sortingDirection(String direction) {
		return SortingDirection.valueOf(direction.toUpperCase());
	}

	@Then("I should see {int} item(s) in the cart")
	public void iShouldSeeItemInTheCart(int cartCount) {
		assertThat(site.productsPage().labelCartCount().getText()).isEqualTo(String.valueOf(cartCount));
	}

	@When("I (have )add(ed) (another ){int} product(s) to the cart")
	public void iAddAProductToTheCart(int productCount) {
		for (int i = 0; i < productCount; i++) {
			site.productsPage().listProducts().withRow(productIndex++).buttonAddToCart().click();
		}
	}

	@When("I remove a product from the cart")
	public void iRemoveAProductFromTheCart() {
		site.productsPage().listProducts().withRow(--productIndex).buttonRemoveFromCart().click();
	}

	@Then("I should see no items in the cart")
	public void iShouldSeeNoItemsInTheCart() {
		site.productsPage().labelCartCount().assertIsNotVisible();
	}

	@When("I click the shopping cart icon")
	public void iClickTheShoppingCartIcon() {
		site.productsPage().buttonCart().click();
	}

	@And("I (have )add(ed) the following products to the cart")
	public void iHaveAddedTheFollowingProductsToTheCart(DataTable dataTable) {
		List<String> productNames = dataTable.asList();
		List<Product> products = new ArrayList<>();
		productNames.forEach(productName -> {
			site.productsPage().listProducts().usingLabelName().withRow(productName).buttonAddToCart().click();
			Product newProduct = new Product();
			newProduct.setName(productName);
			newProduct.setDescription(site.productsPage().listProducts().labelDescription().getText());
			newProduct.setPrice(Double.parseDouble(site.productsPage().listProducts().labelPrice().getText().replace("$", "")));
			products.add(newProduct);
		});
		scenarioState.setProducts(products);
	}
}
