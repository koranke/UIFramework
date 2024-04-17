package saucedemo.steps;

import configuration.Env;
import configuration.Props;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;
import saucedemo.SauceDemoSite;
import ui.core.SeleniumManager;
import utilities.Log;

public class BaseSteps {
	protected SauceDemoSite site;

	static {
		if (System.getProperty("env.id") == null) {
			Env env = ConfigFactory.create(Env.class);
			System.setProperty("env.id", env.envId().getValue());
		}
	}

	protected Props props = ConfigCache.getOrCreate(Props.class);
	protected Log log = Log.getInstance();

	@BeforeAll
	public static void setUp() {
		System.out.println("Setting up the test");
	}

	@After
	public static void tearDown() {
		SeleniumManager.closeCurrentDriver();
	}
}
