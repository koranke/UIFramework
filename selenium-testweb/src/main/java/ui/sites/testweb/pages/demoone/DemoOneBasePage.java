package ui.sites.testweb.pages.demoone;

import lombok.Getter;
import lombok.experimental.Accessors;
import ui.core.ExtendedBy;
import ui.core.Locator;
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
		this.checkBoxDefault = new FlagControl(site.webDriver, ExtendedBy.testId("CheckBox-Default"));
		this.checkBoxOther = new FlagControl(site.webDriver, ExtendedBy.testId("CheckBox-Other"));
		this.radioButtonOption1 = new FlagControl(site.webDriver, ExtendedBy.testId("RadioButton-Option1"));
		this.radioButtonOption2 = new FlagControl(site.webDriver, ExtendedBy.testId("RadioButton-Option2"));
		this.listOne = new ListOne(new Locator(site.webDriver, ExtendedBy.testId("List-One")));
		this.listTwo = new ListTwo(new Locator(site.webDriver, ExtendedBy.testId("List-Two")));
		this.listComplex = new ListComplex(new Locator(site.webDriver, ExtendedBy.testId("List-Complex")));
		this.comboBoxStandard = new SelectComboBox(site.webDriver, ExtendedBy.testId("ComboBox-Standard-button"));
	}
}