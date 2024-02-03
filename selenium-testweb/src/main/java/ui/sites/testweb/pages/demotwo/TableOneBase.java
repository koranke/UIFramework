package ui.sites.testweb.pages.demotwo;

import ui.core.Locator;
import ui.core.controls.FlagControl;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public class TableOneBase extends ListControl {
	public RepeatingControl<FlagControl> checkBoxDoIt;




    /**
     * List Constructor.
     * @param locator locator
     */
    public TableOneBase(Locator locator) {
        super(locator);
        this.hasHeader = false;
        this.rowLocatorPattern = ".//tbody/tr";
    }


	protected void initialize() {
		
        this.checkBoxDoIt = new RepeatingControl<>(
                locator,
                "CheckBox-DoIt",
                LocatorMethod.DATA_TEST_ID,
                FlagControl::new,
                rowLocatorPattern,
                hasHeader
        );
	}
}