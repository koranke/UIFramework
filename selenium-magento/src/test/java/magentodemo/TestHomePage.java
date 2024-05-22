package magentodemo;

import general.TestBase;
import magentodemo.components.ListProducts;
import magentodemo.pages.homePage.HomePage;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHomePage extends TestBase {

	@Test
	public void testHomePage() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.homePage().open();
		site.homePage().assertIsOpen();

		site.homePage().navigationPanel().whatsNew().click();
		site.whatIsNewPage().assertIsOpen();

		site.whatIsNewPage().navigationPanel().logo().click();
		site.homePage().assertIsOpen();
	}

	@Test
	public void testSearch() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.homePage().open();
		site.homePage().assertIsOpen();

		site.homePage().navigationPanel().textBoxSearch().setText("shirt");
		site.homePage().navigationPanel().buttonSearch().click();
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

		addProductToCart(listProducts, "Hero Hoodie", "L", "Green");
		addProductToCart(listProducts, "Radiant Tee", "M", "Orange");

	}


	private void addProductToCart(ListProducts listProducts, String productName, String option, String color) {
		listProducts.usingLabelName().withRow(productName).labelName().scrollToElement();
		listProducts.labelName().hover();
		listProducts.labelColor(color).click();
		listProducts.labelOption(option).click();
		listProducts.buttonAddToCart().assertIsVisible();
		listProducts.buttonAddToCart().click();
	}

}
