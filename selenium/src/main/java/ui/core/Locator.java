package ui.core;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import utilities.SystemHelper;

import java.time.Duration;
import java.util.List;

public class Locator {
	private WebDriver webDriver;
	private WebElement parentElement;

	@Getter
	private By by;
	private Integer index;
	private Locator next;

	/*
	To be consistent with xpath, use 1-based index instead of 0-based index.
	 */
	public Locator withIndex(int index) {
		this.index = index - 1;
		return this;
	}

	public Locator withNext(By by) {
		this.next = new Locator(webDriver, by);
		return this;
	}

	public Locator withNext(By by, int index) {
		this.next = new Locator(webDriver, by);
		this.next.withIndex(index);
		return this;
	}

	public Locator withParentElement(WebElement parentElement) {
		this.parentElement = parentElement;
		return this;
	}

	public Locator(WebDriver driver, By by) {
		this.webDriver = driver;
		this.by = by;
	}

	public Locator getWithNextLocator(By by) {
		WebElement parentElement;
		if (index == null) {
			parentElement = getWebDriver().findElement(this.by);
		} else {
			parentElement = getWebDriver().findElements(this.by).get(index);
		}
		return new Locator(webDriver, by).withParentElement(parentElement);
	}

	public WebElement getElement() {
		if (parentElement != null) return getElement(parentElement);

		WebElement webElement;
		if (index == null) {
			webElement = getWebDriver().findElement(by);
		} else {
			webElement = getWebDriver().findElements(by).get(index);
		}

		if (next != null) {
			return next.getElement(webElement);
		} else {
			return webElement;
		}
	}

	public WebElement getElement(WebElement parentWebElement) {
		WebElement webElement;
		if (index == null) {
			webElement = parentWebElement.findElement(by);
		} else {
			webElement = parentWebElement.findElements(by).get(index);
		}
		if (next != null) {
			return next.getElement(webElement);
		} else {
			return webElement;
		}
	}

	public List<WebElement> all() {
		return getWebDriver().findElements(by);
	}

	public WebElement nth(int index) {
		withIndex(index);
		return getElement();
	}

	public void click() {
		getElement().click();
	}

	public boolean isEnabled() {
		return getElement().isEnabled();
	}

	public boolean isVisible() {
		return isVisible(0);
	}

	public boolean isVisible(int maxWaitTimeInSeconds) {
		FluentWait<WebElement> wait = new FluentWait<>(getElement())
				.withTimeout(Duration.ofSeconds(maxWaitTimeInSeconds))
				.pollingEvery(Duration.ofMillis(100));

		try {
			return wait.until(WebElement::isDisplayed);
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isNotVisible() {
		return isNotVisible(0);
	}

	public boolean isNotVisible(int maxWaitTimeInSeconds) {
		FluentWait<WebElement> wait = new FluentWait<>(getElement())
				.withTimeout(Duration.ofSeconds(maxWaitTimeInSeconds))
				.pollingEvery(Duration.ofMillis(100));

		try {
			return wait.until(e -> !e.isDisplayed());
		} catch (TimeoutException e) {
			return true;
		}
	}

	public boolean isChecked() {
		return getElement().isSelected();
	}

	public String getText() {
		return getElement().getText().trim();
	}

	public void setText(String text) {
		getElement().sendKeys(text);
	}

	public String getAttribute(String attributeName) {
		return getElement().getAttribute(attributeName);
	}

	public String getDomAttribute(String attributeName) {
		return getElement().getDomAttribute(attributeName);
	}

	/*
	Use this to implement slow time behavior.
	 */
	private WebDriver getWebDriver() {
		SystemHelper.sleep(SeleniumManager.getSlowTime());
		return this.webDriver;
	}

	public Locator clone() {
		return new Locator(webDriver, by);
	}
}
