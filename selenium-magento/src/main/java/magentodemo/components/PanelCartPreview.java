package magentodemo.components;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.domain.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.PanelControl;

import java.util.List;

@Getter
@Accessors(fluent = true)
public class PanelCartPreview extends PanelControl {
	private final Button buttonClose;
	private final Label labelSubtotal;
	private final Label labelCartCount;
	private final Button buttonViewCart;
	private final Button buttonCheckout;
	private final ListCartPreviewItems listCartPreviewItems;

	public PanelCartPreview(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.labelCartCount = new Label(webDriver, By.xpath("//span[@class='count']"));
		this.buttonClose = new Button(webDriver, By.xpath("//button[@title='Close']"));
		this.labelSubtotal = new Label(webDriver, By.xpath("//span[@class='price']"));
		this.buttonViewCart = new Button(webDriver, By.xpath("//span[text()='View and Edit Cart']"));
		this.buttonCheckout = new Button(webDriver, By.xpath("//button[text()='Proceed to Checkout']"));
		this.listCartPreviewItems = new ListCartPreviewItems(
				new Locator(webDriver, By.id("mini-cart")),
				"//li[./div[@class='product']]"
		);
	}

	public void verifySubTotal(List<Product> products) {
		double total = 0;
		for (Product product : products) {
			String price = product.getPrice().substring(1);
			double priceDouble = Double.parseDouble(price);
			total += priceDouble;
		}
		labelSubtotal.assertText(String.format("$%.2f", total));
	}

}
