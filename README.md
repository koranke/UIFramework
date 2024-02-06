# UI Automation Framework

<!-- TOC -->
* [UI Automation Framework](#ui-automation-framework)
  * [Features](#features)
  * [Project Structure](#project-structure)
    * [Core](#core)
    * [Playwright](#playwright)
    * [Selenium](#selenium)
    * [Playwright-swaglabs / Selenium-swaglabs](#playwright-swaglabs--selenium-swaglabs)
    * [Playwright-testweb / Selenium-testweb](#playwright-testweb--selenium-testweb)
  * [Getting Started](#getting-started)
  * [Examples](#examples)
  * [Components](#components)
    * [Page Class](#page-class)
    * [Panel Class](#panel-class)
    * [Repeating Elements in Lists and Tables](#repeating-elements-in-lists-and-tables)
    * [Site Class](#site-class)
  * [Page Control Object Model Generator](#page-control-object-model-generator)
  * [Using the UI framework](#using-the-ui-framework)
    * [Creating Model Classes](#creating-model-classes)
    * [Handling repeating Elements](#handling-repeating-elements)
      * [Step One](#step-one)
      * [Step Two](#step-two)
      * [Step Three](#step-three)
    * [Creating Tests](#creating-tests)
<!-- TOC -->

The goal of this framework is to make tests as easy to write as possible, minimizing test complexity and cost of maintenance.
It uses a Page Control Object Model (PCOM) approach for automation.  This involves modeling a web page 
through a series of classes, which represent the web page and any child dialogs ("panels"), tables and lists. It also 
involves classes that model the core controls that a page may contain, like text boxes, check boxes, combo boxes and buttons.
At the top level is a class that represents the website and exposes all the available pages.  Tests drive actions
by utilizing the PCOM classes rather than directly calling the underlying UI automation technology.

Currently, this project is more of a proof-of-concept that demonstrates certain patterns and approaches to UI automation
rather than a fully-featured framework that can be dropped in as a dependency and immediately used.  It handles standard
HTML elements but may be missing some element support, and it has no element support for any popular custom web control
libraries.  There are two versions, one for Selenium and one for Playwright.  From a test perspective, they work the same. 

## Features
* Easily discoverable and easy to code, thanks to auto-completion in the IDE.  Standard line in a test follows the pattern
```website.pagex().controlx().actionx()``` or ```website.pagex().actionx()```. After each dot, IDE code completion will display the available options. 
For example, what pages are available for the website, what controls are available for a page, what actions are available for a 
particular page or control?
* Automatic logging of test activity and automatic log rotation between test runs.
* Automatic screenshots on a test failure.
* Built-in asserts for controls like textboxes, etc.  For example, ```welcomePage.textBoxName().assertText("George")```.
* Built-in visual asserts.  For example, ```homepage.assertScreenshot()```.  Configurable to permit some pixel differences.
Also supports masking (hiding) some controls on a page so that they are not included in the screenshot comparison. 
* Configurable through a properties file, optionally for different environments, and also supports overrides through
environment variables.  Allows for setting things like target browser and headless mode.
* Built-in "slow time" feature ("slowmo" feature from Playwright) for both Playwright and Selenium to aid in debugging.
* Built-in support for running tests in parallel.
* Built-in support for initializing and cleaning up Playwright or Selenium resources.
* Easily interact with repeating elements.  For example, ```productsPage.listProducts().usingRow(2).labelPrice().assertText("12.50")"```

## Project Structure

### Core
Common code regardless of the underlying UI framework.

### Playwright
PCOM framework using Playwright.

### Selenium
PCOM framework using Selenium.

### Playwright-swaglabs / Selenium-swaglabs
Example PCOM classes and tests when hand-coding classes.  Uses the live "swaglabs" demo website as the application under test.

### Playwright-testweb / Selenium-testweb
Example PCOM classes and tests when auto-generating classes.  Uses the sample "testweb" website as the application under test.
You must run a local webserver to host the website before running tests.


## Getting Started
Before tests can be written for any web page, the PCOM classes for the web page must be created.  This can be done by
manually inspecting the web page, identifying the reference needed for each page element, and constructing the
required classes.  In the most simple case, for a web page with no panels, tables or lists, then a single "page" class
is all that is needed for working with the page.  In addition, a "site" class is needed as a container for any pages.
For web pages that include repeating elements, for example, a table with rows of records, then a class needs to
be created for the table that defines how to reference the table, a row, and also defines the repeating elements
in each row.

Optionally, PCOM classes can be automatically generated using the Page Control Object Generator (see below).
This is only an option if the web page includes embedded "data-testid" attributes for all elements of interest.

Once the site and related page classes have been coded, you can start writing tests. For example...

```java
    public void testNumericTextBox() {
            HomePage homePage = new TestWebSite().homePage().goTo();
            homePage.textBoxNumber().assertIsEnabled();
            homePage.textBoxNumber().typeText("abc");
            homePage.textBoxNumber().assertText("");
            homePage.textBoxNumber().typeText("123");
            homePage.textBoxNumber().assertText("123");
        }
```

## Examples
Two different example projects, "swaglabs" and "testweb", are provided to help in getting started.  
The Swaglabs project classes demonstrate a manually-coded model for a website.  The testweb project classes 
demonstrate an auto-generated model for a website.  To use the testweb project, first expose the "testweb-website" directory
html pages through a local web server so that they can be accessed through http://localhost.


## Components

### Page Class
The simplest model is of a page with only core, non-repeating elements (see below for details on repeating elements).
In this case, a single class is needed to model the page.  All elements should be declared as public members and 
initialized in the constructor.  Use the defined core "control" classes for the different elements.

### Panel Class
Some pages may contain modal dialogs that can be displayed.  A "panel" class models the content of a modal dialog
separate from the "page" class so that tests can more accurately reflect the structure of a page.  Panel classes should
be used for any group of controls that can be displayed and hidden together, but where the parent page URL does not change.
Additionally, panel classes can be used to help break up large, complex pages into more manageable sections.

### Repeating Elements in Lists and Tables
A repeating element is where an element can appear multiple times (for example, in a list or table) and where each instance 
of the element has the same id.  Typically, this is the case with dynamic content.  For example, a list of products 
where the products are loaded from a database.

### Site Class
As page classes are created, add these to the "site" class as members for easy access in tests.  



## Page Control Object Model Generator
The PCOM approach involves a lot of boilerplate code.  Rather than manually creating these pages, we can auto-generate
the pages IF the web page has embedded "data-testid" attributes for all controls that the automation needs to
work with.  Each page is modeled by a "base" page and a "final" page that extends the base page.  The base page is
created by the PCOM Generator.  If you rerun the PCOM generator for a particular page, the existing "base" page will
be replaced by the new base page.  The "final" page is where custom code can be entered.  This is not touched by the PCOM
Generator. Use the "final" page for any custom page methods or for controls that need to be manually defined.
You can run the PCOM generator through the test class "PageGeneratorTests".

To run the PCOM generator, open the test class "PageGeneratorTests" and do the following...
1. Work with the developer to have the data-testid attributes added and deployed.
2. If this is your first time to do this, add the below setting in IntelliJ, Help->Edit custom VM options and restart.
   -Deditable.java.test.console=true

3. In the "selenium" or "playwright" project, update the class "TargetPortal" to include the new website you want to work on.
4. In the PageGeneratorTests class, go to the declarations section for your target website.
5. Update the projectPath and baseClassPackagePath variables to configure the output location for class files.  Output location
should be something like src/main/java/ui/sites/<sitename>.
6. In the output location, copy the "testweb" template package and update names to match the target website.
7. Add a private member declaration for new page that includes the website URL for the page.  For example...

```java
private static final String homeUrl = "/home.html";
```

8. In the constructor, add the page URL to the url map along with the name for the page.  For example...

```java
        pageUrlMap.put(homeUrl, "Home");
```
9. Update the method "makeSinglePageForSite" for the new page and run.
10. This will open the website and navigate to the desired page (if possible) and scan all available controls.  
In the output window, there will be a prompt to either scan for more controls or finish.  Some pages may have hidden
controls that are only visible after clicking a button or doing some action.  If needed, perform the action to expose
other controls and then enter "Y" to scan it.  Otherwise, press ENTER to finish.

Note that if the website includes a login page that must be navigated to before reaching other pages, then first generate
the classes for the login page and update the "site" class with this page before working on other pages.

## Using the UI framework
Step one is to create all needed website page model classes.  Step two is to create the tests.


### Creating Model Classes
The following examples are for Playwright.  The same instructions apply for Selenium, but some parts of the examples
will be different.

1. Under the package ui.core.sites, create a new package for your website classes.
2. Create the "site" class under the new package.  This will be the entry point for accessing all pages for a website.  
As new pages are coded, add them as class members here. Extend from the base "Site" class.  For example...

```java
public class SauceDemoSite extends Site<SauceDemoSite> {

	public SauceDemoSite() {
		super();
		initialize();
	}

	private void initialize() {
		baseUrl = "https://www.saucedemo.com/";
	}
}
```

3. Under the website package, create a new package for "pages".  For example, "ui.core.sites.saucedemo.pages".  Create a 
"base page" class there that all web page classes can extend from.  This is where you can add any custom helper methods 
that should be accessible to all pages for the website.  For example...

```java
public abstract class BaseSauceDemoPage<T> extends BasePage<T> {

   @Getter
   protected SauceDemoSite site;

   public BaseSauceDemoPage(SauceDemoSite site, String path) {
      super(site.page, site.baseUrl, path);
      this.site = site;
   }
}
```

4. You are now ready to model the first web page.  If the website includes a login page, start with that.  Create a new 
package under the "pages" package.  For example, "ui.core.sites.saucedemo.pages.login".  Create a new class for the
target page that extends from the base website page.  This is where we define all the page elements and where we can
add any custom helper methods for the page. For example...

```java
@Getter
@Accessors(fluent = true)
public class LoginPage extends BaseSauceDemoPage<LoginPage> {

     public LoginPage(SauceDemoSite site) {
          super(site, "");
     }
}
```
For the super constructor call, pass in the endpoint path for the page, excluding the base path.  If the base path
and endpoint are the same, use "".

5. Add declarations for any core controls and initialize each in the constructor.  For example...
```java
@Getter
@Accessors(fluent = true)
public class LoginPage extends BaseSauceDemoPage<LoginPage> {
     private TextBox textBoxUserName;

     public LoginPage(SauceDemoSite site) {
          super(site, "");
          textBoxUserName = new TextBox(page.locator("#user-name"));
     }
}
```
Once all controls are coded, you can start adding tests.  If you encounter an element that is not supported yet, for
example, a custom control, create a new class for the control in the package ui.core.controls and extend from the
BaseControl class and add any needed supporting methods.  For example...

```java
public class MyCustomControl extends BaseControl {

     public MyCustomControl(Locator locator) {
          this.locator = locator;
     }

     public void myCustommethod() {
          //do something here.
     }
}

```

### Handling repeating Elements
Web pages will often have repeating elements, especially pages that display dynamic content.  It could be a list of messages, 
products, or other records from a database.  Structurally, these repeating elements could be arranged as part of an 
HTML list or table, but they could also be arranged in a custom manner using DIV elements, for example.  They could be
arranged horizontally or vertically, or even wrapping into multiple columns.

If the content is static, you have the option to treat each item or group of items in the list as unique controls.  For example,
label1, label2, etc.  However, if the content is dynamic, such that we could have 0 rows up to 20 rows, then repeating elements are needed.

#### Step One
Create a class in the "page" package called XxxList that extends from ListControl.  For example...

```java
public class ListProducts extends ListControl<ListProducts> {

	public ListProducts(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//div[@class='inventory_item']";

	}
}
```

#### Step Two
Identify all page elements of interest in a single "row" and note their references.  For each, create a new private repeating
element member and initialize it in the constructor.  For example...

```java
public class ListProducts extends ListControl<ListProducts> {
	private final RepeatingControl<Label> labelName;
	private final RepeatingControl<Button> buttonAddToCart;

	public ListProducts(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//div[@class='inventory_item']";

		labelName = new RepeatingControl<>(
				locator,
				"//div[@class='inventory_item_name ']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);
		buttonAddToCart = new RepeatingControl<>(
				locator, "Add to cart",
				LocatorMethod.TEXT,
				Button::new,
				rowLocatorPattern,
				hasHeader
		);
	}
}
```

#### Step Three
Finally, add a "using" method for all "label" controls and add a "getter" method for all controls.  For example...

```java
public class ListProducts extends ListControl<ListProducts> {
	private final RepeatingControl<Label> labelName;
	private final RepeatingControl<Button> buttonAddToCart;

	public ListProducts(Locator locator) {
		super(locator);
		this.hasHeader = false;
		this.rowLocatorPattern = "//div[@class='inventory_item']";

		labelName = new RepeatingControl<>(
				locator,
				"//div[@class='inventory_item_name ']",
				LocatorMethod.XPATH,
				Label::new,
				rowLocatorPattern,
				hasHeader
		);
		buttonAddToCart = new RepeatingControl<>(
				locator, "Add to cart",
				LocatorMethod.TEXT,
				Button::new,
				rowLocatorPattern,
				hasHeader
		);
	}

	public ListProducts usingLabelName() {
		this.searchLabel = labelName;
		return this;
	}

	public Label labelName() {
		return labelName.get(currentRow);
	}

	public Button buttonAddToCart() {
		return buttonAddToCart.get(currentRow);
	}
}
```



### Creating Tests
All tests need to create an instance of the desired website in order to interact with web pages.  The website
class handles initialization and configuration of the needed Playwright or Selenium resources and is the link to all defined
web pages and their controls.  Example test...

```java
     public void testSomething() {
          HomePage homePage = new TestWebSite().homePage().goTo();
     }
```
