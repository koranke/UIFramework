package generator;

import com.microsoft.playwright.ElementHandle;

public class ExtendedElement implements AttributeElement {
	private final ElementHandle elementHandle;

	public ExtendedElement(ElementHandle elementHandle) {
		this.elementHandle = elementHandle;
	}


	@Override
	public String getAttribute(String attribute) {
		return elementHandle.getAttribute(attribute);
	}
}
