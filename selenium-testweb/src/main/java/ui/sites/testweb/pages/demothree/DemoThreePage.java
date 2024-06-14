package ui.sites.testweb.pages.demothree;

import lombok.Getter;
import lombok.experimental.Accessors;
import ui.core.ExtendedBy;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.PaginationControl;
import ui.sites.testweb.TestWebSite;

@Accessors(fluent = true)
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class DemoThreePage extends DemoThreeBasePage<DemoThreePage> {
    @Getter
    private final PaginationControl paginationControl;

    public DemoThreePage(TestWebSite portal) {
        super(portal);
        paginationControl = new PaginationControl(new Locator(portal.webDriver, ExtendedBy.testId("Pagination-Main")));
        Locator locator = paginationControl.getLocator().clone().withNext(ExtendedBy.text("Previous"));
        paginationControl.buttonPrior(new Button(locator));
        locator = paginationControl.getLocator().clone().withNext(ExtendedBy.text("Next"));
        paginationControl.buttonNext(new Button(locator));
    }

}