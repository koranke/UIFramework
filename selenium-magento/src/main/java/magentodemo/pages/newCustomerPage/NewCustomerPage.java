package magentodemo.pages.newCustomerPage;

import magentodemo.MagentoDemoSite;
import magentodemo.components.PanelNavigation;
import magentodemo.domain.Account;
import magentodemo.pages.BaseMagentoDemoPage;
import org.openqa.selenium.By;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.TextBox;

public class NewCustomerPage extends BaseMagentoDemoPage<NewCustomerPage> {
	private final PanelNavigation panelNavigation;
	private final TextBox textBoxFirstName;
	private final TextBox textBoxLastName;
	private final TextBox textBoxEmail;
	private final TextBox textBoxPassword;
	private final TextBox textBoxConfirmPassword;
	private final Button buttonCreateAccount;

	private final Label labelFirstNameError;

	public NewCustomerPage(MagentoDemoSite site) {
		super(site, "customer/account/create/");
		this.panelNavigation = new PanelNavigation(site.getWebDriver());

		this.textBoxFirstName = new TextBox(site.getWebDriver(), By.id("firstname"));
		this.textBoxLastName = new TextBox(site.getWebDriver(), By.id("lastname"));
		this.textBoxEmail = new TextBox(site.getWebDriver(), By.id("email_address"));
		this.textBoxPassword = new TextBox(site.getWebDriver(), By.id("password"));
		this.textBoxConfirmPassword = new TextBox(site.getWebDriver(), By.id("password-confirmation"));
		this.buttonCreateAccount = new Button(site.getWebDriver(), By.xpath("//button[@title='Create an Account']"));

		this.labelFirstNameError = new Label(site.getWebDriver(), By.id("firstname-error"));
	}

	public NewCustomerPage open() {
		this.site.homePage().open();
		this.site.homePage().panelNavigation().linkCreateAccount().click();
		return this;
	}

	public Account createRandomAccount() {
		Account account = new Account().withDefaults();
		textBoxFirstName.setText(account.customer().firstname());
		textBoxLastName.setText(account.customer().lastname());
		textBoxEmail.setText(account.customer().email());
		textBoxPassword.setText(account.password());
		textBoxConfirmPassword.setText(account.password());
		buttonCreateAccount.click();
		return account;
	}
}
