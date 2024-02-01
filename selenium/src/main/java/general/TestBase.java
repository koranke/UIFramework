package general;

import configuration.Env;
import configuration.Props;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import ui.core.SeleniumManager;
import utilities.Log;

import java.lang.reflect.Method;

@Listeners({ UiTestListener.class })
public class TestBase {

	static {
		if (System.getProperty("env.id") == null) {
			Env env = ConfigFactory.create(Env.class);
			System.setProperty("env.id", env.envId().getValue());
		}
	}

	protected Props props = ConfigCache.getOrCreate(Props.class);
	protected Log log = Log.getInstance();


	@AfterSuite(alwaysRun = true)
	public void suiteCleanup() {
		SeleniumManager.closeCurrentDriver();
	}

	@BeforeMethod(alwaysRun = true)
	public void methodSetup(Method method) {
		log.startTest(getTestName(method));
	}

	@AfterMethod(alwaysRun = true)
	public void methodCleanup(Method method) {
		log.endTest(getTestName(method));
	}

	private String getTestName(Method method) {
		return String.format("%s.%s", method.getDeclaringClass().getSimpleName(), method.getName());
	}

}
