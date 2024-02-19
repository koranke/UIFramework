package ui.core.controls;

import com.microsoft.playwright.Locator;
import lombok.Setter;
import org.testng.Assert;

public class Button extends BaseControl {
    /*
    Some buttons may require a delay before they become responsive to a click event.  Adjust this if needed for
    individual controls.
     */
    @Setter
    private int clickDelay = 0;
    @Setter
    private WebDialog dialog;

    public Button(Locator locator) {
        super(locator);
    }

    public Button click() {
        log.debug("Clicking a button.");
        if (clickDelay > 0) {
            wait(clickDelay);
        }

        if (dialog != null) {
            locator.page().onceDialog(dialog.getDialogConsumer());
        }

        locator.click();

        if (dialog != null) {
            Assert.assertEquals(dialog.getAlertMessage(), dialog.getExpectedAlertMessage());
        }

        return this;
    }

    public String getLabel() {
        String label = locator.getAttribute("value");
        if (label == null) {
            label = locator.textContent();
        }
        return label;
    }

    public Button withAlertAssert(String expectedMessage) {
        this.dialog = new AlertDialog().withExpectedAlertMessage(expectedMessage);
        return this;
    }

    public Button withConfirmAssert(String expectedMessage) {
        this.dialog = new ConfirmDialog().withExpectedAlertMessage(expectedMessage);
        return this;
    }

    public Button withConfirmCancelAssert(String expectedMessage) {
        this.dialog = new ConfirmCancelDialog().withExpectedAlertMessage(expectedMessage);
        return this;
    }

    public Button withPromptAssert(String expectedMessage, String response) {
        this.dialog = new PromptDialog().withExpectedAlertMessage(expectedMessage)
                .withPromptResponse(response);
        return this;
    }
}
