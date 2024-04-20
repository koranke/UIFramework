package saucedemo.general;

import saucedemo.domain.Product;
import saucedemo.enums.SortingDirection;
import saucedemo.pages.cartPage.CartPage;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductsHelper {

	public static void verifyProductsSortOrder(List<Product> products, String sortingField, SortingDirection sortingDirection) {
		switch (sortingField) {
			case "Name":
				List<String> productNames = products.stream()
						.map(Product::getName)
						.toList();

				if (sortingDirection == SortingDirection.DESCENDING) {
					assertThat(productNames).isSortedAccordingTo(Comparator.reverseOrder());
				} else {
					assertThat(productNames).isSorted();
				}
				break;
			case "Price":
				List<Double> productPrices = products.stream()
						.map(Product::getPrice)
						.toList();

				if (sortingDirection == SortingDirection.DESCENDING) {
					assertThat(productPrices).isSortedAccordingTo((a, b) -> Double.compare(b, a));
				} else {
					assertThat(productPrices).isSorted();
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown sorting field: " + sortingField);
		}
	}

	public static void verifyProductsInCart(List<Product> expectedProducts, CartPage cartPage) {
		if (expectedProducts == null || expectedProducts.isEmpty()) {
			cartPage.listCartItems().assertIsNotVisible();
		} else {
			for (Product product : expectedProducts) {
				cartPage.listCartItems().usingLabelName().withRow(product.getName()).labelName().assertText(product.getName());
				cartPage.listCartItems().labelDescription().assertText(product.getDescription());
				cartPage.listCartItems().labelPrice().assertText(String.format("$%.2f", product.getPrice()));
				cartPage.listCartItems().labelQuantity().assertText("1");
			}
		}
	}
}
