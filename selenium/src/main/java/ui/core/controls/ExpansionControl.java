package ui.core.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
Use this for controls where there is a label that, when clicked, will expand to show a list of child labels/elements.
This control is designed for generic access, meaning it should only be declared once for any collection of expansion
elements where the only thing that changes is a text identifier.  For example, if there is a side-bar with 10
expansion elements that are all structurally the same with just text content differences, then for the page or
component where this needs to be defined, only create a single expansion control.
 */
public class ExpansionControl extends BaseControl {
	private final String itemLocator;

	public ExpansionControl(WebDriver webDriver, String expansionLocator, String itemLocator) {
		super(webDriver, expansionLocator);
		this.itemLocator = itemLocator;
	}

	public ExpansionControl expand(String controlText) {
		getLocator(controlText).click();
		return this;
	}

	public ExpansionControl selectItem(String controlText, String itemText) {
		expand(controlText);
		getLocator(controlText).withNext(By.xpath(String.format(itemLocator, itemText))).click();
		return this;
	}
}
