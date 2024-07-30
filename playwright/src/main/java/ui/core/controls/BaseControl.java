package ui.core.controls;

import com.microsoft.playwright.Locator;
import configuration.Props;
import org.aeonbits.owner.ConfigCache;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.testng.Assert;
import utilities.Log;

import java.time.Duration;

public abstract class BaseControl {
    protected Log log = Log.getInstance();
    protected Props props = ConfigCache.getOrCreate(Props.class);
    protected final Locator locator;

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
        try {
            Awaitility.await()
                    .atMost(Duration.ofSeconds(props.visibilityWaitInSeconds()))
                    .pollInterval(Duration.ofMillis(100))
                    .until(locator::isVisible);
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    public boolean isNotVisible() {
        try {
            Awaitility.await()
                    .atMost(Duration.ofSeconds(props.visibilityWaitInSeconds()))
                    .pollInterval(Duration.ofMillis(100))
                    .until(() -> !locator.isVisible());
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    public void assertIsVisible() {
        Assert.assertTrue(isVisible(), "Control is not visible.");
    }

    public void assertIsNotVisible() {
        Assert.assertTrue(isNotVisible(), "Control is visible.");
    }

    public Locator getLocator() {
        return this.locator;
    }

    public void waitForControl() {
        this.locator.focus();
    }

    public void assertText(String text) {
        if (!getActualText(locator).equals(text)) {
            wait(1000);
        }
        Assert.assertEquals(getActualText(locator), text);
    }

    public void assertTextContains(String text) {
        if (!getActualText(getLocator()).contains(text)) {
            wait(1000);
        }
        Assert.assertTrue(getActualText(getLocator()).contains(text));
    }


    private String getActualText(Locator locator) {
        String actualText;
        if (this.getClass().isAssignableFrom(TextBox.class)) {
            actualText = locator.inputValue().trim();
        } else if (this.getClass().isAssignableFrom(Button.class)) {
            actualText = ((Button) this).getLabel();
        } else {
            actualText = locator.textContent().trim();
        }
        return actualText;
    }

    public void assertValue(String text) {
        if (!locator.inputValue().trim().equals(text)) {
            wait(1000);
        }
        Assert.assertEquals(locator.inputValue().trim(), text);
    }

    public void wait(int timeToWait) {
        try {
            locator.page().waitForTimeout(timeToWait);
        } catch (Exception e) {
            //do nothing
        }
    }
}
