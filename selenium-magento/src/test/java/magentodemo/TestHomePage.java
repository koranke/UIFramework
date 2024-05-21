package magentodemo;

import general.TestBase;
import org.testng.annotations.Test;

public class TestHomePage extends TestBase {

	@Test
	public void testHomePage() {
		MagentoDemoSite site = new MagentoDemoSite();
		site.homePage().open();
		site.homePage().assertIsOpen();

		site.homePage().navigationPanel().whatsNew().click();
		site.whatIsNewPage().assertIsOpen();

		site.whatIsNewPage().navigationPanel().logo().click();
		site.homePage().assertIsOpen();
	}

}
