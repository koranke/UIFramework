package generator;

import org.openqa.selenium.WebElement;

public class ExtendedElement implements AttributeElement {
	private final WebElement webElement;

	public ExtendedElement(WebElement webElement) {
		this.webElement = webElement;
	}

	@Override
	public String getAttribute(String attribute) {
		return webElement.getDomAttribute(attribute);
	}
}
