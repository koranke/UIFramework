package magentodemo.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

import java.util.List;

public class ListProducts extends ListControl<ListProducts> {
	private final RepeatingControl<Label> labelName;
	private final RepeatingControl<Label> labelPrice;
	private final RepeatingControl<Label> labelOption;
	private final RepeatingControl<Label> labelColor;
	private final RepeatingControl<Button> buttonAddToCart;

	public ListProducts(Locator locator, String rowLocatorPattern) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = rowLocatorPattern;

		this.labelName = new RepeatingControl<>(
				locator,
				".//a[@class='product-item-link']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.labelPrice = new RepeatingControl<>(
				locator,
				".//span[@class='price']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.labelOption = new RepeatingControl<>(
				locator,
				".//div[@class='swatch-option text' and text()='%s']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.labelColor = new RepeatingControl<>(
				locator,
				".//div[@class='swatch-option color' and @option-label='%s']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);

		this.buttonAddToCart = new RepeatingControl<>(
				locator,
				".//button[@title='Add to Cart']",
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

	public Label labelName() {
		return labelName.get(currentRow);
	}

	public Label labelPrice() {
		return labelPrice.get(currentRow);
	}

	public Label labelOption(String option) {
		return labelOption.get(currentRow, option);
	}

	public Label labelColor(String color) {
		return labelColor.get(currentRow, color);
	}

	public Button buttonAddToCart() {
		return buttonAddToCart.get(currentRow);
	}

	public List<String> getAllSizes() {
		String sizesPattern = ".//div[@class='swatch-option text']";
		List<WebElement> sizes = getRowAsElement(currentRow).findElements(By.xpath(sizesPattern));
		return sizes.stream().map(WebElement::getText).toList();
	}

	public List<String> getAllColors() {
		String sizesPattern = ".//div[@class='swatch-option color']";
		List<WebElement> colors = getRowAsElement(currentRow).findElements(By.xpath(sizesPattern));
		return colors.stream().map(item -> item.getAttribute("option-label")).toList();
	}

}
