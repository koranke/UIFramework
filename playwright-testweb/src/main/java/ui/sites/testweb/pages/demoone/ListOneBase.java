package ui.sites.testweb.pages.demoone;

import com.microsoft.playwright.Locator;
import ui.core.controls.ListControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class ListOneBase extends ListControl<ListOneBase> {


	public ListOneBase(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//li";
	}

	protected void initialize() {
	}
}