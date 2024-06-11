package magentodemo;

import general.TestBase;
import magentodemo.components.ListCartPreviewItems;
import magentodemo.components.PanelCartPreview;
import magentodemo.domain.Product;
import magentodemo.pages.homePage.HomePage;
import org.testng.annotations.Test;

import java.util.List;

public class CartPreviewTests extends TestBase {
	private List<Product> products;

	public CartPreviewTests() {
		this.products = new MagentoDemoSite().homePage().open().listProducts().getAllProducts();
	}

	@Test
	public void testViewCardPreview() {
		HomePage homePage = new MagentoDemoSite().homePage().open();
		homePage.addProductToCart(products.get(0), 0, 0);
		homePage.panelNavigation().buttonCart().click();

		PanelCartPreview panelCartPreview = homePage.panelNavigation().panelCartPreview();
		panelCartPreview.buttonCheckout().assertIsVisible();
		panelCartPreview.labelCartCount().assertText("1");
		panelCartPreview.labelSubtotal().assertText(products.get(0).getPrice());
		panelCartPreview.buttonClose().click();
		panelCartPreview.buttonCheckout().assertIsNotVisible();
	}

	@Test
	public void testViewCartPreviewDetails() {
		HomePage homePage = new MagentoDemoSite().homePage().open();
		homePage.addProductToCart(products.get(0), 0, 0);
		homePage.panelNavigation().labelCartCount().assertIsVisible();
		homePage.panelNavigation().buttonCart().click();

		ListCartPreviewItems listCartPreviewItems = homePage.panelNavigation().panelCartPreview().listCartPreviewItems();
		listCartPreviewItems.usingLabelName().withRow(products.get(0).getName()).labelName().assertIsVisible();
		listCartPreviewItems.labelSize().assertIsNotVisible();
		listCartPreviewItems.labelColor().assertIsNotVisible();
		listCartPreviewItems.labelDetails().click();

		listCartPreviewItems.labelSize().assertIsVisible();
		listCartPreviewItems.labelColor().assertIsVisible();
		listCartPreviewItems.labelSize().assertText(products.get(0).getSizes().get(0));
		listCartPreviewItems.labelColor().assertText(products.get(0).getColors().get(0));
		listCartPreviewItems.labelPrice().assertText(products.get(0).getPrice());
	}

	@Test
	public void testViewCartPreviewDetailsWithMultipleProducts() {
		HomePage homePage = new MagentoDemoSite().homePage().open();

		homePage.addProductToCart(products.get(0), 0, 0);
		homePage.addProductToCart(products.get(1), 1, 1);

		homePage.panelNavigation().buttonCart().click();
		homePage.panelNavigation().panelCartPreview().verifySubTotal(products.subList(0, 2));

		ListCartPreviewItems listCartPreviewItems = homePage.panelNavigation().panelCartPreview().listCartPreviewItems();
		listCartPreviewItems.verifyItem(products.get(0), 0, 0);
		listCartPreviewItems.verifyItem(products.get(1), 1, 1);
	}

}
