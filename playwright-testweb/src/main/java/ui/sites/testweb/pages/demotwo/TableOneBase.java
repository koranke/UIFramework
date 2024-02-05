package ui.sites.testweb.pages.demotwo;

import com.microsoft.playwright.Locator;
import ui.core.controls.FlagControl;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class TableOneBase extends ListControl<TableOneBase> {
	private RepeatingControl<FlagControl> checkBoxDoIt;


	public TableOneBase(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//tbody/tr";
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

	public FlagControl checkBoxDoIt() {
		return checkBoxDoIt.get(currentRow);
	}
}