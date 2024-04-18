package saucedemo.pages.productsPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import saucedemo.domain.Product;
import saucedemo.enums.SortingDirection;
import ui.core.Locator;
import ui.core.controls.Button;
import ui.core.controls.ComboBox;
import ui.core.controls.Label;
import saucedemo.SauceDemoSite;
import saucedemo.pages.BaseSauceDemoPage;
import ui.core.controls.SelectComboBox;

import java.util.ArrayList;
import java.util.List;

@Getter()
@Accessors(fluent = true)
public class ProductsPage extends BaseSauceDemoPage<ProductsPage> {
	private final Label labelTitle;
	private final ListProducts listProducts;
	private final Button buttonCart;
	private final Label labelCartCount;
	private final ComboBox comboBoxSort;

	public ProductsPage(SauceDemoSite site) {
		super(site, "inventory.html");
		labelTitle = new Label(site.webDriver, By.xpath("//span[@class='title']"));
		buttonCart = new Button(site.webDriver, By.className("shopping_cart_link"));
		labelCartCount = new Label(site.webDriver, By.xpath("//span[@class='shopping_cart_badge']"));
		listProducts = new ListProducts(new Locator(site.webDriver, By.xpath("//div[@class='inventory_list']")));
		comboBoxSort = new SelectComboBox(site.webDriver, By.className("product_sort_container"));
	}

	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		for (int i = 1; i <= listProducts().getRowCount(); i++) {
			site.productsPage().listProducts().withRow(i);
			products.add(listProducts().getCurrentProduct());
		}
		return products;
	}

	public void setSortingOption(String sortingField, SortingDirection sortingDirection) {
		String sortingOption = sortingField;
		if (sortingDirection == SortingDirection.ASCENDING) {
			sortingOption += sortingField.equals("Name") ? " (A to Z)" : " (low to high)";
		} else {
			sortingOption += sortingField.equals("Name") ? " (Z to A)" : " (high to low)";
		}

		site.productsPage().comboBoxSort().setText(sortingOption);
	}

}
