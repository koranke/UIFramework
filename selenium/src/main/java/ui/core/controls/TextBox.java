package ui.core.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.Locator;

public class TextBox extends BaseControl {

    public TextBox(WebDriver webDriver, By locator) {
        super(webDriver, locator);
    }

    public TextBox(Locator locator) {
        super(locator);
    }

    public TextBox setText(String text) {
        log.debug(String.format("Setting text for a textbox: [%s].", text));
        locator.setText(text);
        return this;
    }

    public String getText() {
        return locator.getAttribute("value");
    }
}
