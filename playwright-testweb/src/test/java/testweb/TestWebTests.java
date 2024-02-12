package testweb;

import general.TestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.demoone.DemoOnePage;
import ui.sites.testweb.pages.demothree.DemoThreePage;
import ui.sites.testweb.pages.demothree.PanelDetails;
import ui.sites.testweb.pages.demotwo.DemoTwoPage;
import ui.sites.testweb.pages.home.HomePage;

import java.util.ArrayList;
import java.util.List;

public class TestWebTests extends TestBase {

	@Test
	public void testHome() {
		HomePage homePage = new TestWebSite().homePage().goTo();
		homePage.textBoxWhatever().assertIsNotEnabled();
		homePage.textBoxEmail().assertIsEnabled();
		homePage.textBoxEmail().setText("george@outlook.com");
		homePage.textBoxNumber().typeText("abc");
		homePage.textBoxNumber().assertText("");
		homePage.textBoxNumber().typeText("123");
		homePage.textBoxNumber().assertText("123");
		homePage.textBoxPassword().setText("asdfgqwert");
		homePage.textBoxPassword().assertText("asdfgqwert");
		homePage.buttonSubmit().assertText("Submit");
		homePage.buttonSubmit().click();
	}

	@Test
	public void testDemoOne() {
		DemoOnePage demoOnePage = new TestWebSite().demoOnePage().goTo();
		demoOnePage.comboBoxStandard().setText(2);
		demoOnePage.radioButtonOption2().click();
		demoOnePage.listComplex().withRow(2).checkBoxOption().click();

		demoOnePage.comboBoxStandard().assertText("2");
		demoOnePage.radioButtonOption1().assertIsNotSelected();
		demoOnePage.radioButtonOption2().assertIsSelected();
		demoOnePage.listComplex().withRow(2).checkBoxOption().assertIsSelected();
		demoOnePage.listComplex().withRow(3).checkBoxOption().assertIsNotSelected();
	}


	@Test
	public void testDemoTwo() {
		DemoTwoPage demoTwoPage = new TestWebSite().demoTwoPage().goTo();
		demoTwoPage.tableTwo().withRow(2).checkBoxDoIt().click();
		demoTwoPage.tableTwo().comboBoxState().setText("Retired");

		demoTwoPage.tableTwo().withRow(1).checkBoxDoIt().assertIsNotSelected();
		demoTwoPage.tableTwo().comboBoxState().assertText("Pending");
		demoTwoPage.tableTwo().withRow(2).checkBoxDoIt().assertIsSelected();
		demoTwoPage.tableTwo().comboBoxState().assertText("Retired");
	}

	@Test
	public void testDemoThree() {
		DemoThreePage demoThreePage = new TestWebSite().demoThreePage().goTo();
		PanelDetails panelDetails = demoThreePage.panelDetails();

		panelDetails.labelTitle().assertIsNotVisible();
		demoThreePage.buttonViewDetails().click();
		panelDetails.labelTitle().assertIsVisible();

		panelDetails.labelOne().assertText("One");
		panelDetails.listCars().withRow(1).labelCar().assertText("Camry");
		panelDetails.buttonClose().click();

		panelDetails.labelTitle().assertIsNotVisible();
	}

	@Test(enabled = false)
	public void testScreenShotOnFailure() {
		HomePage homePage = new TestWebSite().homePage().goTo();
		homePage.textBoxNumber().typeText("abc");
		homePage.textBoxNumber().assertText("abc");
		homePage.buttonSubmit().click();
	}

	@Test
	public void testTracingOnDemand() {
		HomePage homePage = new TestWebSite().enableTracing().homePage().goTo();
		homePage.textBoxNumber().typeText("abc");
		homePage.textBoxNumber().assertText("");
		homePage.buttonSubmit().click();
	}


	@DataProvider(name = "ParallelScenarios", parallel = true)
	public Object[][] getParallelScenarios() {
		List<Object[]> data = new ArrayList<>();
		String scenario;

		//------------------------------------------------
		scenario = "Scenario One";
		//------------------------------------------------
		data.add(new Object[]{ scenario, "George", "" });

		//------------------------------------------------
		scenario = "Scenario Two";
		//------------------------------------------------
		data.add(new Object[]{ scenario, "1234", "1234" });

		//------------------------------------------------
		scenario = "Scenario Three";
		//------------------------------------------------
		data.add(new Object[]{ scenario, "-25", "-25" });

		return data.toArray(new Object[][]{});
	}

	@Test(dataProvider = "ParallelScenarios")
	public void testParallelScenarios(String scenario, String number, String expectedValue) {
		HomePage homePage = new TestWebSite().homePage().goTo();
		homePage.textBoxNumber().typeText(number);
		homePage.textBoxNumber().assertText(expectedValue);
	}
}


