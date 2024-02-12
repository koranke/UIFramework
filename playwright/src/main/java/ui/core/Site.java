package ui.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import enums.TargetBrowser;
import lombok.Getter;
import lombok.Setter;

public abstract class Site<T> {
    public String baseUrl;
    public Page page;

    @Setter
    @Getter
    protected boolean isSignedIn;

    @Setter
    @Getter
    protected String sessionStorage;

    public Site() {
        this.page = PlaywrightManager.getNewPage();
    }

    public Site(Browser.NewContextOptions contextOptions) {
        this.page = PlaywrightManager.getNewPage(contextOptions, null);
    }

    public Site(TargetBrowser targetBrowser) {
        this.page = PlaywrightManager.getNewPage(null, targetBrowser);
    }

    public Page getNewPage(TargetBrowser targetBrowser) {
        this.page = PlaywrightManager.getNewPage(targetBrowser);
        return page;
    }

    public T enableTracing() {
        this.page.context().tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
        PlaywrightManager.flagTracingOn();
        return (T) this;
    }
}
