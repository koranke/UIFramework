package testweb.pages;

import lombok.Getter;
import testweb.TestWebSite;
import ui.core.BasePage;

public abstract class BaseTestWebPage<T> extends BasePage<T> {

	@Getter
	protected TestWebSite site;

	public BaseTestWebPage(TestWebSite site, String path) {
		super(site.webDriver, site.baseUrl, path);
		this.site = site;
	}

}
