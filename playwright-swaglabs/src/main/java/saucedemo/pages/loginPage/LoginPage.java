package saucedemo.pages.loginPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.experimental.Accessors;
import saucedemo.SauceDemoSite;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.TextBox;
import saucedemo.pages.BaseSauceDemoPage;

import java.util.ArrayList;
import java.util.List;

@Getter()
@Accessors(fluent = true)
public class LoginPage extends BaseSauceDemoPage<LoginPage> {
	private final TextBox textBoxUserName;
	private final TextBox textBoxPassword;
	private final Label labelError;
	private final Button buttonLogin;

	public LoginPage(SauceDemoSite site) {
		super(site, "");
		textBoxUserName = new TextBox(page.locator("#user-name"));
		textBoxPassword = new TextBox(page.locator("#password"));
		labelError = new Label(page.locator("css=[data-test='error']"));
		buttonLogin = new Button(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")));
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

	public List<Locator> getMask() {
		List<Locator> locators = new ArrayList<>();
		locators.add(textBoxUserName.getLocator());
		locators.add(textBoxPassword.getLocator());
		return locators;
	}

}
