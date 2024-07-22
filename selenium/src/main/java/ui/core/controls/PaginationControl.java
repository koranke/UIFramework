package ui.core.controls;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.core.Locator;

import java.util.ArrayList;
import java.util.List;

@Accessors(fluent = true)
public class PaginationControl extends BaseControl {

	/*
	Depending on the pagination control, the text numbers for each page may be plain content for each link or may be
	wrapped in a span or other element.  "pageLocatorPattern" is used to find the page number in the element text when
	gathering all page numbers.  "pageLocatorPatternWithIndex" is used to find the page number in the element text when
	clicking on a specific page number.
	 */
	@Setter
	protected String pageLocatorPattern = ".//a";
	@Setter
	protected String pageLocatorPatternWithIndex = pageLocatorPattern + "[text()=%d]";

	@Getter
	@Setter
	protected Button buttonFirst;
	@Getter
	@Setter
	protected Button buttonPrior;
	@Getter
	@Setter
	protected Button buttonNext;
	@Getter
	@Setter
	protected Button buttonLast;

	public PaginationControl(Locator locator) {
		super(locator);
	}

	public void clickPage(int pageNumber) {
		try {
			WebElement element = locator.getWithNextLocator(By.xpath(String.format(pageLocatorPatternWithIndex, pageNumber))).getElement();
			element.click();
		}catch(Exception e) {
			throw new RuntimeException("Page number " + pageNumber + " not found");
		}
	}

	public List<Integer> getPages() {
		List<Integer> pages = new ArrayList<>();
		for (WebElement element : locator.getWithNextLocator(By.xpath(pageLocatorPattern)).all()) {
			String text = element.getText();
			int pageNumber = extractPageNumber(text);
			if (pageNumber != -1) {
				pages.add(pageNumber);
			}
		}
		return pages;
	}

	//method that will accept a string and extract/return an integer from it if one is found.  for example, "page <span>1</span>" would return 1.  needs to work even if the string includes newline characters.
	public int extractPageNumber(String text) {
		String[] lines = text.split("\n");
		for (String line : lines) {
			if (line.matches("\\d+")) {
				return Integer.parseInt(line);
			}
		}
		return -1;
	}
}
