package ui.core.controls;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/*
Use this for cases where there are multiple labels that are the same except for some text reference, and where the text
value possibilities are numerous.  Using this, you can just define the label once.
 */
public class TaggedLabel extends BaseControl {

    public TaggedLabel(WebDriver webDriver, String xpath) {
        super(webDriver, xpath);
    }

    public boolean isVisible(String item) {
        return getLocator(item).isVisible();
    }

    public void assertIsVisible(String item) {
        Assert.assertTrue(getLocator(item).isVisible());
    }

    public void assertIsNotVisible(String item) {
        Assert.assertTrue(getLocator(item).isNotVisible());
    }
}
