package saucedemo;

import com.microsoft.playwright.Browser;
import enums.TargetBrowser;
import saucedemo.pages.loginPage.LoginPage;
import saucedemo.pages.productsPage.ProductsPage;
import ui.core.Site;

public class SauceDemoSite extends Site<SauceDemoSite> {
	private LoginPage loginPage;
	private ProductsPage productsPage;

	public SauceDemoSite() {
		super();
		initialize();
	}

	public SauceDemoSite(TargetBrowser targetBrowser) {
		super(targetBrowser);
		initialize();
	}

	public SauceDemoSite(Browser.NewContextOptions contextOptions) {
		super(contextOptions);
		initialize();
	}

	private void initialize() {
		baseUrl = SauceDemoConstants.baseUrl;
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
