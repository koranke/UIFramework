package ui.sites.testweb.pages.demotwo;

import com.microsoft.playwright.Locator;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class TableTwo extends TableTwoBase {

    public TableTwo(Locator locator) {
        super(locator);
        //Add any overrides here.  Method "initialize" must be called last.
        this.initialize();
    }
}
