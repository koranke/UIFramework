package ui.sites.testweb.pages.demoone;


import ui.core.Locator;

public class ListComplex extends ListComplexBase {

    public ListComplex(Locator locator) {
        super(locator);
        //Add any overrides here.  Method "initialize" must be called last.
        this.hasHeader = true;
        this.initialize();
    }
}
