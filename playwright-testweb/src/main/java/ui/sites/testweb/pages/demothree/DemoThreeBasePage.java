package ui.sites.testweb.pages.demothree;

import ui.core.controls.Button;
import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.BaseTestWebPage;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class DemoThreeBasePage<T> extends BaseTestWebPage<T> {
	public Button buttonViewDetails;
	public PanelDetails panelDetails;


	public DemoThreeBasePage(TestWebSite portal) {
		super(portal, "demopage3.html");
		initialize();
	}

	protected void initialize() {
		this.buttonViewDetails = new Button(page.getByTestId("Button-ViewDetails"));
		this.panelDetails = new PanelDetails(page);
	}
}