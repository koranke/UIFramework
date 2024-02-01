package testweb.pages.demothree;

import testweb.TestWebSite;
import testweb.pages.BaseTestWebPage;
import ui.core.ExtendedBy;
import ui.core.controls.Button;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class DemoThreeBasePage<T> extends BaseTestWebPage<T> {
	public Button buttonViewDetails;
	public PanelDetails panelDetails;


	public DemoThreeBasePage(TestWebSite portal) {
		super(portal, "demopage3.html");
		initialize();
	}

	protected void initialize() {
		this.buttonViewDetails = new Button(site.webDriver, ExtendedBy.testId("Button-ViewDetails"));
		this.panelDetails = new PanelDetails(site.webDriver);
	}
}