package magentodemo.pages.homePage;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.MagentoDemoSite;
import magentodemo.components.ListProducts;
import magentodemo.components.PanelNavigation;
import magentodemo.domain.Product;
import magentodemo.pages.BaseMagentoDemoPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.core.Locator;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Accessors(fluent = true)
public class HomePage extends BaseMagentoDemoPage<HomePage> {
	private final PanelNavigation panelNavigation;
	private final ListProducts listProducts;

	public HomePage(MagentoDemoSite site) {
		super(site, "");
		this.panelNavigation = new PanelNavigation(site.getWebDriver());
		this.listProducts = new ListProducts(
				new Locator(site.webDriver, By.xpath("//ol[@class='product-items widget-product-grid']")),
				"//li[@class='product-item']");
	}

	public void addProductToCart(Product product, int sizeIndex, int colorIndex) {
		int cartCount = 0;
		try {
			cartCount =	Integer.parseInt(this.panelNavigation().labelCartCount().getText());
		} catch (Exception e) {
			// do nothing
		}

		listProducts.addProductToCart(product, sizeIndex, colorIndex);
		String expectedCartCount = String.valueOf(cartCount + 1);
		WebDriverWait wait = new WebDriverWait(getWebDriver(), Duration.of(2, ChronoUnit.SECONDS));
		wait.until(driver -> this.panelNavigation().labelCartCount().getText().equals(expectedCartCount));

	}


}
