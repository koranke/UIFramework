package saucedemo;

import saucedemo.pages.productsPage.ProductsPage;
import ui.core.Site;
import saucedemo.pages.loginPage.LoginPage;

public class SauceDemoSite extends Site<SauceDemoSite> {
	private LoginPage loginPage;
	private ProductsPage productsPage;

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
}
