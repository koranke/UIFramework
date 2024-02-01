package ui.core.controls;

import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.Locator;
import utilities.SystemHelper;

public class Button extends BaseControl {

    /*
    Some buttons may require a delay before they become responsive to a click event.  Adjust this if needed for
    individual controls.
     */
    @Setter
    private int clickDelay = 0;

    public Button(WebDriver webDriver, By locator) {
        super(webDriver, locator);
    }

    public Button(Locator locator) {
        super(locator);
    }

    public Button click() {
        log.debug("Clicking a button.");
        if (clickDelay > 0) {
            SystemHelper.sleep(clickDelay);
        }
        locator.click();
        return this;
    }

    public String getLabel() {
        String label = locator.getDomAttribute("value");
        if (label == null) {
            label = locator.getText();
        }
        return label;
    }
}
