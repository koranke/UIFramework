package ui.core.controls;

import com.microsoft.playwright.Locator;

public class Label extends BaseControl implements TextControl {
    public Label(Locator locator) {
        super(locator);
    }

    public void click() {
        locator.click();
    }

    public String getText() {
        return locator.textContent().trim();
    }
}
