package magentodemo.pages;

import lombok.Getter;
import magentodemo.MagentoDemoSite;
import ui.core.BasePage;

public abstract class BaseMagentoDemoPage<T> extends BasePage<T> {

	@Getter
	protected MagentoDemoSite site;

	public BaseMagentoDemoPage(MagentoDemoSite site, String path) {
		super(site.webDriver, site.baseUrl, path);
		this.site = site;
	}

	/*
	Default open.  Replace in page-specific class with override that uses menu.
	 */
	public T open() {
		return goTo();
	}

}
