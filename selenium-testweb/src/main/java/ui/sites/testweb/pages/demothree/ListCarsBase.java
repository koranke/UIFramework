package ui.sites.testweb.pages.demothree;

import ui.core.Locator;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class ListCarsBase extends ListControl<ListCarsBase> {
	private RepeatingControl<Label> labelCar;


	public ListCarsBase(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = ".//li";
	}

	protected void initialize() {
		this.labelCar = new RepeatingControl<>(
			locator,
			"Label-Car",
			LocatorMethod.DATA_TEST_ID,
			Label::new,
			rowLocatorPattern,
			hasHeader
		);
	}

	public ListCarsBase usingLabelCar() {
		this.searchLabel = labelCar;
		return this;
	}

	public Label labelCar() {
		return labelCar.get(currentRow);
	}
}