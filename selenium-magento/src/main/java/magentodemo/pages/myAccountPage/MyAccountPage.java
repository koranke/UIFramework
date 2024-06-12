package magentodemo.pages.myAccountPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import magentodemo.MagentoDemoSite;
import magentodemo.domain.AuthBody;
import magentodemo.pages.BaseMagentoDemoPage;
import org.openqa.selenium.By;
import ui.core.controls.Button;
import ui.core.controls.Label;

@Getter
@Accessors(fluent = true)
public class MyAccountPage extends BaseMagentoDemoPage<MyAccountPage> {
	private final Label labelContactInfo;
	private final Button buttonEditContactInfo;
	private final Button buttonChangePassword;
	private final Label labelBillingAddress;
	private final Label labelShippingAddress;
	private final Button buttonEditBillingAddress;
	private final Button buttonEditShippingAddress;

	public MyAccountPage(MagentoDemoSite site) {
		super(site, "customer/account/");
		labelContactInfo = new Label(this.getWebDriver(), By.xpath("//strong[./span[text()='Contact Information']]/following-sibling::div[@class='box-content']/p"));
		buttonEditContactInfo = new Button(this.getWebDriver(), By.xpath("//strong[./span[text()='Contact Information']]/following-sibling::div[@class='box-actions']/a[1]"));
		buttonChangePassword = new Button(this.getWebDriver(), By.xpath("//strong[./span[text()='Contact Information']]/following-sibling::div[@class='box-actions']/a[2]"));
		labelBillingAddress = new Label(this.getWebDriver(), By.xpath("//strong[./span[text()='Default Billing Address']]/following-sibling::div/address"));
		labelShippingAddress = new Label(this.getWebDriver(), By.xpath("//strong[./span[text()='Default Shipping Address']]/following-sibling::div/address"));
		buttonEditBillingAddress = new Button(this.getWebDriver(), By.xpath("//strong[./span[text()='Default Billing Address']]/following-sibling::div/a"));
		buttonEditShippingAddress = new Button(this.getWebDriver(), By.xpath("//strong[./span[text()='Default Billing Address']]/following-sibling::div/a"));
	}

	public MyAccountPage open(AuthBody authBody) {
		if (!site.isLoggedIn()) {
			site.loginPage().login(authBody, this);
		} else {
			open();
		}
		return this;
	}

}
