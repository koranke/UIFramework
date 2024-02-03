package ui.sites.testweb.pages.demothree;

import org.openqa.selenium.WebDriver;

public class PanelDetails extends PanelDetailsBase {

    public PanelDetails(WebDriver webDriver) {
        super(webDriver);

        this.buttonClose.setClickDelay(100);
    }
}
