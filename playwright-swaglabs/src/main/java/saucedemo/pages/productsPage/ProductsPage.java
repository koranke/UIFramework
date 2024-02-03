package saucedemo.pages.productsPage;

import com.microsoft.playwright.Locator;
import saucedemo.SauceDemoSite;
import ui.core.controls.Label;
import saucedemo.pages.BaseSauceDemoPage;

import java.util.List;

public class ProductsPage extends BaseSauceDemoPage<ProductsPage> {
	public Label title;
	public ListProducts listProducts;

	public ProductsPage(SauceDemoSite site) {
		super(site, "inventory.html");
		title = new Label(site.page.locator("//span[@class='title']"));
		listProducts = new ListProducts(site.page.locator("//div[@class='inventory_list']"));
	}

	public List<Locator> getMask() {

		return null;
	}
}
