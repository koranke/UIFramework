package magentodemo.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.PaginationControl;

public class PaginationMain extends PaginationControl {

	public PaginationMain(WebDriver driver) {
		super(new Locator(driver, By.xpath("//div[@class='products wrapper grid products-grid']/following-sibling::div")));
		this.pageLocatorPattern = ".//li[@class='item']/a";
		this.pageLocatorPatternWithIndex = this.pageLocatorPattern + "/span[text()=%d]";

		this.buttonPrior = new Button(new Locator(driver, By.xpath(".//a[@title='Previous']")).withParent(this.locator));
		this.buttonNext = new Button(new Locator(driver, By.xpath(".//a[@title='Next']")).withParent(this.locator));
	}

}
