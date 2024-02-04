package saucedemo.pages.productsPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import saucedemo.SauceDemoSite;
import ui.core.controls.Button;
import ui.core.controls.Label;
import saucedemo.pages.BaseSauceDemoPage;

@Getter()
@Accessors(fluent = true)
public class ProductsPage extends BaseSauceDemoPage<ProductsPage> {
	private final Label labelTitle;
	private final ListProducts listProducts;
	private final Button buttonCart;
	private final Label labelCartCount;

	public ProductsPage(SauceDemoSite site) {
		super(site, "inventory.html");
		labelTitle = new Label(site.page.locator("//span[@class='title']"));
		listProducts = new ListProducts(site.page.locator("//div[@class='inventory_list']"));
		buttonCart = new Button(site.page.locator("//a[@class='shopping_cart_link']"));
		labelCartCount = new Label(site.page.locator("//span[@class='shopping_cart_badge']"));
	}

}
