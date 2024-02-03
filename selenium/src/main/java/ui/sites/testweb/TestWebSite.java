package ui.sites.testweb;

import enums.TargetBrowser;
import ui.core.Site;

public class TestWebSite extends Site<TestWebSite> {

	public TestWebSite() {
		super();
		initialize();
	}

	public TestWebSite(TargetBrowser targetBrowser) {
		super(targetBrowser);
		initialize();
	}

	private void initialize() {
		baseUrl = TestWebSiteConstants.baseUrl;
	}

}
