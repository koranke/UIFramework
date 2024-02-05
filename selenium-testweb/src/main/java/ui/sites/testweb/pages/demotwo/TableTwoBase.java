package ui.sites.testweb.pages.demotwo;

import ui.core.Locator;
import ui.core.controls.ComboBox;
import ui.core.controls.FlagControl;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.controls.SelectComboBox;
import ui.core.enums.LocatorMethod;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class TableTwoBase extends ListControl<TableTwoBase> {
	private RepeatingControl<FlagControl> checkBoxDoIt;
	private RepeatingControl<ComboBox> comboBoxState;


	public TableTwoBase(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = ".//tbody/tr";
	}

	protected void initialize() {
		this.checkBoxDoIt = new RepeatingControl<>(
			locator,
			"CheckBox-DoIt",
			LocatorMethod.DATA_TEST_ID,
			FlagControl::new,
			rowLocatorPattern,
			hasHeader
		);
		this.comboBoxState = new RepeatingControl<>(
			locator,
			"ComboBox-State-button",
			LocatorMethod.DATA_TEST_ID,
			SelectComboBox::new,
			rowLocatorPattern,
			hasHeader
		);
	}

	public FlagControl checkBoxDoIt() {
		return checkBoxDoIt.get(currentRow);
	}

	public ComboBox comboBoxState() {
		return comboBoxState.get(currentRow);
	}
}