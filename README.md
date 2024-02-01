# UI Automation Framework
This framework uses a Page Control Object Model (PCOM) approach for automation.  This involves modeling a web page 
through a series of classes, which represent the web page and any child dialogs ("panels"), tables and lists. It also 
involves classes that model the core controls that a page may contain, like text boxes, check boxes, combo boxes and buttons.  
At the top level is a class that represents the website and exposes all the available pages.  Tests drive actions
by utilizing the PCOM classes rather than directly calling the underlying automation technology, like Selenium or Playwright.

## Getting Started
Before tests can be written for any web page, the PCOM classes for the web page must be created.  This can be done
manually by inspecting the web page, identifying the reference needed for each page element, and constructing the
required classes.  In the most simple case, for a web page with no panels, tables or lists, then a single "page" class
is all that is needed for working with the page.  In addition, a "site" class is needed as a container for any pages.
For web pages that include repeating elements, for example, a table the displays rows of records,

Optionally, PCOM classes can be automatically generated using the Page Control Object Model Generator (see below).
This is only an option if the web page includes embedded "data-testid" attributes for all elements of interest.

Once the site and related page classes have been coded, you can start writing tests. For example...

```java
    public void testNumericTextBox() {
            HomePage homePage = new TestWebSite().homePage().goTo();
            homePage.textBoxNumber.assertIsEnabled();
            homePage.textBoxNumber.typeText("abc");
            homePage.textBoxNumber.assertText("");
            homePage.textBoxNumber.typeText("123");
            homePage.textBoxNumber.assertText("123");
        }
```

## Examples
Two different examples, projects for "swaglabs" and "testweb", are provided to help in getting started.  
The Swaglabs project classes demonstrate a manually-coded model for a website.  The testweb project classes 
demonstrate an auto-generated model for a website.  To use the testweb project, first expose the "testweb-website" directory
html pages through a local web server so that they can be accessed through http://localhost.


## Structure

### Page Class
The simplest model is of a page with only core, non-repeating elements (see below for details on repeating elements).
In this case, a single class is needed to model the page.  All elements should be declared as public members and 
initialized in the constructor.  Use the defined core "control" classes for the different elements.

### Panel Class
Some pages may contain modal dialogs that can be displayed.  A "panel" class models the content of a modal dialog
separate from the "page" class so that tests can more accurately reflect the structure of a page.  Panel classes should
be used for any group of controls that can be displayed and hidden together, but where the parent page URL does not change.

### Repeating Elements in Lists and Tables
A repeating element is where an element can
appear multiple times (typically in a list or table) and where each instance of the element has the same id.  Typically,
this is the case with dynamic content.  For example, a list of products where the products are loaded from a database.

### Site Class
As page classes are created, add these to the "site" class as members for easy access in tests.  



## Page Control Object Model Generator
The PCOM approach involves a lot of boilerplate code.  Rather than manually creating these pages, we can auto-generate
the pages IF the web page has embedded "data-testid" attributes for all controls that automation needs to be able
to work with.  Each page is modeled by a "base" page and a "final" page that extends the base page.  The base page is
created by the PCOM Generator.  If you rerun the PCOM generator for a particular page, the existing "base" page will
be replaced by the new base page.  The "final" page is where custom code can be entered.  This is not touched by the PCOM
Generator. Use the "final" page for any custom page methods or for controls that need to be manually defined.
You can run the PCOM generator through the test class "PageGeneratorTests".

To run the PCOM generator, open the test class "PageGeneratorTests" and do the following...
1. Work with the developer to have the data-testid attributes added and deployed.
2. If this is your first time to do this, add the below setting in IntelliJ, Help->Edit custom VM options and restart.
   -Deditable.java.test.console=true

3. In the PageGeneratorTests class, go to the declarations section for your target website.
4. Add private member declaration for new page that includes the portal URL for the page.  For example...

```java
private static final String homeUrl = "/home.html";
```

5. In the constructor add the page URL to url map along with name for page.  For example...

```java
        pageUrlMap.put(homeUrl, "Home");
```

6. Update the method "makeSinglePageForSite" for new page and run.
7. This will open the website and navigate to the desired page (if possible) and scan all available controls.  
   In the output window, there will be a prompt to either scan for more controls or finish.  Some pages may have hidden
   controls that are only visible after clicking a button or doing some action.  If needed, perform the action to expose
   other controls and then enter "Y" to scan it.  Otherwise, press ENTER to finish.

## Using the UI framework
All tests need to create an instance of the desired website in order to interact with web pages.  The website
class handles initialization and configuration of the needed Playwright resources and is the link to all defined
web pages and their controls.  Example test...

```java
    public void testSomething() {
            HomePage homePage = new TestWebSite().homePage().goTo();
            homePage.textBoxWhatever.assertIsNotEnabled();
            homePage.textBoxEmail.assertIsEnabled();
            homePage.textBoxEmail.setText("george@outlook.com");
            homePage.textBoxNumber.typeText("abc");
            homePage.textBoxNumber.assertText("");
            homePage.textBoxNumber.typeText("123");
            homePage.textBoxNumber.assertText("123");
            homePage.buttonSubmit.click();
        }
```
