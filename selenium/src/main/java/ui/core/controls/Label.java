package ui.core.controls;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.Locator;

public class Label extends BaseControl implements TextControl {

    public Label(WebDriver webDriver, By by) {
        super(webDriver, by);
    }
    public Label(Locator locator) {
        super(locator);
    }

    public void click() {
        locator.click();
    }

    public String getText() {
        return locator.getText().trim();
    }
}
