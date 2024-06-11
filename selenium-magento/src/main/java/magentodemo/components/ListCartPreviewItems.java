package magentodemo.components;

import magentodemo.domain.Product;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.controls.TextBox;
import ui.core.enums.LocatorMethod;

public class ListCartPreviewItems extends ListControl<ListCartPreviewItems> {
	private final RepeatingControl<Label> labelName;
	private final RepeatingControl<Label> labelDetails;
	private final RepeatingControl<Label> labelPrice;
	private final RepeatingControl<Label> labelSize;
	private final RepeatingControl<Label> labelColor;
	private final RepeatingControl<TextBox> textBoxQuantity;
	private final RepeatingControl<Button> buttonRemove;
	private final RepeatingControl<Button> buttonEdit;

	public ListCartPreviewItems(Locator locator, String rowLocatorPattern) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = rowLocatorPattern;

		this.labelName = new RepeatingControl<>(
				locator,
				".//strong[@class='product-item-name']/a",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader);
		this.labelDetails = new RepeatingControl<>(
				locator,
				".//span[text()='See Details']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader);
		this.labelPrice = new RepeatingControl<>(
				locator,
				".//span[@class='price']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader);
		this.labelSize = new RepeatingControl<>(
				locator,
				".//dt[text()='Size']/following-sibling::dd/span",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader);
		this.labelColor = new RepeatingControl<>(
				locator,
				".//dt[text()='Color']/following-sibling::dd/span",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader);
		this.textBoxQuantity = new RepeatingControl<>(
				locator,
				".//input[@class='input-text qty']",
				LocatorMethod.XPATH,
				TextBox::new,
				rowLocatorPattern,
				hasHeader);
		this.buttonRemove = new RepeatingControl<>(
				locator,
				".//a[@class='action action-delete']",
				LocatorMethod.XPATH,
				Button::new,
				rowLocatorPattern,
				hasHeader);
		this.buttonEdit = new RepeatingControl<>(
				locator,
				".//a[@class='action edit']",
				LocatorMethod.XPATH,
				Button::new,
				rowLocatorPattern,
				hasHeader);
	}

	public ListCartPreviewItems usingLabelName() {
		this.searchLabel = labelName;
		return this;
	}

	public Label labelName() {
		return labelName.get(currentRow);
	}

	public Label labelDetails() {
		return labelDetails.get(currentRow);
	}

	public Label labelPrice() {
		return labelPrice.get(currentRow);
	}

	public Label labelSize() {
		return labelSize.get(currentRow);
	}

	public Label labelColor() {
		return labelColor.get(currentRow);
	}

	public TextBox textBoxQuantity() {
		return textBoxQuantity.get(currentRow);
	}

	public Button buttonRemove() {
		return buttonRemove.get(currentRow);
	}

	public Button buttonEdit() {
		return buttonEdit.get(currentRow);
	}

	public void verifyItem(Product product, Integer sizeIndex, Integer colorIndex) {
		usingLabelName().withRow(product.getName()).labelDetails().click();
		if (sizeIndex != null) {
			labelSize().assertText(product.getSizes().get(sizeIndex));
		} else {
			labelSize().assertIsNotVisible();
		}
		if (colorIndex != null) {
			labelColor().assertText(product.getColors().get(colorIndex));
		} else {
			labelColor().assertIsNotVisible();
		}

		labelPrice().assertText(product.getPrice());
	}
}
