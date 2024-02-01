package testweb.pages.demoone;

import ui.core.Locator;
import ui.core.controls.ListControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public class ListOneBase extends ListControl {




    /**
     * List Constructor.
     * @param locator locator
     */
    public ListOneBase(Locator locator) {
        super(locator);
        this.hasHeader = false;
        this.rowLocatorPattern = ".//li";
    }


	protected void initialize() {
	}
}