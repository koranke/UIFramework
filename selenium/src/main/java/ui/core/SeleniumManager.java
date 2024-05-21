package ui.core;

import configuration.Props;
import enums.TargetBrowser;
import lombok.Getter;
import lombok.Setter;
import org.aeonbits.owner.ConfigCache;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import utilities.Log;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SeleniumManager {
	private static final Props props = ConfigCache.getOrCreate(Props.class);

	@Setter
	@Getter
	private static int slowTime = props.slowTime();
	private static final Log log = Log.getInstance();
	private static final Map<Long, WebDriver> driverMap = new HashMap<>();

	public static WebDriver getNewDriver() {
		return getNewDriver(null);
	}

	public static WebDriver getNewDriver(TargetBrowser targetBrowser) {
		long threadId = Thread.currentThread().threadId();

		if (driverMap.containsKey(threadId)) {
			driverMap.get(threadId).quit();
		}
		log.debug("Getting new driver for thread: " + Thread.currentThread().threadId());
		driverMap.put(threadId, getConfiguredDriver(targetBrowser));
		return driverMap.get(threadId);
	}

	public static WebDriver getCurrentDriver() {
		long threadId = Thread.currentThread().threadId();
		return driverMap.getOrDefault(threadId, null);
	}

	public static void closeCurrentDriver() {
		long threadId = Thread.currentThread().threadId();

		if (driverMap.containsKey(threadId)) {
			log.debug("Closing current driver for thread: " + Thread.currentThread().threadId());
			driverMap.get(threadId).quit();
			driverMap.remove(threadId);
		} else {
			log.debug("No driver to close for thread: " + Thread.currentThread().threadId());
		}
	}

	private static WebDriver getConfiguredDriver(TargetBrowser targetBrowser) {
		if (targetBrowser == null) {
			targetBrowser = props.targetBrowser();
		}

		WebDriver webDriver;
		switch (targetBrowser) {
			case CHROMIUM, CHROME -> {
				ChromeOptions options = new ChromeOptions();
				if (props.useBiDi()) {
					options.setCapability("webSocketUrl", true);
				}
				if (props.headless()) {
					options.addArguments("--headless=new");
				}
				webDriver = new ChromeDriver(options);
			}
			case EDGE -> {
				EdgeOptions options = new EdgeOptions();
				if (props.useBiDi()) {
					options.setCapability("webSocketUrl", true);
				}
				if (props.headless()) {
					options.addArguments("--headless=new");
				}
				webDriver = new EdgeDriver(options);
			}
			case WEBKIT -> {
				webDriver = new SafariDriver();
			}
			case FIREFOX -> {
				FirefoxOptions options = new FirefoxOptions();
				if (props.useBiDi()) {
					options.setCapability("webSocketUrl", true);
				}
				if (props.headless()) {
					options.addArguments("-headless");
				}
				webDriver = new FirefoxDriver();
			}
			default -> {
				log.logAssert(false, "Unsupported Browser: " + targetBrowser.name());
				return null;
			}
		}
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(props.implicitWait()));
		return webDriver;
	}
}
