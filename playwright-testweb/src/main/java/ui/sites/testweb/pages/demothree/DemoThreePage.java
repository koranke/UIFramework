package ui.sites.testweb.pages.demothree;


import lombok.Getter;
import lombok.experimental.Accessors;
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
        paginationControl = new PaginationControl(portal.page.getByTestId("Pagination-Main"));
        paginationControl.buttonPrior(new Button(paginationControl.getLocator().getByText("Previous")));
        paginationControl.buttonNext(new Button(paginationControl.getLocator().getByText("Next")));
    }

}