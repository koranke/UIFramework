package saucedemo.pages.cartPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import saucedemo.SauceDemoSite;
import saucedemo.pages.BaseSauceDemoPage;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;

@Getter
@Accessors(fluent = true)
public class CartPage extends BaseSauceDemoPage<CartPage> {
	private final Label labelTitle;
	private final Button buttonContinueShopping;
	private final ListCartItems listCartItems;

	public CartPage(SauceDemoSite site) {
		super(site, "cart.html");

		this.labelTitle = new Label(site.webDriver, By.className("title"));
		this.buttonContinueShopping = new Button(site.webDriver, By.id("continue-shopping"));
		this.listCartItems = new ListCartItems(new Locator(site.webDriver, By.className("cart_list")));
	}
}
