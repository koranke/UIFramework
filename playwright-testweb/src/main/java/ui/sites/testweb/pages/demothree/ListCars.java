package ui.sites.testweb.pages.demothree;

import com.microsoft.playwright.Locator;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class ListCars extends ListCarsBase {

    public ListCars(Locator locator) {
        super(locator);
        //Add any overrides here.  Method "initialize" must be called last.
        this.initialize();

        //TODO: we need a way to set control id to null.  Old method no longer works.
//        this.labelCar().setControlId(null);
    }
}
