package ui.sites.testweb.pages.demoone;

import lombok.Getter;
import lombok.experimental.Accessors;
import ui.core.controls.ComboBox;
import ui.core.controls.FlagControl;
import ui.core.controls.SelectComboBox;
import ui.sites.testweb.TestWebSite;
import ui.sites.testweb.pages.BaseTestWebPage;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
@Getter
@Accessors(fluent = true)
public abstract class DemoOneBasePage<T> extends BaseTestWebPage<T> {
	private FlagControl checkBoxDefault;
	private FlagControl checkBoxOther;
	private FlagControl radioButtonOption1;
	private FlagControl radioButtonOption2;
	private ListOne listOne;
	private ListTwo listTwo;
	private ListComplex listComplex;
	private ComboBox comboBoxStandard;


	public DemoOneBasePage(TestWebSite portal) {
		super(portal, "demopage1.html");
		initialize();
	}

	protected void initialize() {
		this.checkBoxDefault = new FlagControl(page.getByTestId("CheckBox-Default"));
		this.checkBoxOther = new FlagControl(page.getByTestId("CheckBox-Other"));
		this.radioButtonOption1 = new FlagControl(page.getByTestId("RadioButton-Option1"));
		this.radioButtonOption2 = new FlagControl(page.getByTestId("RadioButton-Option2"));
		this.listOne = new ListOne(page.getByTestId("List-One"));
		this.listTwo = new ListTwo(page.getByTestId("List-Two"));
		this.listComplex = new ListComplex(page.getByTestId("List-Complex"));
		this.comboBoxStandard = new SelectComboBox(page.getByTestId("ComboBox-Standard-button"));
	}
}