package swaglabs.regulartests;

import general.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucedemo.SauceDemoSite;
import saucedemo.domain.Product;
import saucedemo.enums.SortingDirection;
import saucedemo.general.ProductsHelper;
import saucedemo.pages.productsPage.ProductsPage;

import java.util.ArrayList;
import java.util.List;

public class ProductsTests extends TestBase {

	@Test
	public void testViewAllProducts() {
		ProductsPage productsPage = new SauceDemoSite().productsPage().open();
		List<Product> products = productsPage.getAllProducts();
		ProductsHelper.verifyProductsSortOrder(products, "Name", SortingDirection.ASCENDING);
	}

	@DataProvider(name = "SortScenarios")
	public Object[][] getSortScen() {
		List<Object[]> data = new ArrayList<>();

		data.add(new Object[]{ "Name", SortingDirection.ASCENDING });
		data.add(new Object[]{ "Name", SortingDirection.DESCENDING });
		data.add(new Object[]{ "Price", SortingDirection.ASCENDING });
		data.add(new Object[]{ "Price", SortingDirection.DESCENDING });

		return data.toArray(new Object[][]{});
	}

	@Test(dataProvider = "SortScenarios")
	public void testSortProducts(String sortingField, SortingDirection sortingDirection) {
		ProductsPage productsPage = new SauceDemoSite().productsPage().open();
		productsPage.setSortingOption(sortingField, sortingDirection);
		List<Product> products = productsPage.getAllProducts();
		ProductsHelper.verifyProductsSortOrder(products, sortingField, sortingDirection);
	}

	@Test
	public void testAddProductToCart() {
		ProductsPage productsPage = new SauceDemoSite().productsPage().open();
		productsPage.listProducts().withRow(1).buttonAddToCart().click();
		productsPage.labelCartCount().assertText("1");
	}

	@Test
	public void testAddAnotherProductToCart() {
		ProductsPage productsPage = new SauceDemoSite().productsPage().open();
		productsPage.listProducts().withRow(1).buttonAddToCart().click();
		productsPage.listProducts().withRow(2).buttonAddToCart().click();
		productsPage.labelCartCount().assertText("2");
	}

	@Test
	public void testRemoveProductFromCart() {
		ProductsPage productsPage = new SauceDemoSite().productsPage().open();
		productsPage.listProducts().withRow(1).buttonAddToCart().click();
		productsPage.listProducts().buttonRemoveFromCart().click();
		productsPage.labelCartCount().assertIsNotVisible();
	}
}
