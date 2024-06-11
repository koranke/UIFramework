package magentodemo;

import general.TestBase;
import magentodemo.components.ListProducts;
import magentodemo.domain.Product;
import magentodemo.pages.homePage.HomePage;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageTests extends TestBase {

	@Test
	public void testHomePage() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.homePage().open();
		site.homePage().assertIsOpen();

		site.homePage().panelNavigation().whatsNew().click();
		site.whatIsNewPage().assertIsOpen();

		site.whatIsNewPage().panelNavigation().logo().click();
		site.homePage().assertIsOpen();
	}

	@Test
	public void testSearch() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.homePage().open();
		site.homePage().panelNavigation().searchFor("shirt");
		site.searchResultsPage().assertIsOpen();
		site.searchResultsPage().labelResults().assertText("Search results for: 'shirt'");
		site.searchResultsPage().listProducts().assertRowCount(5);
	}

	@Test
	public void testSizesAndColors() {
		HomePage homePage = new MagentoDemoSite().homePage().open();
		ListProducts listProducts = homePage.listProducts();
		listProducts.usingLabelName().withRow("Hero Hoodie").labelName().scrollToElement();
		List<String> options = listProducts.getAllSizes();
		assertThat(options).contains("XS","S", "M", "L", "XL");
		List<String> colors = listProducts.getAllColors();
		assertThat(colors).contains("Green", "Black", "Gray");
	}

	@Test
	public void testAddToCart() {
		HomePage homePage = new MagentoDemoSite().homePage().open();
		ListProducts listProducts = homePage.listProducts();
		List<Product> products = listProducts.getAllProducts();
		listProducts.addProductToCart(products.get(0), 0 , 0);
		listProducts.addProductToCart(products.get(1), 1 , 1);
		homePage.panelNavigation().labelCartCount().assertText("2");
	}

	@Test
	public void testProducts() {
		HomePage homePage = new MagentoDemoSite().homePage().open();
		ListProducts listProducts = homePage.listProducts();
		List<Product> products = listProducts.getAllProducts();
		assertThat(products).hasSize(6);
	}

}
