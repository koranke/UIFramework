package ui.sites.testweb.pages.demotwo;

import lombok.Getter;
import lombok.experimental.Accessors;
import ui.core.ExtendedBy;
import ui.core.Locator;
import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.BaseTestWebPage;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
@Getter
@Accessors(fluent = true)
public abstract class DemoTwoBasePage<T> extends BaseTestWebPage<T> {
	private TableBasic tableBasic;
	private TableOne tableOne;
	private TableTwo tableTwo;


	public DemoTwoBasePage(TestWebSite portal) {
		super(portal, "demopage2.html");
		initialize();
	}

	protected void initialize() {
		this.tableBasic = new TableBasic(new Locator(site.webDriver, ExtendedBy.testId("Table-Basic")));
		this.tableOne = new TableOne(new Locator(site.webDriver, ExtendedBy.testId("Table-One")));
		this.tableTwo = new TableTwo(new Locator(site.webDriver, ExtendedBy.testId("Table-Two")));
	}
}