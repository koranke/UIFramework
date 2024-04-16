package swaglabs;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin = { "html:selenium-swaglabs/target/results.html", "message:selenium-swaglabs/target/results.ndjson" },
	features = "selenium-swaglabs/src/test/resources",
	glue = "saucedemo.steps")
public class RunCucumberTest extends AbstractTestNGCucumberTests {

	@DataProvider(parallel = true)
	@Override
	public Object[][] scenarios() {
		return super.scenarios();
	}

}