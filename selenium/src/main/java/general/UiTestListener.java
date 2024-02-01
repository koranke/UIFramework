package general;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ui.FrameworkConstants;
import ui.core.SeleniumManager;
import utilities.Log;

import java.io.File;

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
        WebDriver webDriver = SeleniumManager.getCurrentDriver();

        /*
        We only want to take screenshots if a page is open.  For example, a test could fail during the
        setup phase, before a page has opened, in which case there is no screenshot to take.
         */
        if (webDriver != null) {
            File file = ((TakesScreenshot) SeleniumManager.getCurrentDriver()).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(file, new File(FrameworkConstants.imagesPathErrors + fileName));
            } catch (Exception e) {
                log.logAssert(false, e.getMessage());
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        /*
        Make sure page is closed from any prior test.
         */
        if (SeleniumManager.getCurrentDriver() != null) {
            SeleniumManager.closeCurrentDriver();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long time = result.getEndMillis() - result.getStartMillis();
        log.info(String.format("Execution time for test [%s]: %d ms", result.getName(), time));
        SeleniumManager.closeCurrentDriver();
    }
}
