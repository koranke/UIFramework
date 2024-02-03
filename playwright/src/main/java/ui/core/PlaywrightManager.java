package ui.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import configuration.Props;
import enums.TargetBrowser;
import lombok.Getter;
import lombok.Setter;
import org.aeonbits.owner.ConfigCache;
import utilities.Log;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaywrightManager {
    private static final Props props = ConfigCache.getOrCreate(Props.class);

    @Setter
    @Getter
    private static int slowTime = props.slowTime();

    private static final Log log = Log.getInstance();
    private static final Map<Long, PlaywrightThreadResources> resourcesMap = new HashMap<>();

    public static Page getNewPage() {
        return getNewPage(null, null);
    }

    public static Page getNewPage(TargetBrowser targetBrowser) {
        return getNewPage(null, targetBrowser);
    }

    /**
     * Get new page for thread.
     * @param contextOptions contextOptions
     * @return Page
     */
    public static Page getNewPage(Browser.NewContextOptions contextOptions, TargetBrowser targetBrowser) {
        try {
            long threadId = Thread.currentThread().threadId();
            PlaywrightThreadResources resources;

            if (contextOptions == null) {
                contextOptions = new Browser.NewContextOptions().setViewportSize(null);
            }

            if (resourcesMap.containsKey(threadId)) {
                resources = resourcesMap.get(threadId);

            } else {
                resources = new PlaywrightThreadResources();
                resources.setPlaywright(Playwright.create());
                setBrowser(resources, contextOptions, targetBrowser);
            }

            if (resources.getPage() != null) {
                resources.getPage().close();
            }
            if (resources.getBrowserContext() != null) {
                resources.getBrowserContext().close();
            }

            resources.setBrowserContext(resources.getBrowser().newContext(contextOptions));

            resources.setPage(resources.getBrowserContext().newPage());
            resourcesMap.put(threadId, resources);
            return resources.getPage();

        } catch (Exception e) {
            log.logAssert(false, "Failed to initialize Playwright resources.\n" + e.getMessage());
        }
        return null;
    }

    public static Page getCurrentPage() {
        if (resourcesMap.get(Thread.currentThread().threadId()) == null) {
            return null;
        }
        return resourcesMap.get(Thread.currentThread().threadId()).getPage();
    }

    public static void closeCurrentPage() {
        long threadId = Thread.currentThread().threadId();
        if (resourcesMap.containsKey(threadId)) {
            resourcesMap.get(threadId).getPage().close();
            resourcesMap.get(threadId).getBrowserContext().close();
            resourcesMap.remove(threadId);
        }
    }

    private static void setBrowser(PlaywrightThreadResources resources, Browser.NewContextOptions contextOptions,
                                   TargetBrowser targetBrowser) {

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(props.headless());
        launchOptions.setSlowMo(slowTime);

        if (targetBrowser == null) {
            targetBrowser = props.targetBrowser();
        }

        Browser browser = null;
        switch (targetBrowser) {
            case CHROMIUM:
                launchOptions.setArgs(List.of("--start-maximized"));
                browser = resources.getPlaywright().chromium().launch(launchOptions);
                break;
            case FIREFOX:
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int width = (int) screenSize.getWidth();
                int height = (int) screenSize.getHeight();
                contextOptions.setViewportSize(width, height);
                browser = resources.getPlaywright().firefox().launch(launchOptions);
                break;
            case EDGE:
                launchOptions.setArgs(List.of("--start-maximized"));
                browser = resources.getPlaywright().chromium().launch(launchOptions.setChannel("msedge"));
                break;
            case CHROME:
                launchOptions.setArgs(List.of("--start-maximized"));
                browser = resources.getPlaywright().chromium().launch(launchOptions.setChannel("chrome"));
                break;
            case WEBKIT:
                browser = resources.getPlaywright().webkit().launch(launchOptions);
                break;
            default:
                log.logAssert(false, String.format("Unsupported browser: %s.", targetBrowser.name()));
        }
        resources.setBrowser(browser);
    }

}
