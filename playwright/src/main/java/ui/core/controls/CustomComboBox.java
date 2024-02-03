package ui.core.controls;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import ui.core.enums.KeyboardKey;

import java.util.List;

public class CustomComboBox extends BaseControl implements ComboBox {
    private final Locator button;
    private final Locator textBox;
    private final Locator textSelection;
    private String textSelectionPattern = "//div[%d]";

    public CustomComboBox(Locator button, Locator textBox, Locator textSelection) {
        super(button);
        this.button = button;
        this.textBox = textBox;
        this.textSelection = textSelection;
    }

    public String getText() {
        return textBox.textContent();
    }

    public void setText(String targetText) {
        button.click();
        textSelection.getByText(targetText).click();
    }

    /**
     * setText.
     * @param textIndex textIndex.  Starts at 1, not 0.
     */
    public void setText(int textIndex) {
        button.click();
        /*
        The portal currently doesn't fully support selecting an option using keyboard shortcuts.
        "Enter" fails to select the highlighted item.  Uncomment if support is added eventually.
         */

        // for (int i = 0; i < textIndex; i++) {
        //     button.page().keyboard().press(KeyboardKey.DOWN_ARROW.getName());
        // }

        //This likely will not work everywhere.
        textSelection.locator(String.format(textSelectionPattern, textIndex)).click();
    }

    public void assertText(String expectedText) {
        if (!getText().equals(expectedText)) {
            wait(1000);
        }
        Assert.assertEquals(getText(), expectedText);
    }

    //TODO: add support for this
    public List<String> getOptions() {
        return null;
    }

    //TODO: add support for this
    public void assertOptions(List<String> expectedOptions) {
    }

    public boolean optionExists(String targetText) {
        button.click();
        boolean result = textSelection.getByText(targetText).count() > 0;
        button.page().keyboard().press(KeyboardKey.ESCAPE.getName());
        return result;
    }

    public CustomComboBox withTextSelectionPattern(String textSelectionPattern) {
        this.textSelectionPattern = textSelectionPattern;
        return this;
    }
}
