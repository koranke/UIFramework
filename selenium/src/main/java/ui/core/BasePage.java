package ui.core;

import configuration.Props;
import lombok.Getter;
import org.aeonbits.owner.ConfigCache;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import configuration.FrameworkConstants;
import utilities.Log;
import utilities.SystemHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public abstract class BasePage<T> {
	protected Props props = ConfigCache.getOrCreate(Props.class);
	protected Log log = Log.getInstance();

	@Getter
	protected final WebDriver webDriver;
	protected String path;
	protected String url;
	protected int maxDiffPixel;

	public BasePage(WebDriver webDriver, String baseUrl, String path) {
		this.webDriver = webDriver;
		this.path = path;
		this.url = String.format("%s%s", baseUrl, path);
		this.maxDiffPixel = props.maxDiffPixels();
	}

	public T goTo() {
		log.info("Navigating to: " + url);
		webDriver.get(url);
		return (T) this;
	}

	public String getPageUrl() {
		return webDriver.getCurrentUrl();
	}

	/*
	This method can give unexpected results when preceded by goTo method.
	 */
	public boolean isOpen() {
		return new WebDriverWait(webDriver, Duration.of(30, ChronoUnit.SECONDS))
				.until(ExpectedConditions.urlToBe(url));
	}

	public void assertIsOpen() {
		Assert.assertTrue(isOpen());
	}

	public void wait(int timeToWait) {
		try {
			SystemHelper.sleep(timeToWait);
		} catch (Exception e) {
			//do nothing
		}
	}

	protected List<Locator> getMask() {
		return null;
	}

	public void assertScreenShot() {
		prepareScreenshotDirectoryStructure();

		Path masterImage = Paths.get(FrameworkConstants.imagesPathMasters + getImageNameFromClass());
		Path testImage = Paths.get(FrameworkConstants.imagesPathTests + getImageNameFromClass());

		if (!Files.exists(masterImage)) {
			takeAndSaveShot(masterImage);
		}
		takeAndSaveShot(testImage);

		BufferedImage masterBufferedImage = null;
		BufferedImage testButteredImage = null;

		try {
			masterBufferedImage = ImageIO.read(new File(masterImage.toUri()));
			testButteredImage = ImageIO.read(new File(testImage.toUri()));
		} catch (Exception e) {
			log.logAssert(false, "Failed to read source images.\n" + e.getMessage());
		}

		ImageDiff imageDiff = new ImageDiffer()
				.makeDiff(masterBufferedImage, testButteredImage)
				.withDiffSizeTrigger(maxDiffPixel);

		if (imageDiff.hasDiff()) {
			try {
				ImageIO.write(imageDiff.getMarkedImage(), "png",
						new File(FrameworkConstants.imagesPathTests + getImageDiffNameFromClass()));
			} catch (Exception e) {
				log.logAssert(false, "Failed to save diff image.\n" + e.getMessage());
			}
		}
		Assert.assertFalse(imageDiff.hasDiff());
	}

	private void prepareScreenshotDirectoryStructure() {
		try {
			if (!Files.exists(Path.of(FrameworkConstants.imagesPathMasters))) {
				Files.createDirectories(Path.of(FrameworkConstants.imagesPathMasters));
			}
			if (!Files.exists(Path.of(FrameworkConstants.imagesPathTests))) {
				Files.createDirectories(Path.of(FrameworkConstants.imagesPathTests));
			}
		} catch (Exception e) {
			log.logAssert(false, "Failed to create directory structure for screenshots.\n"
					+ e.getMessage());
		}
	}

	private void takeAndSaveShot(Path imagePath) {
		AShot shotTaker = new AShot();
		if (getMask() != null) {
			for (Locator locator : getMask()) {
				shotTaker.addIgnoredElement(locator.getBy());
			}
		}
		Screenshot screenshot = shotTaker.takeScreenshot(webDriver);
		try {
			ImageIO.write(screenshot.getImage(), "png", new File(imagePath.toUri()));
		} catch (Exception e) {
			log.logAssert(false, "Failed to save image as file.\n" + e.getMessage());
		}
	}

	private String getImageNameFromClass() {
		return this.getClass().getSimpleName() + ".png";
	}

	private String getImageDiffNameFromClass() {
		return this.getClass().getSimpleName() + "Diff.png";
	}
}
