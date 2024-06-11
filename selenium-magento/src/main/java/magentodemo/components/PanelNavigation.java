package magentodemo.components;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.controls.Button;
import ui.core.controls.ImageControl;
import ui.core.controls.Label;
import ui.core.controls.LinkControl;
import ui.core.controls.MenuOption;
import ui.core.controls.PanelControl;
import ui.core.controls.TextBox;

@Getter
@Accessors(fluent = true)
public class PanelNavigation extends PanelControl {
	private final LinkControl linkCreateAccount;
	private final String menuSelector = "//nav//span[text()=\"%s\"]";
	private final MenuOption whatsNew;
	private final MenuOption women;

	private final ImageControl logo;
	private final TextBox textBoxSearch;
	private final Button buttonSearch;
	private final Button buttonCart;
	private final Label labelCartCount;
	private final PanelCartPreview panelCartPreview;

	public PanelNavigation(WebDriver driver) {
		this.webDriver = driver;
		this.linkCreateAccount = new LinkControl(this.webDriver, By.xpath("//div[@class='panel wrapper']//a[text()='Create an Account']"));
		this.whatsNew = new MenuOption(this.webDriver, getMenuSelector("What's New"));
		this.women = new MenuOption(this.webDriver, getMenuSelector("Women"));

		this.logo = new ImageControl(this.webDriver, By.xpath("//a[@class='logo']/img"));
		this.textBoxSearch = new TextBox(this.webDriver, By.xpath("//input[@id='search']"));
		this.buttonSearch = new Button(this.webDriver, By.xpath("//button[@class='action search']"));
		this.buttonCart = new Button(this.webDriver, By.xpath("//a[@class='action showcart']"));
		this.labelCartCount = new Label(this.webDriver, By.xpath("//span[@class='counter-number']"));
		this.panelCartPreview = new PanelCartPreview(this.webDriver);
	}

	private By getMenuSelector(String menuName) {
		return By.xpath(String.format(menuSelector, menuName));
	}

	public void searchFor(String search) {
		textBoxSearch.setText(search);
		buttonSearch.click();
	}


}
