package magentodemo.pages.searchResultsPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.MagentoDemoSite;
import magentodemo.components.ListProducts;
import magentodemo.components.PanelNavigation;
import magentodemo.pages.BaseMagentoDemoPage;
import org.openqa.selenium.By;
import ui.core.Locator;
import ui.core.controls.Label;

@Getter
@Accessors(fluent = true)
public class SearchResultsPage extends BaseMagentoDemoPage<SearchResultsPage> {
	private final PanelNavigation panelNavigation;
	private final ListProducts listProducts;
	private final Label labelResults;
	private final Label labelNoResults;

	public SearchResultsPage(MagentoDemoSite site) {
		super(site, "catalogsearch/result/.*");

		this.panelNavigation = new PanelNavigation(site.getWebDriver());
		this.listProducts = new ListProducts(new Locator(site.getWebDriver(), By.xpath("//ol[@class='products list items product-items']")),
				"//li[@class='item product product-item']");

		this.labelResults = new Label(site.getWebDriver(), By.xpath("//h1[@class='page-title']/span"));
		this.labelNoResults = new Label(site.getWebDriver(), By.xpath("//div[@class='message notice']"));
	}
}
