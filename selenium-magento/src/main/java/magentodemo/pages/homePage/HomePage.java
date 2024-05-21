package magentodemo.pages.homePage;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.MagentoDemoSite;
import magentodemo.components.ListProducts;
import magentodemo.components.NavigationPanel;
import magentodemo.pages.BaseMagentoDemoPage;
import org.openqa.selenium.By;
import ui.core.Locator;

@Getter
@Accessors(fluent = true)
public class HomePage extends BaseMagentoDemoPage<HomePage> {
	private final NavigationPanel navigationPanel;
	private final ListProducts listProducts;

	public HomePage(MagentoDemoSite site) {
		super(site, "");
		this.navigationPanel = new NavigationPanel(site.getWebDriver());
		this.listProducts = new ListProducts(
				new Locator(site.webDriver, By.xpath("//ol[@class='product-items widget-product-grid']")),
				"//li[@class='product-item']");
	}


}
