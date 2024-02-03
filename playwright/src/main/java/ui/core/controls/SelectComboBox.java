package ui.core.controls;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import org.testng.Assert;

import java.util.List;
//import static org.assertj.core.api.Assertions.assertThat;

public class SelectComboBox extends BaseControl implements ComboBox {

    public SelectComboBox(Locator locator) {
        super(locator);
    }

    public void setText(String option) {
        locator.selectOption(option);
    }

    public void setText(int textIndex) {
        locator.selectOption(new SelectOption().setIndex(textIndex));
    }

    public String getText() {
        return locator.inputValue();
    }

    public List<String> getOptions() {
        return List.of(locator.innerText().split("\n"));
    }

    public void assertText(String expectedText) {
        if (!getText().equals(expectedText)) {
            wait(1000);
        }
        Assert.assertEquals(getText(), expectedText);
    }

    public void assertOptions(List<String> expectedOptions) {
//        assertThat(getOptions()).containsExactlyInAnyOrderElementsOf(expectedOptions);
    }

    public boolean optionExists(String targetText) {
        return getOptions().contains(targetText);
    }

}
