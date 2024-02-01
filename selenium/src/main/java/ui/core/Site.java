package ui.core;

import enums.TargetBrowser;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

public abstract class Site<T> {
    public String baseUrl;
    public WebDriver webDriver;

    @Setter
    @Getter
    protected boolean isSignedIn;

    @Setter
    @Getter
    protected String sessionStorage;

    public Site() {
        this.webDriver = SeleniumManager.getNewDriver();
    }

//    public Site(Browser.NewContextOptions contextOptions) {
//        this.page = PlaywrightManager.getNewPage(contextOptions, null);
//    }

    public Site(TargetBrowser targetBrowser) {
        this.webDriver = SeleniumManager.getNewDriver( targetBrowser);
    }

    public WebDriver getNewDriver(TargetBrowser targetBrowser) {
        this.webDriver = SeleniumManager.getNewDriver(targetBrowser);
        return webDriver;
    }

}
