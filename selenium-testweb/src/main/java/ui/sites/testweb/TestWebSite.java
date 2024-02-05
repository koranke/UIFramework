package ui.sites.testweb;

import enums.TargetBrowser;
import ui.sites.testweb.pages.demoone.DemoOnePage;
import ui.core.Site;
import ui.sites.testweb.pages.demothree.DemoThreePage;
import ui.sites.testweb.pages.demotwo.DemoTwoPage;
import ui.sites.testweb.pages.home.HomePage;

public class TestWebSite extends Site<TestWebSite> {
	private HomePage homePage;
	private DemoOnePage demoOnePage;
	private DemoTwoPage demoTwoPage;
	private DemoThreePage demoThreePage;

	public TestWebSite() {
		super();
		initialize();
	}

	public TestWebSite(TargetBrowser targetBrowser) {
		super(targetBrowser);
		initialize();
	}

	private void initialize() {
		baseUrl = TestWebConstants.baseUrl;
	}

	public HomePage homePage() {
		if (homePage == null) {
			homePage = new HomePage(this);
		}
		return homePage;
	}

	public DemoOnePage demoOnePage() {
		if (demoOnePage == null) {
			demoOnePage = new DemoOnePage(this);
		}
		return demoOnePage;
	}

	public DemoTwoPage demoTwoPage() {
		if (demoTwoPage == null) {
			demoTwoPage = new DemoTwoPage(this);
		}
		return demoTwoPage;
	}

	public DemoThreePage demoThreePage() {
		if (demoThreePage == null) {
			demoThreePage = new DemoThreePage(this);
		}
		return demoThreePage;
	}
}
