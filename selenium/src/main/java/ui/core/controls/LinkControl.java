package ui.core.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.core.Locator;

public class LinkControl extends BaseControl {

	public LinkControl(WebDriver driver, By locator) {
		super(driver, locator);
	}

	public LinkControl(Locator locator) {
		super(locator);
	}

	public String getHref() {
		return getAttribute("href");
	}

	public void click() {
		locator.click();
	}

	public void assertHref(String expected) {
		Assert.assertEquals(getHref(), expected, "Expected href to be " + expected + " but found " + getHref());
	}
}
