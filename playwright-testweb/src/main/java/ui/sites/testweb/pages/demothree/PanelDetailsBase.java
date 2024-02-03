package ui.sites.testweb.pages.demothree;

import com.microsoft.playwright.Page;
import ui.core.controls.Button;
import ui.core.controls.Label;
import ui.core.controls.PanelControl;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName", "checkstyle:LineLength"})
public abstract class PanelDetailsBase extends PanelControl {
	public Label labelTitle;
	public Button buttonX;
	public Label labelMessage;
	public Label labelOne;
	public Label labelTwo;
	public Label labelThree;
	public ListCars listCars;
	public Button buttonClose;


	public PanelDetailsBase(Page page) {
		this.page = page;
		initialize();
	}

	protected void initialize() {
		this.labelTitle = new Label(page.getByTestId("Label-Title"));
		this.buttonX = new Button(page.getByTestId("Button-X"));
		this.labelMessage = new Label(page.getByTestId("Label-Message"));
		this.labelOne = new Label(page.getByTestId("Label-One"));
		this.labelTwo = new Label(page.getByTestId("Label-Two"));
		this.labelThree = new Label(page.getByTestId("Label-Three"));
		this.listCars = new ListCars(page.getByTestId("List-Cars"));
		this.buttonClose = new Button(page.getByTestId("Button-Close"));
	}
}