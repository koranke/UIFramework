package ui.core.controls;

import com.microsoft.playwright.Locator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(fluent = true)
public class PaginationControl extends BaseControl {
	@Setter
	private String pageLocatorPattern = "//a";
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
			locator.getByText(Integer.toString(pageNumber)).click();
		} else {
			throw new RuntimeException("Page number " + pageNumber + " not found");
		}
	}

	public List<Integer> getPages() {
		List<Integer> pages = new ArrayList<>();
		for (Locator element : locator.locator(pageLocatorPattern).all()) {
			String text = element.innerText();
			if (text.matches("\\d+")) {
				pages.add(Integer.parseInt(text));
			}
		}
		return pages;
	}
}
