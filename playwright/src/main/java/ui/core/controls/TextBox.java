package ui.core.controls;

import com.microsoft.playwright.Locator;

public class TextBox extends BaseControl implements TextControl {
    public TextBox(Locator locator) {
        super(locator);
    }

    public TextBox setText(String text) {
        log.debug(String.format("Setting text for a textbox: [%s].", text));
        locator.fill(text);
        return this;
    }

    /*
    Use this if the textbox includes validation logic while typing.
     */
    public TextBox typeText(String text) {
        log.debug(String.format("Setting text for a textbox: [%s].", text));
        locator.type(text);
        return this;
    }

    public String getText() {
        return locator.inputValue();
    }
}
