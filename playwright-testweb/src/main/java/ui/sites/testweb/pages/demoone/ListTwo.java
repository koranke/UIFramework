package ui.sites.testweb.pages.demoone;

import com.microsoft.playwright.Locator;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class ListTwo extends ListTwoBase {

    public ListTwo(Locator locator) {
        super(locator);
        //Add any overrides here.  Method "initialize" must be called last.
        this.initialize();
    }
}
