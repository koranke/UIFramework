package ui.core;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotAnimations;
import com.microsoft.playwright.options.ScreenshotCaret;
import configuration.FrameworkConstants;
import configuration.Props;
import lombok.Getter;
import org.aeonbits.owner.ConfigCache;
import org.testng.Assert;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import utilities.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class BasePage<T> {
	protected Props props = ConfigCache.getOrCreate(Props.class);
	protected Log log = Log.getInstance();

	@Getter
	protected final Page page;
	protected String path;
	protected String url;
	protected int maxDiffPixel;

	public BasePage(Page page, String baseUrl, String path) {
		this.page = page;
		this.path = path;
		this.url = String.format("%s%s", baseUrl, path);
		this.maxDiffPixel = props.maxDiffPixels();
	}

	public T goTo() {
		log.info("Navigating to: " + url);
		page.navigate(url);
		return (T) this;
	}

	public String getPageUrl() {
		return page.url();
	}

	/*
	This method can give unexpected results when preceded by goTo method.
	 */
	public boolean isOpen() {
		int timeout = 100;
		while (!page.url().equals(url)) {
			page.waitForTimeout(100);
			timeout *= 2;
			if (timeout > 2400) {
				break;
			}
		}
		return page.url().equals(url);
	}

	public void assertIsOpen() {
		Assert.assertTrue(isOpen());
	}

	public void wait(int timeToWait) {
		try {
			page.waitForTimeout(timeToWait);
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
		PlaywrightManager.getCurrentPage().screenshot(
				new Page.ScreenshotOptions()
						.setPath(imagePath)
						.setAnimations(ScreenshotAnimations.DISABLED)
						.setCaret(ScreenshotCaret.HIDE)
						.setMask(getMask())
		);
	}

	private String getImageNameFromClass() {
		return this.getClass().getSimpleName() + ".png";
	}

	private String getImageDiffNameFromClass() {
		return this.getClass().getSimpleName() + "Diff.png";
	}

}
