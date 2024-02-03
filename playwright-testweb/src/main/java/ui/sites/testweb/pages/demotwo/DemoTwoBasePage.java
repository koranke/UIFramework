package ui.sites.testweb.pages.demotwo;

import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.BaseTestWebPage;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class DemoTwoBasePage<T> extends BaseTestWebPage<T> {
	public TableBasic tableBasic;
	public TableOne tableOne;
	public TableTwo tableTwo;


	public DemoTwoBasePage(TestWebSite portal) {
		super(portal, "demopage2.html");
		initialize();
	}

	protected void initialize() {
		this.tableBasic = new TableBasic(page.getByTestId("Table-Basic"));
		this.tableOne = new TableOne(page.getByTestId("Table-One"));
		this.tableTwo = new TableTwo(page.getByTestId("Table-Two"));
	}
}