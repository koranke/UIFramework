package saucedemo.pages;

import lombok.Getter;
import saucedemo.SauceDemoSite;
import ui.core.BasePage;

public abstract class BaseSauceDemoPage<T> extends BasePage<T> {

	@Getter
	protected SauceDemoSite site;

	public BaseSauceDemoPage(SauceDemoSite site, String path) {
		super(site.webDriver, site.baseUrl, path);
		this.site = site;
	}

	/*
	Default open.  Replace in page-specific class with override that uses menu.
	 */
	public T open() {
		loginIfNeeded();
		return goTo();
	}

	protected void loginIfNeeded() {
		if (!site.isSignedIn()) {
			site.loginPage().signIn();
		}
	}

}
