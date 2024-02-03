package ui.core.controls;

import java.util.List;

public interface ComboBox {

    void setText(String option);

    void setText(int textIndex);

    String getText();

    List<String> getOptions();

    boolean optionExists(String targetText);

    void assertText(String expectedText);

    void assertIsEnabled();

    void assertIsNotEnabled();

    void assertIsVisible();

    void assertOptions(List<String> expectedOptions);
}
