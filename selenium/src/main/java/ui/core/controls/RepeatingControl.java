package ui.core.controls;


import org.openqa.selenium.By;
import ui.core.ExtendedBy;
import ui.core.Locator;
import ui.core.TriFunction;
import ui.core.enums.LocatorMethod;

import java.util.function.Function;

public class RepeatingControl<T> extends BaseControl {
	private final String controlId;
	private final String controlId2;
	private final String controlId3;
	private final LocatorMethod locatorMethod;
	private final Function<Locator, T> getControl;
	private final TriFunction<Locator, Locator, Locator, T> getCustomControl;
	protected String rowLocatorPattern;
	protected boolean hasHeader;

	public RepeatingControl(
			Locator locator,
			String controlId,
			LocatorMethod locatorMethod,
			Function<Locator, T> getControlMethod,
			String rowLocatorPattern,
			boolean hasHeader) {

		super(locator);
		this.controlId = controlId;
		this.controlId2 = null;
		this.controlId3 = null;
		this.locatorMethod = locatorMethod;
		this.getControl = getControlMethod;
		this.getCustomControl = null;
		this.rowLocatorPattern = rowLocatorPattern;
		this.hasHeader = hasHeader;
	}

	public RepeatingControl(
			Locator locator,
			String controlId1,
			String controlId2,
			String controlId3,
			LocatorMethod locatorMethod,
			TriFunction<Locator, Locator, Locator, T> getControlMethod,
			String rowLocatorPattern,
			boolean hasHeader) {

		super(locator);
		this.controlId = controlId1;
		this.controlId2 = controlId2;
		this.controlId3 = controlId3;
		this.locatorMethod = locatorMethod;
		this.getControl = null;
		this.getCustomControl = getControlMethod;
		this.rowLocatorPattern = rowLocatorPattern;
		this.hasHeader = hasHeader;
	}

	public T get(int row) {
		if (getControl == null) {
			return getCustom(row);
		}

		if (controlId == null) {
			return getControl.apply(getRowLocator(row));
		} else {
			switch (locatorMethod) {
				case DATA_TEST_ID:
					return getControl.apply(getRowLocator(row).withNext(ExtendedBy.relativeTestId(controlId)));
				case TEXT:
					return getControl.apply(getRowLocator(row).withNext(By.xpath(String.format(".//*[text()='%s']", controlId))));
				case XPATH:
					return getControl.apply(getRowLocator(row).withNext(By.xpath(controlId)));
			}
		}
		return null;
	}

	public T getCustom(int row) {
//		log.logAssert(getCustomControl != null, "Not configured for custom controls.");
//		switch (locatorMethod) {
//			case DATA_TEST_ID:
//				return getCustomControl.apply(getRowLocator(row).getByTestId(controlId),
//						getRowLocator(row).getByTestId(controlId2), getRowLocator(row).getByTestId(controlId3));
//			case TEXT:
//				return getCustomControl.apply(getRowLocator(row).getByText(controlId),
//						getRowLocator(row).getByText(controlId2), getRowLocator(row).getByText(controlId3));
//			case XPATH:
//				return getCustomControl.apply(getRowLocator(row).locator(controlId),
//						getRowLocator(row).locator(controlId2), getRowLocator(row).locator(controlId3));
//		}
		return null;
	}


	public T get(String text) {
		int index = getIndex(text);
		return get(index);
	}

	public Integer getIndex(String targetText) {
		int startingIndex = hasHeader ? 2 : 1;
		int rowCount = getRowCount();
		for (int i = startingIndex; i < rowCount; i++) {
			TextControl textControl = (TextControl) get(i);
			if (textControl.getText().contains(targetText)) {
				return i;
			}
		}
		return null;
	}

	private int getAdjustedRow(int row) {
		return hasHeader ? row + 1 : row;
	}

	private Locator getRowLocator(int row) {
		row = getAdjustedRow(row);
		return locator.getWithNextLocator(By.xpath(String.format("%s[%d]", rowLocatorPattern, row)));
	}

	private int getRowCount() {
		return locator.getWithNextLocator(By.xpath(rowLocatorPattern)).all().size();
	}
}
