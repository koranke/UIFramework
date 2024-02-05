package ui.sites.testweb.pages.home;

import lombok.Getter;
import lombok.experimental.Accessors;
import ui.core.ExtendedBy;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.TextBox;
import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.BaseTestWebPage;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
@Getter
@Accessors(fluent = true)
public abstract class HomeBasePage<T> extends BaseTestWebPage<T> {
	private Label labelSomeText;
	private TextBox textBoxWhatever;
	private Label labelEmail;
	private TextBox textBoxEmail;
	private Label labelNumber;
	private TextBox textBoxNumber;
	private Label labelDate;
	private TextBox textBoxDate;
	private Label labelPhone;
	private TextBox textBoxPhone;
	private Label labelMessage;
	private TextBox textBoxMessage;
	private Label labelPassword;
	private TextBox textBoxPassword;
	private Label labelPasswordInstructions;
	private Button buttonSubmit;
	private Button buttonReset;


	public HomeBasePage(TestWebSite portal) {
		super(portal, "home.html");
		initialize();
	}

	protected void initialize() {
		this.labelSomeText = new Label(site.webDriver, ExtendedBy.testId("Label-SomeText"));
		this.textBoxWhatever = new TextBox(site.webDriver, ExtendedBy.testId("TextBox-Whatever"));
		this.labelEmail = new Label(site.webDriver, ExtendedBy.testId("Label-Email"));
		this.textBoxEmail = new TextBox(site.webDriver, ExtendedBy.testId("TextBox-Email"));
		this.labelNumber = new Label(site.webDriver, ExtendedBy.testId("Label-Number"));
		this.textBoxNumber = new TextBox(site.webDriver, ExtendedBy.testId("TextBox-Number"));
		this.labelDate = new Label(site.webDriver, ExtendedBy.testId("Label-Date"));
		this.textBoxDate = new TextBox(site.webDriver, ExtendedBy.testId("TextBox-Date"));
		this.labelPhone = new Label(site.webDriver, ExtendedBy.testId("Label-Phone"));
		this.textBoxPhone = new TextBox(site.webDriver, ExtendedBy.testId("TextBox-Phone"));
		this.labelMessage = new Label(site.webDriver, ExtendedBy.testId("Label-Message"));
		this.textBoxMessage = new TextBox(site.webDriver, ExtendedBy.testId("TextBox-Message"));
		this.labelPassword = new Label(site.webDriver, ExtendedBy.testId("Label-Password"));
		this.textBoxPassword = new TextBox(site.webDriver, ExtendedBy.testId("TextBox-Password"));
		this.labelPasswordInstructions = new Label(site.webDriver, ExtendedBy.testId("Label-PasswordInstructions"));
		this.buttonSubmit = new Button(site.webDriver, ExtendedBy.testId("Button-Submit"));
		this.buttonReset = new Button(site.webDriver, ExtendedBy.testId("Button-Reset"));
	}
}