package saucedemo.pages.cartPage;

import com.microsoft.playwright.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

public class ListCartItems extends ListControl<ListCartItems> {
	private final RepeatingControl<Label> labelQuantity;
	private final RepeatingControl<Label> labelName;
	private final RepeatingControl<Label> labelPrice;
	private final RepeatingControl<Button> buttonRemove;

	public ListCartItems(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//div[@class='cart_item']";

		labelQuantity = new RepeatingControl<>(
				locator,
				"//div[@class='cart_quantity']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);
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
		buttonRemove = new RepeatingControl<>(locator,
				"Remove",
				LocatorMethod.TEXT,
				Button::new,
				rowLocatorPattern,
				hasHeader
		);
	}

	public ListCartItems usingLabelName() {
		this.searchLabel = labelName;
		return this;
	}

	public Label labelQuantity() {
		return labelQuantity.get(currentRow);
	}

	public Label labelName() {
		return labelName.get(currentRow);
	}

	public Label labelPrice() {
		return labelPrice.get(currentRow);
	}

	public Button buttonRemove() {
		return buttonRemove.get(currentRow);
	}

}
