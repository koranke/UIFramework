package ui.core.controls;

import configuration.Props;
import lombok.Getter;
import org.aeonbits.owner.ConfigCache;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.core.Locator;
import utilities.Log;
import utilities.SystemHelper;

public abstract class BaseControl {

    @Getter
    protected Locator locator;
    protected Props props = ConfigCache.getOrCreate(Props.class);
    protected Log log = Log.getInstance();

    public BaseControl(WebDriver webDriver, By by) {
        this.locator = new Locator(webDriver, by);
    }

    public BaseControl(Locator locator) {
        this.locator = locator;
    }

    public boolean isEnabled() {
        return locator.isEnabled();
    }

    public void assertIsEnabled() {
        Assert.assertTrue(isEnabled(), "Unexpected enabled state.");
    }

    public void assertIsNotEnabled() {
        Assert.assertTrue(!isEnabled(), "Unexpected enabled state.");
    }

    public boolean isVisible() {
        return locator.isVisible(props.visibilityWaitInSeconds());
    }

    public boolean isNotVisible() {
        return locator.isNotVisible(props.visibilityWaitInSeconds());
    }

    public void assertIsVisible(int maxWaitTime) {
        Assert.assertTrue(locator.isVisible(maxWaitTime), "Control is not visible.");
    }

    public void assertIsVisible() {
        Assert.assertTrue(isVisible(), "Control is not visible.");
    }

    public void assertIsNotVisible(int maxWaitTimeInSeconds) {
        Assert.assertTrue(locator.isNotVisible(maxWaitTimeInSeconds), "Control is visible.");
    }

    public void assertIsNotVisible() {
        Assert.assertTrue(isNotVisible(), "Control is visible.");
    }

    public void waitForControl() {
        this.locator.isVisible();
    }

    public void assertText(String text) {
        if (!getActualText(locator).equals(text)) {
            wait(1000);
        }
        Assert.assertEquals(getActualText(locator), text);
    }

    public void assertTextContains(String text) {
        if (!getActualText(locator).equals(text)) {
            wait(1000);
        }
        Assert.assertTrue(getActualText(locator).contains(text));
    }

    private String getActualText(Locator locator) {
        String actualText;
        if (this.getClass().isAssignableFrom(TextBox.class)) {
            actualText = locator.getAttribute("value");
        } else if (this.getClass().isAssignableFrom(Button.class)) {
            actualText = ((Button) this).getLabel();
        } else {
            actualText = locator.getText();
        }
        return actualText == null ? "" : actualText;
    }

//    public void assertValue(String text) {
//        if (!locator.inputValue().trim().equals(text)) {
//            wait(1000);
//        }
//        Assert.assertEquals(locator.inputValue().trim(), text);
//    }

    public void wait(int timeToWait) {
        SystemHelper.sleep(timeToWait);
    }

    public void hover() {
        locator.hover();
    }

    public void scrollToElement() {
        locator.scrollToElement();
    }

    public String getAttribute(String attributeName) {
        return locator.getAttribute(attributeName);
    }
}
