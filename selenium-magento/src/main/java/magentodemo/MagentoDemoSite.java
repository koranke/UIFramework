package magentodemo;

import lombok.Getter;
import lombok.Setter;
import magentodemo.pages.categoryPage.CategoryPage;
import magentodemo.pages.homePage.HomePage;
import magentodemo.pages.loginPage.LoginPage;
import magentodemo.pages.myAccountPage.MyAccountPage;
import magentodemo.pages.newCustomerPage.NewCustomerPage;
import magentodemo.pages.searchResultsPage.SearchResultsPage;
import magentodemo.pages.whatIsNewPage.WhatIsNewPage;
import ui.core.Site;

public class MagentoDemoSite extends Site<MagentoDemoSite> {
	@Getter
	@Setter
	private boolean isLoggedIn;
	private HomePage homePage;
	private LoginPage loginPage;
	private NewCustomerPage newCustomerPage;
	private MyAccountPage myAccountPage;
	private WhatIsNewPage whatIsNewPage;
	private SearchResultsPage searchResultsPage;
	private CategoryPage categoryPage;

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

	public NewCustomerPage newCustomerPage() {
		if (newCustomerPage == null) {
			newCustomerPage = new NewCustomerPage(this);
		}
		return newCustomerPage;
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

	public MyAccountPage myAccountPage() {
		if (myAccountPage == null) {
			myAccountPage = new MyAccountPage(this);
		}
		return myAccountPage;
	}

	public LoginPage loginPage() {
		if (loginPage == null) {
			loginPage = new LoginPage(this);
		}
		return loginPage;
	}

	public CategoryPage categoryPage(String department, String category) {
		if (categoryPage == null) {
			categoryPage = new CategoryPage(this, department, category);
		}
		return categoryPage;
	}
}
