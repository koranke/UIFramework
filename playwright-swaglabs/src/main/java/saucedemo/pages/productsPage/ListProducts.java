package saucedemo.pages.productsPage;

import com.microsoft.playwright.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

public class ListProducts extends ListControl {
	public RepeatingControl<Label> labelPrice;
	public RepeatingControl<Label> labelName;
	public RepeatingControl<Button> buttonAddToCart;
	public RepeatingControl<Button> buttonRemoveFromCart;

	public ListProducts(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//div[@class='inventory_item']";

		labelPrice = new RepeatingControl<>(
				locator,
				"//div[@class='inventory_item_price']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);
		labelName = new RepeatingControl<>(
				locator,
				"//div[@class='inventory_item_name ']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);
		buttonAddToCart = new RepeatingControl<>(
				locator, "Add to cart",
				LocatorMethod.TEXT,
				Button::new,
				rowLocatorPattern,
				hasHeader
		);
		buttonRemoveFromCart = new RepeatingControl<>(locator,
				"Remove",
				LocatorMethod.TEXT,
				Button::new,
				rowLocatorPattern,
				hasHeader
		);
	}

	public Button getButtonAddToCart(String targetText) {
		int index = labelName.getIndex(targetText);
		return buttonAddToCart.get(index);
	}

	public Button getButtonRemoveFromCart(String targetText) {
		int index = labelName.getIndex(targetText);
		return buttonRemoveFromCart.get(index);
	}
}
