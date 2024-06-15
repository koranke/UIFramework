package magentodemo.components;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.controls.Button;
import ui.core.controls.ExpansionControl;
import ui.core.controls.PanelControl;
import ui.core.controls.TaggedButton;
import ui.core.controls.TaggedLabel;

@Getter
@Accessors(fluent = true)
public class PanelFilters extends PanelControl {
	private final ExpansionControl generalFilter;
	private final ExpansionControl sizeColorFilter;
	private final Button buttonClearAll;
	private final TaggedButton buttonClearItem;
	private final TaggedLabel labelFilterItem;


	public PanelFilters(WebDriver driver) {
		this.webDriver = driver;
		buttonClearAll = new Button(this.webDriver, By.xpath("//a[span[text()='Clear All']]"));
		buttonClearItem = new TaggedButton(this.webDriver, "//ol[@class='items']//span[text()='%s']/following-sibling::a");
		labelFilterItem = new TaggedLabel(this.webDriver, "//ol[@class='items']//span[@class='filter-value' and text()='%s']");
		generalFilter = new ExpansionControl(webDriver,
				"//div[text()='%s']",
				".//following-sibling::div[@data-role='content']/ol/li/a[normalize-space(text())='%s']");

		sizeColorFilter = new ExpansionControl(webDriver,
				"//div[text()='%s']",
				".//following-sibling::div[@data-role='content']//a[@aria-label='%s']/div");
	}
}
