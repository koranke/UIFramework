package magentodemo;

import general.TestBase;
import magentodemo.pages.categoryPage.CategoryPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CategoryPageTests extends TestBase {

	@DataProvider(name = "CategoryPageScenarios")
	public Object[][] getCategoryPageScenarios() {
		List<Object[]> data = new ArrayList<>();

		data.add(new Object[]{ "men", "tops-men", "Tops - Men" });
		data.add(new Object[]{ "women", "tops-women", "Tops - Women" });
		data.add(new Object[]{ "gear", "bags", "Bags - Gear" });

		return data.toArray(new Object[][]{});
	}

	@Test(dataProvider = "CategoryPageScenarios")
	public void testOpenCategoryPage(String department, String category, String expectedTitle) {
		MagentoDemoSite site = new MagentoDemoSite();
		CategoryPage categoryPage = site.categoryPage(department, category).open();
		categoryPage.assertIsOpen();
		categoryPage.assertTitle(expectedTitle);
	}

	@Test
	public void testAddSingleFilter() {
		MagentoDemoSite site = new MagentoDemoSite();
		CategoryPage categoryPage = site.categoryPage("men", "tops-men").open();

		categoryPage.panelFilters().sizeColorFilter().selectItem("Size", "M");
		categoryPage.panelFilters().labelFilterItem().assertIsVisible("M");
	}

	@Test
	public void testAddMultipleFilters() {
		MagentoDemoSite site = new MagentoDemoSite();
		CategoryPage categoryPage = site.categoryPage("men", "tops-men").open();

		System.out.println(categoryPage.pagination().getPages());
		categoryPage.pagination().clickPage(2);

		categoryPage.panelFilters().sizeColorFilter().selectItem("Size", "M");
		categoryPage.panelFilters().labelFilterItem().assertIsVisible("M");

		categoryPage.panelFilters().sizeColorFilter().selectItem("Color", "Red");
		categoryPage.panelFilters().labelFilterItem().assertIsVisible("Red");
	}

	@Test
	public void testClearAllFilters() {
		MagentoDemoSite site = new MagentoDemoSite();
		CategoryPage categoryPage = site.categoryPage("men", "tops-men").open();

		categoryPage.panelFilters().sizeColorFilter().selectItem("Size", "M");
		categoryPage.panelFilters().sizeColorFilter().selectItem("Color", "Red");

		categoryPage.panelFilters().buttonClearAll().click();
		categoryPage.panelFilters().labelFilterItem().assertIsNotVisible("M");
		categoryPage.panelFilters().labelFilterItem().assertIsNotVisible("Red");
	}
}
