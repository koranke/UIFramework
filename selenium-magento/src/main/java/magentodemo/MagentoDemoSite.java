package magentodemo;

import magentodemo.pages.homePage.HomePage;
import magentodemo.pages.searchResultsPage.SearchResultsPage;
import magentodemo.pages.whatIsNewPage.WhatIsNewPage;
import ui.core.Site;

public class MagentoDemoSite extends Site<MagentoDemoSite> {
	private HomePage homePage;
	private WhatIsNewPage whatIsNewPage;
	private SearchResultsPage searchResultsPage;

	public MagentoDemoSite() {
		super();
		initialize();
	}

	private void initialize() {
		this.baseUrl = MagentoDemoConstants.baseUrl;
	}

	public HomePage homePage() {
		if (homePage == null) {
			homePage = new HomePage(this);
		}
		return homePage;
	}

	public WhatIsNewPage whatIsNewPage() {
		if (whatIsNewPage == null) {
			whatIsNewPage = new WhatIsNewPage(this);
		}
		return whatIsNewPage;
	}

	public SearchResultsPage searchResultsPage() {
		if (searchResultsPage == null) {
			searchResultsPage = new SearchResultsPage(this);
		}
		return searchResultsPage;
	}
}
