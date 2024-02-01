package ui.core.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.core.Locator;

public class FlagControl extends BaseControl {

    public FlagControl(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    public FlagControl(Locator locator) {
        super(locator);
    }

    public FlagControl click() {
        locator.click();
        return this;
    }

    public String getLabel() {
        return locator.getText().trim();
    }

    public boolean isSelected() {
        return locator.isChecked();
    }

    public FlagControl assertIsSelected() {
        Assert.assertTrue(locator.isChecked(), "Option not selected.");
        return this;
    }

    public FlagControl assertIsNotSelected() {
        Assert.assertFalse(locator.isChecked(), "Option is selected.");
        return this;
    }
}
