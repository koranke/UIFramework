package saucedemo.pages.productsPage;

import org.openqa.selenium.By;
import ui.core.Locator;
import ui.core.controls.Label;
import saucedemo.SauceDemoSite;
import saucedemo.pages.BaseSauceDemoPage;

public class ProductsPage extends BaseSauceDemoPage<ProductsPage> {
	public Label title;
	public ListProducts listProducts;

	public ProductsPage(SauceDemoSite site) {
		super(site, "inventory.html");
		title = new Label(site.webDriver, By.xpath("//span[@class='title']"));
		listProducts = new ListProducts(new Locator(site.webDriver, By.xpath("//div[@class='inventory_list']")));
	}

}
