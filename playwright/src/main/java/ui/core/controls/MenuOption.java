package ui.core.controls;

import com.microsoft.playwright.Locator;
import org.testng.Assert;

public class MenuOption extends BaseControl {
    public MenuOption(Locator locator) {
        super(locator);
    }

    public MenuOption click() {
        locator.click();
        return this;
    }

    public MenuOption hover() {
        locator.hover();
        return this;
    }

    public String getLabel() {
        return locator.textContent();
    }

    public void assertIsVisible() {
        Assert.assertTrue(locator.isVisible(), "Failed to find menu item.");
    }

    public void assertIsNotVisible() {
        Assert.assertFalse(locator.isVisible(), "Found menu item.");
    }
}
