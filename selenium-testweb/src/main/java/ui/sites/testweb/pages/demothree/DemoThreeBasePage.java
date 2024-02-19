package ui.sites.testweb.pages.demothree;

import lombok.Getter;
import lombok.experimental.Accessors;
import ui.core.ExtendedBy;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.BaseTestWebPage;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
@Getter
@Accessors(fluent = true)
public abstract class DemoThreeBasePage<T> extends BaseTestWebPage<T> {
	private Button buttonViewDetails;
	private PanelDetails panelDetails;
	private Button buttonAlert;
	private Button buttonConfirm;
	private Label labelChoice;
	private Button buttonPrompt;
	private Label labelAnswer;


	public DemoThreeBasePage(TestWebSite portal) {
		super(portal, "demopage3.html");
		initialize();
	}

	protected void initialize() {
		this.buttonViewDetails = new Button(site.webDriver, ExtendedBy.testId("Button-ViewDetails"));
		this.panelDetails = new PanelDetails(site.webDriver);
		this.buttonAlert = new Button(site.webDriver, ExtendedBy.testId("Button-Alert"));
		this.buttonConfirm = new Button(site.webDriver, ExtendedBy.testId("Button-Confirm"));
		this.labelChoice = new Label(site.webDriver, ExtendedBy.testId("Label-Choice"));
		this.buttonPrompt = new Button(site.webDriver, ExtendedBy.testId("Button-Prompt"));
		this.labelAnswer = new Label(site.webDriver, ExtendedBy.testId("Label-Answer"));
	}
}