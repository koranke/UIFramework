package general;

import com.microsoft.playwright.Page;
import configuration.FrameworkConstants;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ui.core.PlaywrightManager;
import utilities.Log;

import java.nio.file.Path;

public class UiTestListener extends TestListenerAdapter {
    private final Log log = Log.getInstance();

    @Override
    public void onTestFailure(ITestResult result) {
        String fileName;
        if (result.getParameters() != null && result.getParameters().length > 0 && result.getParameters()[0].getClass().isAssignableFrom(String.class)) {
            fileName = String.format("%s.%s.%s.%d.png", result.getInstanceName(), result.getName(),
                    result.getParameters()[0], System.currentTimeMillis());
        } else {
            fileName = String.format("%s.%s.%d.png", result.getInstanceName(), result.getName(), System.currentTimeMillis());
        }
        Page page = PlaywrightManager.getCurrentPage();

        /*
        We only want to take screenshots if a page is open.  For example, a test could fail during the
        setup phase, before a page has opened, in which case there is no screenshot to take.
         */
        if (page != null && !page.isClosed()) {
            PlaywrightManager.getCurrentPage().screenshot(
                    new Page.ScreenshotOptions()
                            .setPath(Path.of(FrameworkConstants.imagesPathErrors + fileName))
            );
        }

        if (PlaywrightManager.tracingIsOn()) {
            PlaywrightManager.saveTracing(getCombinedTestName(result));
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        /*
        Make sure page is closed from any prior test.
         */
        if (PlaywrightManager.getCurrentPage() != null) {
            PlaywrightManager.closeCurrentPage();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long time = result.getEndMillis() - result.getStartMillis();
        log.info(String.format("Execution time for test [%s]: %d ms", result.getName(), time));

        if (PlaywrightManager.tracingIsOn()) {
            PlaywrightManager.saveTracing(getCombinedTestName(result));
        }

        PlaywrightManager.closeCurrentPage();
    }

    private String getCombinedTestName(ITestResult result) {
        return String.format("%s.%s", result.getInstanceName(), result.getName());
    }
}
