package ui.core.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ui.core.Locator;

import java.util.List;
import java.util.stream.Collectors;
//import static org.assertj.core.api.Assertions.assertThat;

public class SelectComboBox extends BaseControl implements ComboBox {

    public SelectComboBox(WebDriver webDriver, By by) {
        super(webDriver, by);
    }

    public SelectComboBox(Locator locator) {
        super(locator);
    }

    public void setValue(String value) {
        log.debug("Setting a value for a combo box.");
        getAsSelect().selectByValue(value);
    }

    public void setText(String option) {
        log.debug("Setting text for a combo box.");
        getAsSelect().selectByVisibleText(option);
    }

    public void setText(int textIndex) {
        getAsSelect().selectByIndex(textIndex);
    }

    public String getText() {
        return getAsSelect().getFirstSelectedOption().getText();
    }

    public String getValue() {
        return locator.getAttribute("value");
    }

    public List<String> getOptions() {
        return getAsSelect().getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void assertText(String expectedText) {
        if (!getText().equals(expectedText)) {
            wait(1000);
        }
        Assert.assertEquals(getText(), expectedText);
    }

    public void assertValue(String expectedValue) {
        if (!getValue().equals(expectedValue)) {
            wait(1000);
        }
        Assert.assertEquals(getValue(), expectedValue);
    }

    public void assertOptions(List<String> expectedOptions) {
//        assertThat(getOptions()).containsExactlyInAnyOrderElementsOf(expectedOptions);
    }

    public boolean optionExists(String targetText) {
        return getOptions().contains(targetText);
    }

    private Select getAsSelect() {
        return new Select(locator.getElement());
    }
}
