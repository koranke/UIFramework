package ui.sites.testweb.pages.demoone;

import ui.core.Locator;
import ui.core.controls.ListControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class ListTwoBase extends ListControl<ListTwoBase> {


	public ListTwoBase(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = ".//li";
	}

	protected void initialize() {
	}
}