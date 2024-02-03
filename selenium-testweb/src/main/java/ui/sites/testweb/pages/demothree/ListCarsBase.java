package ui.sites.testweb.pages.demothree;

import ui.core.Locator;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class ListCarsBase extends ListControl {
	public RepeatingControl<Label> labelCar;




    /**
     * List Constructor.
     * @param locator locator
     */
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
}