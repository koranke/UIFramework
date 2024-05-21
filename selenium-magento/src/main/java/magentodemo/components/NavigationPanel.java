package magentodemo.components;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.controls.Button;
import ui.core.controls.ImageControl;
import ui.core.controls.MenuOption;
import ui.core.controls.PanelControl;
import ui.core.controls.TextBox;

@Getter
@Accessors(fluent = true)
public class NavigationPanel extends PanelControl {
	private final String menuSelector = "//nav//span[text()=\"%s\"]";
	private final MenuOption whatsNew;
	private final MenuOption women;

	private final ImageControl logo;
	private final TextBox textBoxSearch;
	private final Button buttonSearch;

	public NavigationPanel(WebDriver driver) {
		this.webDriver = driver;
		this.whatsNew = new MenuOption(this.webDriver, getMenuSelector("What's New"));
		this.women = new MenuOption(this.webDriver, getMenuSelector("Women"));

		this.logo = new ImageControl(this.webDriver, By.xpath("//a[@class='logo']/img"));
		this.textBoxSearch = new TextBox(this.webDriver, By.xpath("//input[@id='search']"));
		this.buttonSearch = new Button(this.webDriver, By.xpath("//button[@class='action search']"));
	}

	private By getMenuSelector(String menuName) {
		return By.xpath(String.format(menuSelector, menuName));
	}
}
