package ui.sites.testweb.pages.demotwo;

import ui.core.Locator;
import ui.core.controls.ListControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public class TableBasicBase extends ListControl {




    /**
     * List Constructor.
     * @param locator locator
     */
    public TableBasicBase(Locator locator) {
        super(locator);
        this.hasHeader = false;
        this.rowLocatorPattern = ".//tr";
    }


	protected void initialize() {
	}
}