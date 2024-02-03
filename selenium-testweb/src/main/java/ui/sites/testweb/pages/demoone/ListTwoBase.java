package ui.sites.testweb.pages.demoone;

import ui.core.Locator;
import ui.core.controls.ListControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public class ListTwoBase extends ListControl {




    /**
     * List Constructor.
     * @param locator locator
     */
    public ListTwoBase(Locator locator) {
        super(locator);
        this.hasHeader = false;
        this.rowLocatorPattern = ".//li";
    }


	protected void initialize() {
	}
}