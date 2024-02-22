package ui.core.controls;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.core.ExtendedBy;
import ui.core.Locator;

import java.util.ArrayList;
import java.util.List;

@Accessors(fluent = true)
public class PaginationControl extends BaseControl {
	@Setter
	private String pageLocatorPattern = ".//a";
	@Getter
	@Setter
	private Button buttonFirst;
	@Getter
	@Setter
	private Button buttonPrior;
	@Getter
	@Setter
	private Button buttonNext;
	@Getter
	@Setter
	private Button buttonLast;

	public PaginationControl(Locator locator) {
		super(locator);
	}

	public void clickPage(int pageNumber) {
		if (getPages().contains(pageNumber)) {
			locator.withNext(ExtendedBy.text(Integer.toString(pageNumber))).click();
		} else {
			throw new RuntimeException("Page number " + pageNumber + " not found");
		}
	}

	public List<Integer> getPages() {
		List<Integer> pages = new ArrayList<>();
		for (WebElement element : locator.getWithNextLocator(By.xpath(pageLocatorPattern)).all()) {
			String text = element.getText();
			if (text.matches("\\d+")) {
				pages.add(Integer.parseInt(text));
			}
		}
		return pages;
	}
}
