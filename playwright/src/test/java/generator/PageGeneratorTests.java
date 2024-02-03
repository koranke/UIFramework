package generator;

import org.testng.annotations.Test;
import ui.core.enums.TargetPortal;

import java.util.HashMap;
import java.util.Map;

/*
INSTRUCTIONS:

1. In order for these "tests" to work, need to add the following to IntelliJ, Help->Edit custom VM options and restart.
-Deditable.java.test.console=true
*/
@Test
public class PageGeneratorTests {
    private static final String projectPath = System.getProperty("user.dir") + "/";

    //--------------TEST WEB SITE-------------------------------------------
    private static final String baseClassPackagePath = projectPath
            + "playwright/src/main/java/ui/sites/testweb/pages/";
    private static final String homeUrl = "home.html";
    private static final String demoPage1Url = "demopage1.html";
    private static final String demoPage2Url = "demopage2.html";
    private static final String demoPage3Url = "demopage3.html";
    private static final Map<String, String> pageUrlMap = new HashMap<>();
    //---------------------------------------------------------

    public PageGeneratorTests() {
        pageUrlMap.put(homeUrl, "Home");
        pageUrlMap.put(demoPage1Url, "DemoOne");
        pageUrlMap.put(demoPage2Url, "DemoTwo");
        pageUrlMap.put(demoPage3Url, "DemoThree");
    }

    public void makePages() {
        for (String url : pageUrlMap.keySet()) {
            PageControlModelGenerator.makePage(baseClassPackagePath, url, pageUrlMap.get(url), TargetPortal.TEST_WEB);
        }
    }

    public void makeSinglePageForSite() {
        PageControlModelGenerator.makePage(baseClassPackagePath, demoPage3Url,
                pageUrlMap.get(demoPage3Url), TargetPortal.TEST_WEB);
    }

}
