package magentodemo.components;

import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

public class ListProducts extends ListControl<ListProducts> {
	private RepeatingControl<Label> labelName;
	private RepeatingControl<Label> labelPrice;
	private RepeatingControl<Label> labelOption;
	private RepeatingControl<Label> labelColor;
	private RepeatingControl<Button> buttonAddToCart;

	public ListProducts(Locator locator, String rowLocatorPattern) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = rowLocatorPattern;

		this.labelName = new RepeatingControl<>(
				locator,
				"//a[@class='product-item-link']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.labelPrice = new RepeatingControl<>(
				locator,
				"//span[@class='price']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.labelOption = new RepeatingControl<>(
				locator,
				"//div[@class='swatch-attribute-options clearfix']/div[text()='{0}']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.labelColor = new RepeatingControl<>(
				locator,
				"//div[@class='swatch-attribute color']//div[@option-label='{0}']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.buttonAddToCart = new RepeatingControl<>(
				locator,
				"//button[@title='Add to Cart']",
				LocatorMethod.XPATH,
				Button::new,
				rowLocatorPattern,
				hasHeader
		);
	}

	public ListProducts usingLabelName() {
		this.searchLabel = labelName;
		return this;
	}

	public Label labelName(int index) {
		return labelName.get(index);
	}

	public Label labelPrice(int index) {
		return labelPrice.get(index);
	}

	public Label labelOption(int index) {
		return labelOption.get(index);
	}

	public Label labelColor(int index) {
		return labelColor.get(index);
	}

	public Button buttonAddToCart(int index) {
		return buttonAddToCart.get(index);
	}
}
