package ui.core;

import org.openqa.selenium.By;

public class ExtendedBy {

	public static By testId(String testId) {
		return By.xpath(String.format("//*[@data-testid='%s']", testId));
	}

	public static By relativeTestId(String testId) {
		return By.xpath(String.format(".//descendant-or-self::*[@data-testid='%s']", testId));
	}

	public static By text(String text) {
		return By.xpath(String.format("//*[text()='%s']", text));
	}
}
