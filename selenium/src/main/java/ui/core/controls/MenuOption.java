package ui.core.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.Locator;

public class MenuOption extends BaseControl {

	public MenuOption(WebDriver webDriver, By by) {
		super(webDriver, by);
	}

	public MenuOption(Locator locator) {
		super(locator);
	}

	public void click() {
		locator.click();
	}

	public void Hover() {
		locator.hover();
	}

	public String getText() {
		return locator.getText();
	}

}
