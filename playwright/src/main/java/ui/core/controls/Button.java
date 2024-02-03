package ui.core.controls;

import com.microsoft.playwright.Locator;
import lombok.Setter;

public class Button extends BaseControl {
    /*
    Some buttons may require a delay before they become responsive to a click event.  Adjust this if needed for
    individual controls.
     */
    @Setter
    private int clickDelay = 0;

    public Button(Locator locator) {
        super(locator);
    }

    public Button click() {
        log.debug("Clicking a button.");
        if (clickDelay > 0) {
            wait(clickDelay);
        }
        locator.click();
        return this;
    }

    public String getLabel() {
        String label = locator.getAttribute("value");
        if (label == null) {
            label = locator.textContent();
        }
        return label;
    }

}
