package saucedemo.pages.productsPage;

import com.microsoft.playwright.Locator;
import saucedemo.domain.Product;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

public class ListProducts extends ListControl<ListProducts> {
	private final RepeatingControl<Label> labelPrice;
	private final RepeatingControl<Label> labelName;
	private final RepeatingControl<Label> labelDescription;
	private final RepeatingControl<Button> buttonAddToCart;
	private final RepeatingControl<Button> buttonRemoveFromCart;

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
		labelDescription = new RepeatingControl<>(
				locator,
				"//div[@class='inventory_item_desc']",
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

	public ListProducts usingLabelName() {
		this.searchLabel = labelName;
		return this;
	}

	public Label labelPrice() {
		return labelPrice.get(currentRow);
	}

	public Label labelName() {
		return labelName.get(currentRow);
	}

	public Label labelDescription() {
		return labelDescription.get(currentRow);
	}

	public Button buttonAddToCart() {
		return buttonAddToCart.get(currentRow);
	}

	public Button buttonRemoveFromCart() {
		return buttonRemoveFromCart.get(currentRow);
	}

	public Product getCurrentProduct() {
		Product product = new Product();
		product.setName(labelName().getText());
		product.setDescription(labelDescription().getText());
		product.setPrice(Double.parseDouble(labelPrice().getText().replace("$", "")));
		return product;
	}
}
