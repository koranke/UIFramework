package ui.core.controls;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.core.Locator;

@Accessors(fluent = true)
public class PaginationControl extends BaseControl {
	@Setter
	protected String pageLocatorPattern = ".//a";
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
			WebElement element = locator.getWithNextLocator(By.xpath(String.format(pageLocatorPattern, pageNumber))).getElement();
			element.click();
		}catch(Exception e) {
			throw new RuntimeException("Page number " + pageNumber + " not found");
		}
	}

}
