package magentodemo.pages.categoryPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.MagentoDemoSite;
import magentodemo.components.ListProducts;
import magentodemo.components.PaginationMain;
import magentodemo.components.PanelNavigation;
import magentodemo.pages.BaseMagentoDemoPage;
import org.openqa.selenium.By;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.ComboBox;
import ui.core.controls.SelectComboBox;

@Getter
@Accessors(fluent = true)
public class CategoryPage extends BaseMagentoDemoPage<CategoryPage> {
	private final PanelNavigation panelNavigation;
	private final ComboBox comboBoxSortBy;
	private final Button buttonSortBy;
	private final ListProducts listProducts;
	private final PaginationMain pagination;

	public CategoryPage(MagentoDemoSite site, String department, String category) {
		super(site, String.format("%s/%s.html", department, category));

		panelNavigation = new PanelNavigation(site.webDriver);
		comboBoxSortBy = new SelectComboBox(site.webDriver, By.id("sorter"));
		buttonSortBy = new Button(site.webDriver, By.xpath("//a[@data-role='direction-switcher']"));
		this.listProducts = new ListProducts(
				new Locator(site.webDriver, By.xpath("//ol[@class='products list items product-items']")),
				"//li[@class='item product product-item']");
		pagination = new PaginationMain(site.webDriver);
	}
}
