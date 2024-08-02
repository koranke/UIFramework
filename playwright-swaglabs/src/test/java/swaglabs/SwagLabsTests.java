package swaglabs;

import general.TestBase;
import org.testng.annotations.Test;
import saucedemo.SauceDemoSite;
import saucedemo.pages.cartPage.CartPage;
import saucedemo.pages.loginPage.LoginPage;
import saucedemo.pages.productsPage.ProductsPage;

public class SwagLabsTests extends TestBase {

	@Test(enabled = false)
	public void testLoginControls() {
		LoginPage loginPage = new SauceDemoSite().loginPage().goTo();
		loginPage.textBoxUserName().setText("George");
		loginPage.textBoxPassword().setText("myPassword");

		loginPage.textBoxUserName().assertText("George");
		loginPage.textBoxPassword().assertText("myPassword");
		loginPage.buttonLogin().assertText("Login");
	}

	@Test(enabled = false)
	public void testLoginVisual() {
		LoginPage loginPage = new SauceDemoSite().loginPage().goTo();
		loginPage.assertScreenShot();
	}

	@Test(enabled = false)
	public void testProductPage() {
		ProductsPage productsPage = new SauceDemoSite().productsPage().open();
		productsPage.assertIsOpen();
		productsPage.labelTitle().assertText("Products");

		log.info(productsPage.listProducts().withRow(2).labelName().getText());
		productsPage.listProducts().buttonAddToCart().click();
		productsPage.labelCartCount().assertText("1");
		productsPage.listProducts().buttonRemoveFromCart().click();
		productsPage.labelCartCount().assertIsNotVisible();

		productsPage.listProducts().usingLabelName().withRow("Sauce Labs Fleece Jacket").buttonAddToCart().click();
		productsPage.labelCartCount().assertText("1");
		productsPage.listProducts().buttonRemoveFromCart().click();
		productsPage.labelCartCount().assertIsNotVisible();
	}

	@Test(enabled = false)
	public void testCart() {
		SauceDemoSite site = new SauceDemoSite();
		ProductsPage productsPage = site.productsPage().open();
		CartPage cartPage = site.cartPage();

		for (int i = 1; i <= 3; i++) {
			productsPage.listProducts().withRow(i).buttonAddToCart().click();
		}

		productsPage.buttonCart().click();
		cartPage.assertIsOpen();
		cartPage.labelTitle().assertText("Your Cart");
		cartPage.listCartItems().assertRowCount(3);
		cartPage.listCartItems().withRow(2).buttonRemove().click();
		cartPage.listCartItems().assertRowCount(2);
		cartPage.buttonContinueShopping().click();
		productsPage.assertIsOpen();
	}

}
