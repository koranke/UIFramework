package ui.sites.testweb.pages.demothree;

import com.microsoft.playwright.Page;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class PanelDetails extends PanelDetailsBase {

    public PanelDetails(Page page) {
        super(page);
        this.buttonClose.setClickDelay(100);
    }
}
