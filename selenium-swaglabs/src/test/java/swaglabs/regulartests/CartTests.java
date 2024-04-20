package swaglabs.regulartests;

import general.TestBase;
import org.testng.annotations.Test;
import saucedemo.SauceDemoSite;
import saucedemo.domain.Product;
import saucedemo.general.ProductsHelper;

import java.util.ArrayList;
import java.util.List;

public class CartTests extends TestBase {

	@Test
	public void testViewCartWithNoProducts() {
		SauceDemoSite site = new SauceDemoSite();
		site.productsPage().open();
		site.productsPage().buttonCart().click();
		site.cartPage().assertIsOpen();
		site.cartPage().listCartItems().assertIsNotVisible();
	}

	@Test
	public void testViewCartWithProduct() {
		SauceDemoSite site = new SauceDemoSite();
		site.productsPage().open();
		site.productsPage().listProducts().withRow(1).buttonAddToCart().click();
		site.productsPage().buttonCart().click();
		site.cartPage().assertIsOpen();
		site.cartPage().listCartItems().assertRowCount(1);
	}

	@Test
	public void testViewCartWithMultipleProducts() {
		SauceDemoSite site = new SauceDemoSite();
		site.productsPage().open();

		List<String> productNames = List.of("Sauce Labs Onesie", "Sauce Labs Bike Light");
		List<Product> products = new ArrayList<>();
		productNames.forEach(productName -> {
			site.productsPage().listProducts().usingLabelName().withRow(productName).buttonAddToCart().click();
			products.add(site.productsPage().listProducts().getCurrentProduct());
		});

		site.productsPage().buttonCart().click();
		site.cartPage().listCartItems().assertRowCount(productNames.size());
		ProductsHelper.verifyProductsInCart(products, site.cartPage());
	}
}
