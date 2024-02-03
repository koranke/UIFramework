package ui.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import enums.TargetBrowser;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Paths;
import java.time.Instant;

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
        return (T) this;
    }

    /**
     * Save Tracing.  View at <a href="https://trace.playwright.dev/">...</a>
     * @return Site
     */
    public T saveTracing() {
        this.page.context().tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get(String.format("traces/trace_%d.zip", Instant.now().getEpochSecond())))
        );
        return (T) this;
    }

}
