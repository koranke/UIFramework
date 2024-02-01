package saucedemo.pages.loginPage;

import org.openqa.selenium.By;
import saucedemo.SauceDemoSite;
import saucedemo.pages.BaseSauceDemoPage;
import ui.core.controls.Button;
import ui.core.controls.TextBox;

public class LoginPage extends BaseSauceDemoPage<LoginPage> {
	public TextBox textBoxUserName;
	public TextBox textBoxPassword;
	public Button buttonLogin;

	public LoginPage(SauceDemoSite site) {
		super(site, "");
		textBoxUserName = new TextBox(site.webDriver, By.id("user-name"));
		textBoxPassword = new TextBox(site.webDriver, By.id("password"));
		buttonLogin = new Button(site.webDriver, By.id("login-button"));
	}

	public LoginPage signIn(String userName, String password) {
		goTo();
		this.textBoxUserName.setText(userName);
		this.textBoxPassword.setText(password);
		this.buttonLogin.click();

		site.setSignedIn(true);
		return this;
	}

	public LoginPage signIn() {
		return signIn("standard_user", "secret_sauce");
	}
}
