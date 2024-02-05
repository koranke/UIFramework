package ui.sites.testweb.pages.demoone;

import com.microsoft.playwright.Locator;
import ui.core.controls.FlagControl;
import ui.core.controls.Label;
import ui.core.controls.ListControl;
import ui.core.controls.RepeatingControl;
import ui.core.enums.LocatorMethod;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class ListComplexBase extends ListControl<ListComplexBase> {
	private RepeatingControl<FlagControl> checkBoxOption;
	private RepeatingControl<Label> labelOption;


	public ListComplexBase(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//li";
	}

	protected void initialize() {
		
        this.checkBoxOption = new RepeatingControl<>(
                locator,
                "CheckBox-Option",
                LocatorMethod.DATA_TEST_ID,
                FlagControl::new,
                rowLocatorPattern,
                hasHeader
        );
		
        this.labelOption = new RepeatingControl<>(
                locator,
                "Label-Option",
                LocatorMethod.DATA_TEST_ID,
                Label::new,
                rowLocatorPattern,
                hasHeader
        );
	}

	public ListComplexBase usingLabelOption() {
		this.searchLabel = labelOption;
		return this;
	}

	public FlagControl checkBoxOption() {
		return checkBoxOption.get(currentRow);
	}

	public Label labelOption() {
		return labelOption.get(currentRow);
	}
}