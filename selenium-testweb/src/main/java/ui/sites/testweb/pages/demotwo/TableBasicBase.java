package ui.sites.testweb.pages.demotwo;

import ui.core.Locator;
import ui.core.controls.ListControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class TableBasicBase extends ListControl<TableBasicBase> {


	public TableBasicBase(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = ".//tbody/tr";
	}

	protected void initialize() {
	}
}