package ui.core.controls;

import lombok.Setter;
import org.openqa.selenium.WebDriver;
import utilities.SystemHelper;

/*
Use this for cases where there are multiple buttons that are the same except for some text reference.  Using this,
you can just define the button once.
 */
public class TaggedButton extends BaseControl {

    /*
    Some buttons may require a delay before they become responsive to a click event.  Adjust this if needed for
    individual controls.
     */
    @Setter
    private int clickDelay = 0;

    public TaggedButton(WebDriver webDriver, String xpath) {
        super(webDriver, xpath);
    }

    public TaggedButton click(String item) {
        if (clickDelay > 0) {
            SystemHelper.sleep(clickDelay);
        }
        getLocator(item).click();
        return this;
    }

}
