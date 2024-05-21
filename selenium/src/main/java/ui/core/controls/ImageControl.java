package ui.core.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.core.Locator;

public class ImageControl extends BaseControl {

	public ImageControl(WebDriver driver, By locator) {
		super(driver, locator);
	}

	public ImageControl(Locator locator) {
		super(locator);
	}

	public String getSrc() {
		return getAttribute("src");
	}

	public String getAlt() {
		return getAttribute("alt");
	}

	public void click() {
		locator.click();
	}

	public void assertSourceContains(String expected) {
		Assert.assertTrue(getSrc().contains(expected), "Expected source to contain " + expected + " but found " + getSrc());
	}

	public void assertAltContains(String expected) {
		Assert.assertTrue(getAlt().contains(expected), "Expected alt to contain " + expected + " but found " + getAlt());
	}
}
