package ui.sites.testweb.pages.demothree;

import lombok.Getter;
import lombok.experimental.Accessors;
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
		this.buttonViewDetails = new Button(page.getByTestId("Button-ViewDetails"));
		this.panelDetails = new PanelDetails(page);
		this.buttonAlert = new Button(page.getByTestId("Button-Alert"));
		this.buttonConfirm = new Button(page.getByTestId("Button-Confirm"));
		this.labelChoice = new Label(page.getByTestId("Label-Choice"));
		this.buttonPrompt = new Button(page.getByTestId("Button-Prompt"));
		this.labelAnswer = new Label(page.getByTestId("Label-Answer"));
	}
}