package saucedemo.pages.productsPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.ComboBox;
import ui.core.controls.Label;
import saucedemo.SauceDemoSite;
import saucedemo.pages.BaseSauceDemoPage;
import ui.core.controls.SelectComboBox;

@Getter()
@Accessors(fluent = true)
public class ProductsPage extends BaseSauceDemoPage<ProductsPage> {
	private final Label labelTitle;
	private final ListProducts listProducts;
	private final Button buttonCart;
	private final Label labelCartCount;
	private final ComboBox comboBoxSort;

	public ProductsPage(SauceDemoSite site) {
		super(site, "inventory.html");
		labelTitle = new Label(site.webDriver, By.xpath("//span[@class='title']"));
		buttonCart = new Button(site.webDriver, By.className("shopping_cart_link"));
		labelCartCount = new Label(site.webDriver, By.xpath("//span[@class='shopping_cart_badge']"));
		listProducts = new ListProducts(new Locator(site.webDriver, By.xpath("//div[@class='inventory_list']")));
		comboBoxSort = new SelectComboBox(site.webDriver, By.className("product_sort_container"));
	}

}
