package ui.sites.testweb.pages;

import lombok.Getter;
import ui.core.BasePage;
import ui.sites.testweb.TestWebSite;

public abstract class BaseTestWebPage<T> extends BasePage<T> {

	@Getter
	protected TestWebSite site;

	public BaseTestWebPage(TestWebSite site, String path) {
		super(site.webDriver, site.baseUrl, path);
		this.site = site;
	}

}
