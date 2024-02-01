package swaglabs;


import general.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import saucedemo.SauceDemoSite;
import saucedemo.pages.loginPage.LoginPage;
import saucedemo.pages.productsPage.ProductsPage;
import ui.core.SeleniumManager;

public class SwagLabsTests extends TestBase {

//	@BeforeClass
//	public void setup() {
//		SeleniumManager.setSlowTime(500);
//	}

	@Test
	public void testShoppingCart() {
		ProductsPage productsPage = new SauceDemoSite().productsPage().open();
		productsPage.assertIsOpen();
		productsPage.title.assertText("Products");

		log.info(productsPage.listProducts.labelName.get(2).getText());
		productsPage.listProducts.buttonAddToCart.get(2).click();
		productsPage.listProducts.buttonRemoveFromCart.get(2).click();

		int index = productsPage.listProducts.labelName.getIndex("Sauce Labs Fleece Jacket");
		productsPage.listProducts.buttonAddToCart.get(index).click();
		productsPage.listProducts.getButtonRemoveFromCart("Sauce Labs Fleece Jacket").click();
	}

	@Test
	public void testLoginControls() {
		LoginPage loginPage = new SauceDemoSite().loginPage().goTo();
		loginPage.textBoxUserName.setText("George");
		loginPage.textBoxPassword.setText("myPassword");

		loginPage.textBoxUserName.assertText("George");
		loginPage.textBoxPassword.assertText("myPassword");
		loginPage.buttonLogin.assertText("Login");
	}

	@Test
	public void testLoginVisual() {
		LoginPage loginPage = new SauceDemoSite().loginPage().goTo();
		loginPage.assertScreenShot();
	}
}
