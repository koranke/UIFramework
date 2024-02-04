package saucedemo;

import saucedemo.pages.cartPage.CartPage;
import saucedemo.pages.productsPage.ProductsPage;
import ui.core.Site;
import saucedemo.pages.loginPage.LoginPage;

public class SauceDemoSite extends Site<SauceDemoSite> {
	private LoginPage loginPage;
	private ProductsPage productsPage;
	private CartPage cartPage;

	public SauceDemoSite() {
		super();
		initialize();
	}

	private void initialize() {
		this.baseUrl = SauceDemoConstants.baseUrl;
	}

	public LoginPage loginPage() {
		if (loginPage == null) {
			loginPage = new LoginPage(this);
		}
		return loginPage;
	}

	public ProductsPage productsPage() {
		if (productsPage == null) {
			productsPage = new ProductsPage(this);
		}
		return productsPage;
	}

	public CartPage cartPage() {
		if (cartPage == null) {
			cartPage = new CartPage(this);
		}
		return cartPage;
	}
}
