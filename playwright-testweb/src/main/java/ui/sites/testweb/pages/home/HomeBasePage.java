package ui.sites.testweb.pages.home;

import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.TextBox;
import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.BaseTestWebPage;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class HomeBasePage<T> extends BaseTestWebPage<T> {
    public Label labelSomeText;
    public TextBox textBoxWhatever;
    public Label labelEmail;
    public TextBox textBoxEmail;
    public Label labelNumber;
    public TextBox textBoxNumber;
    public Label labelDate;
    public TextBox textBoxDate;
    public Label labelPhone;
    public TextBox textBoxPhone;
    public Label labelMessage;
    public TextBox textBoxMessage;
    public Label labelPassword;
    public TextBox textBoxPassword;
    public Label labelPasswordInstructions;
    public Button buttonSubmit;
    public Button buttonReset;


    public HomeBasePage(TestWebSite portal) {
        super(portal, "home.html");
        initialize();
    }

    protected void initialize() {
        this.labelSomeText = new Label(page.getByTestId("Label-SomeText"));
        this.textBoxWhatever = new TextBox(page.getByTestId("TextBox-Whatever"));
        this.labelEmail = new Label(page.getByTestId("Label-Email"));
        this.textBoxEmail = new TextBox(page.getByTestId("TextBox-Email"));
        this.labelNumber = new Label(page.getByTestId("Label-Number"));
        this.textBoxNumber = new TextBox(page.getByTestId("TextBox-Number"));
        this.labelDate = new Label(page.getByTestId("Label-Date"));
        this.textBoxDate = new TextBox(page.getByTestId("TextBox-Date"));
        this.labelPhone = new Label(page.getByTestId("Label-Phone"));
        this.textBoxPhone = new TextBox(page.getByTestId("TextBox-Phone"));
        this.labelMessage = new Label(page.getByTestId("Label-Message"));
        this.textBoxMessage = new TextBox(page.getByTestId("TextBox-Message"));
        this.labelPassword = new Label(page.getByTestId("Label-Password"));
        this.textBoxPassword = new TextBox(page.getByTestId("TextBox-Password"));
        this.labelPasswordInstructions = new Label(page.getByTestId("Label-PasswordInstructions"));
        this.buttonSubmit = new Button(page.getByTestId("Button-Submit"));
        this.buttonReset = new Button(page.getByTestId("Button-Reset"));
    }
}