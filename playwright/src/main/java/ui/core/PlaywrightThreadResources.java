package ui.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.Data;

@Data
public class PlaywrightThreadResources {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
}
