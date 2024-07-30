package saucedemo.pages.productsPage;

import lombok.Getter;
import lombok.experimental.Accessors;
import saucedemo.SauceDemoSite;
import saucedemo.domain.Product;
import saucedemo.enums.SortingDirection;
import ui.core.controls.Button;
import ui.core.controls.ComboBox;
import ui.core.controls.Label;
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
		labelTitle = new Label(site.page.locator("//span[@class='title']"));
		listProducts = new ListProducts(site.page.locator("//div[@class='inventory_list']"));
		buttonCart = new Button(site.page.locator("//a[@class='shopping_cart_link']"));
		labelCartCount = new Label(site.page.locator("//span[@class='shopping_cart_badge']"));
		comboBoxSort = new SelectComboBox(site.page.locator("//select[@class='product_sort_container']"));
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
