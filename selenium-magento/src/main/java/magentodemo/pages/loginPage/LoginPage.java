package magentodemo.pages.loginPage;

import magentodemo.MagentoDemoSite;
import magentodemo.domain.AuthBody;
import magentodemo.pages.BaseMagentoDemoPage;
import org.openqa.selenium.By;
import ui.core.controls.Button;
import ui.core.controls.LinkControl;
import ui.core.controls.TextBox;

public class LoginPage extends BaseMagentoDemoPage<LoginPage> {
	private final TextBox textBoxEmail;
	private final TextBox textBoxPassword;
	private final Button buttonLogin;
	private final LinkControl linkForgotYourPassword;
	private final Button buttonCreateAnAccount;

	public LoginPage(MagentoDemoSite site) {
		super(site, "customer/account/login/");

		textBoxEmail = new TextBox(this.getWebDriver(), By.id("email"));
		textBoxPassword = new TextBox(this.getWebDriver(), By.id("pass"));
		buttonLogin = new Button(this.getWebDriver(), By.id("send2"));
		linkForgotYourPassword = new LinkControl(this.getWebDriver(), By.linkText("Forgot Your Password?"));
		buttonCreateAnAccount = new Button(this.getWebDriver(), By.linkText("Create an Account"));
	}

	public LoginPage login(AuthBody authBody, BaseMagentoDemoPage pageAfterLogin) {
		this.open();
		textBoxEmail.setText(authBody.username());
		textBoxPassword.setText(authBody.password());
		buttonLogin.click();

		pageAfterLogin.assertIsOpen();
		return this;
	}
}
