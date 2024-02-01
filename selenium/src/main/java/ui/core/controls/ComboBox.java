package ui.core.controls;

import java.util.List;

public interface ComboBox {

    void setValue(String value);
    void setText(String option);

    void setText(int textIndex);

    String getText();

    String getValue();

    List<String> getOptions();

    boolean optionExists(String targetText);

    void assertText(String expectedText);

    void assertValue(String expectedValue);

    void assertIsEnabled();

    void assertIsNotEnabled();

    void assertIsVisible();

    void assertOptions(List<String> expectedOptions);
}
