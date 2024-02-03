package ui.core.controls;

import com.microsoft.playwright.Locator;
import org.testng.Assert;

public class FlagControl extends BaseControl {
    public FlagControl(Locator locator) {
        super(locator);
    }

    public FlagControl click() {
        locator.click();
        return this;
    }

    public String getLabel() {
        return locator.textContent().trim();
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
