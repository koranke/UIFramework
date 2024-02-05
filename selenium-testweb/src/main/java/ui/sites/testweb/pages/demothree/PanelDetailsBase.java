package ui.sites.testweb.pages.demothree;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebDriver;
import ui.core.ExtendedBy;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.PanelControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
@Getter
@Accessors(fluent = true)
public abstract class PanelDetailsBase extends PanelControl {
	private Label labelTitle;
	private Button buttonX;
	private Label labelMessage;
	private Label labelOne;
	private Label labelTwo;
	private Label labelThree;
	private ListCars listCars;
	private Button buttonClose;


	public PanelDetailsBase(WebDriver webDriver) {
		this.webDriver = webDriver;
		initialize();
	}

	protected void initialize() {
		this.labelTitle = new Label(webDriver, ExtendedBy.testId("Label-Title"));
		this.buttonX = new Button(webDriver, ExtendedBy.testId("Button-X"));
		this.labelMessage = new Label(webDriver, ExtendedBy.testId("Label-Message"));
		this.labelOne = new Label(webDriver, ExtendedBy.testId("Label-One"));
		this.labelTwo = new Label(webDriver, ExtendedBy.testId("Label-Two"));
		this.labelThree = new Label(webDriver, ExtendedBy.testId("Label-Three"));
		this.listCars = new ListCars(new Locator(webDriver, ExtendedBy.testId("List-Cars")));
		this.buttonClose = new Button(webDriver, ExtendedBy.testId("Button-Close"));
	}
}