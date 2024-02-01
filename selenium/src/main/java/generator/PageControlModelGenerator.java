package generator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.core.Site;
import ui.core.enums.TargetPortal;
import utilities.FileHelper;
import utilities.Log;
import utilities.ObjectHelper;
import utilities.StringHelper;
import utilities.SystemHelper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
Class for creating Page Control Object Model classes for UI automation.
Input: Either a saved local HTML file (for testing) or a live portal page URL.
Output: A "base" page class, a "final" page class, along with any table classes or panel classes that are children
of a page.  All should be contained within the same package for the page.

For example:
package "tenants", which would contain the files/classes like the following
 - TenantsBasePage
 - TenantsPage
 - TenantAccountTable
 - NewTenantPanel

"Base" pages and panels will always get replaced if you rerun the generator for a particular page.  "Final"
pages and panels, along with tables, will never get replaced, as these may contain custom code.

 */
public class PageControlModelGenerator {
    private static final Log log = Log.getInstance();
    private static final Map<ControlType, String> importMap = getImportMap();
    private static final String coreControlsPath = "ui.core.controls.";
    private static final String sitePathTemplate = "ui.sites.%s";
    private static final String sitePagePathTemplate = "ui.sites.%s.pages.%s";

    private static Map<ControlType, String> getImportMap() {
        Map<ControlType, String> importMap = new HashMap<>(
                Map.of(
                        ControlType.BUTTON, String.format("import %sButton", coreControlsPath),
                        ControlType.COMBOBOX, String.format("import %sComboBox", coreControlsPath),
                        ControlType.CUSTOM_COMBOBOX, String.format("import %sCustomComboBox", coreControlsPath),
                        ControlType.SELECT_COMBOBOX, String.format("import %sSelectComboBox", coreControlsPath),
                        ControlType.TEXTBOX, String.format("import %sTextBox", coreControlsPath),
                        ControlType.LABEL, String.format("import %sLabel", coreControlsPath),
                        ControlType.RADIOBUTTON, String.format("import %sFlagControl", coreControlsPath),
                        ControlType.CHECKBOX, String.format("import %sFlagControl", coreControlsPath),
                        ControlType.FLAGCONTROL, String.format("import %sFlagControl", coreControlsPath),
                        ControlType.TABGROUP, String.format("import %sTabGroup", coreControlsPath)
                ));
        importMap.put(ControlType.TABLE, "import ui.core.Locator");
        importMap.put(ControlType.LIST, "import ui.core.Locator");

        return importMap;
    }

    /**
     * makePage.
     * @param basePath basePath
     * @param pageUrl pageUrl
     * @param pageName pageName
     * @param targetPortal targetPortal
     */
    public static void makePage(String basePath, String pageUrl, String pageName, TargetPortal targetPortal) {
        ClassBuilder classBuilder = new ClassBuilder()
                .withClassPackageName(getPackageName(targetPortal, pageName))
                .withImports(getCoreImports(targetPortal))
                .withClassAnnotations(List.of(getClassAnnotation()))
                .withClassName(pageName + "BasePage")
                .withClassDeclaration(getPageClass(targetPortal, pageName + "BasePage"))
                ;

        Map<ElementType, List<WebElement>> elementMap = getAllTaggedElementsForPage(pageUrl, targetPortal);
        List<WebElement> elements = elementMap.get(ElementType.GENERAL);
        List<String> processedElements = new ArrayList<>();
        Map<String, List<String>> comboBoxes = new HashMap<>();

        classBuilder.setMemberDeclarations(
                getClassMemberDeclarationsForElements(elements, processedElements, classBuilder.getImports(), comboBoxes)
        );

        classBuilder.getMemberDeclarations().addAll(
                getComboBoxClassMemberDeclarations(comboBoxes)
        );

        classBuilder.setConstructors(
                List.of(getPageConstructorMethod(pageName + "BasePage", targetPortal, pageUrl))
        );

        classBuilder.setMemberInitializations(getPageMemberInitializations(elements, comboBoxes, classBuilder.getImports()));

        String fullPath = String.format("%s%s/", basePath, pageName);

        //create package
        new File(fullPath.toLowerCase()).mkdirs();

        //create base page
        FileHelper.createFile(fullPath, pageName + "BasePage.java", classBuilder.build());

        makeFinalPage(targetPortal, fullPath, pageName);

        String packageName = getPackageName(targetPortal, pageName);

        //create list classes
        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");

            ControlType type = getType(testId);
            if (type == ControlType.LIST || type == ControlType.TABLE) {
                String listName = getName(testId);
                List<WebElement> elementsForThisList =
                        getElementsForList(element, elementMap.get(type == ControlType.LIST ? ElementType.GENERAL_LIST : ElementType.GENERAL_TABLE_ROW));
                makeList(targetPortal, fullPath, packageName, pageName, listName, elementsForThisList);
            }
        }

        //create panel classes
        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");

            ControlType type = getType(testId);
            if (type == ControlType.PANEL) {
                String panelName = getName(testId);
                panelName = getUpperCaseInitial(panelName);
                List<WebElement> panelElements = getElementsForPanel(element);
                makePanel(targetPortal, fullPath, packageName, panelName, panelElements, elementMap.get(ElementType.PANEL_LIST));
            }
        }
    }

    private static void makeList(TargetPortal targetPortal, String fullPath, String packageName, String baseName,
            String listName, List<WebElement> elements) {

        listName = listName.substring(0, 1).toUpperCase() + listName.substring(1);
        if (packageName == null) {
            packageName = getPackage(targetPortal, baseName);
        }
        if (elements == null) elements = new ArrayList<>();
        Map<String, List<String>> comboBoxes = new HashMap<>();
        List<String> processedElements = new ArrayList<>();

        ClassBuilder classBuilder = new ClassBuilder()
                .withClassPackageName(packageName)
                .withImports(getCoreListImports())
                .withClassName(listName)
                .withClassAnnotations(List.of(getClassAnnotation()))
                .withClassDeclaration(getListClass( listName + "Base"))
                ;

        classBuilder.withMemberDeclarations(getListClassMemberDeclarationForElement(elements, processedElements, classBuilder.getImports(), comboBoxes));
        classBuilder.getMemberDeclarations().addAll(getListComboBoxClassMemberDeclarations(comboBoxes));

        classBuilder.setConstructors(
                List.of(getListConstructor(listName + "Base"))
        );

        classBuilder.setMemberInitializations(getListMemberInitializations(elements, comboBoxes, classBuilder.getImports()));

        String finalContent = classBuilder.build();

        FileHelper.createFile(fullPath, listName + "Base.java", finalContent);
        makeFinalList(fullPath, packageName, listName);
    }

    private static void makePanel(TargetPortal targetPortal, String fullPath, String packageName, String panelName,
            List<WebElement> elements, List<WebElement> listElements) {

        Map<String, List<String>> comboBoxes = new HashMap<>();

        ClassBuilder classBuilder = new ClassBuilder()
                .withClassPackageName(packageName)
                .withImports(getCorePanelImports())
                .withClassName(panelName + "Base")
                .withClassAnnotations(List.of(getClassAnnotation()))
                .withClassDeclaration(getPanelClass(panelName + "Base"))
                ;

        List<String> processedElements = new ArrayList<>();
        classBuilder.withMemberDeclarations(getClassMemberDeclarationsForElements(elements, processedElements, classBuilder.getImports(), comboBoxes));
        classBuilder.getMemberDeclarations().addAll(getComboBoxClassMemberDeclarations(comboBoxes));

        classBuilder.setConstructors(
                List.of(getPanelConstructor(panelName + "Base"))
        );

        classBuilder.withMemberInitializations(getPanelMemberInitializations(elements, comboBoxes, classBuilder.getImports()));

        FileHelper.createFile(fullPath, panelName + "Base.java", classBuilder.build());

        //create list classes
        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");

            ControlType type = getType(testId);
            if (type == ControlType.LIST || type == ControlType.TABLE) {
                String listName = getName(testId);
                List<WebElement> elementsForThisList = getElementsForList(element, listElements);
                makeList(targetPortal, fullPath, packageName, panelName, listName, elementsForThisList);
            }
        }

        makeFinalPanel(fullPath, packageName, panelName);
    }

    private static void makeFinalPage(TargetPortal targetPortal, String fullPath, String pageName) {
        if (!FileHelper.isFileExists(fullPath, pageName + "Page.java")) {
            String portalName = targetPortal.getSiteName();

            String sitePath = String.format(sitePathTemplate, targetPortal.getPackageName()) + "." + targetPortal.getSiteName();

            String content = String.format(getPackage(targetPortal, pageName)
                    + "\n"
                    + "import %s;\n"
                    + "\n"
                    + "@SuppressWarnings(\"checkstyle:AbbreviationAsWordInName\")\n"
                    + "public class %sPage extends %sBasePage<%sPage> {\n"
                    + "\n"
                    + "    public %sPage(%s portal) {\n"
                    + "        super(portal);\n"
                    + "    }\n"
                    + "\n"
                    + "}", sitePath, pageName, pageName, pageName, pageName, portalName);

            FileHelper.createFile(fullPath, pageName + "Page.java", content);
        }
    }

    private static void makeFinalPanel(String fullPath, String packageName, String panelName) {
        if (!FileHelper.isFileExists(fullPath, panelName + ".java")) {
            String content = String.format("package %s;\n"
                    + "\n"
                    + "import org.openqa.selenium.WebDriver;\n"
                    + "\n"
                    + "@SuppressWarnings(\"checkstyle:AbbreviationAsWordInName\")\n"
                    + "public class %s extends %sBase {\n"
                    + "\n"
                    + "    public %s(WebDriver webDriver) {\n"
                    + "        super(webDriver);\n"
                    + "    }\n"
                    + "}\n", packageName, panelName, panelName, panelName);

            FileHelper.createFile(fullPath, panelName + ".java", content);
        }
    }

    private static void makeFinalList(String fullPath, String packageName, String listName) {
        if (!FileHelper.isFileExists(fullPath, listName + ".java")) {
            String content = String.format("package %s;\n"
                    + "\n"
                    + "import ui.core.Locator;\n"
                    + "\n"
                    + "@SuppressWarnings(\"checkstyle:AbbreviationAsWordInName\")\n"
                    + "public class %s extends %sBase {\n"
                    + "\n"
                    + "    public %s(Locator locator) {\n"
                    + "        super(locator);\n"
                    + "        //Add any overrides here.  Method \"initialize\" must be called last.\n"
                    + "        this.initialize();\n"
                    + "    }\n"
                    + "}\n", packageName, listName, listName, listName);

            FileHelper.createFile(fullPath, listName + ".java", content);
        }
    }

    private static String getPageConstructorMethod(String pageName, TargetPortal targetPortal, String pageUrl) {
        return String.format("\tpublic %s(%s portal) {\n" +
                        "\t\tsuper(portal, \"%s\");\n" +
                        "\t\tinitialize();\n" +
                        "\t}\n",
                pageName,
                targetPortal.getSiteName(),
                pageUrl
        );
    }

    private static String getPanelConstructor(String panelName) {
        return String.format("\tpublic %s(WebDriver webDriver) {\n" +
                        "\t\tthis.webDriver = webDriver;\n" +
                        "\t\tinitialize();\n" +
                        "\t}\n",
                panelName
        );
    }

    private static String getListConstructor(String listName) {
        String rowLocatorPattern = listName.startsWith(ControlType.LIST.getType()) ? ".//li" : ".//tbody/tr";
        String constructor = String.format("\tpublic %s(Locator locator) {\n"
                        + "\t\tsuper(locator);\n"
                        + "\t\tthis.hasHeader = false;\n"
                        + "\t\tthis.rowLocatorPattern = \"%s\";\n"
                        + "\t}\n",
                listName, rowLocatorPattern
        );
        return constructor;
    }

    private static List<String> getPageMemberInitializations(List<WebElement> elements, Map<String, List<String>> comboBoxes, List<String> imports) {
        List<String> memberInitializations = new ArrayList<>();
        List<String> processedElements = new ArrayList<>();

        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");
            if (processedElements.contains(testId)) {
                continue;
            } else {
                processedElements.add(testId);
            }
            ControlType type = getType(testId);
            String name = getName(testId);
            if (type != ControlType.COMBOBOX) {
                String content = "";
                if (!List.of(ControlType.TABLE, ControlType.LIST, ControlType.PANEL).contains(type)) {
                    content = String.format("this.%s = new %s(site.webDriver, ExtendedBy.testId(\"%s\"))", name, type.getType(),
                            testId);
                } else if (type == ControlType.TABLE) {
                    String tableTypeName = getUpperCaseInitial(name);
                    content = String.format("this.%s = new %s(new Locator(site.webDriver, ExtendedBy.testId(\"%s\")))", name, tableTypeName,
                            testId);
                } else if (type == ControlType.LIST) {
                    String listTypeName = getUpperCaseInitial(name);
                    content = String.format("this.%s = new %s(new Locator(site.webDriver, ExtendedBy.testId(\"%s\")))", name, listTypeName,
                            testId);
                } else if (type == ControlType.PANEL) {
                    String panelTypeName = getUpperCaseInitial(name);
                    content = String.format("this.%s = new %s(site.webDriver)", name, panelTypeName);
                }
                memberInitializations.add(content);
            }
        }

        for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
            String content;
            if (entry.getValue().size() == 1) {
                content = String.format("this.%s = new SelectComboBox(site.webDriver, ExtendedBy.testId(\"%s\"))",
                        entry.getKey(),
                        getFromList(entry.getValue(), "button"));

                updateImportMap(imports, ControlType.SELECT_COMBOBOX);
            } else {
                content = String.format("this.%s = new CustomComboBox(\n"
                                + "\t\t\tpage.getByTestId(\"%s\"),\n"
                                + "\t\t\tpage.getByTestId(\"%s\"),\n"
                                + "\t\t\tpage.getByTestId(\"%s\")\n"
                                + "\t\t)",
                        entry.getKey(),
                        getFromList(entry.getValue(), "button"),
                        getFromList(entry.getValue(), "text"),
                        getFromList(entry.getValue(), "list"));

                updateImportMap(imports, ControlType.CUSTOM_COMBOBOX);
            }
            memberInitializations.add(content);
        }
        return memberInitializations;
    }

    private static List<String> getPanelMemberInitializations(List<WebElement> elements, Map<String, List<String>> comboBoxes, List<String> imports) {
        List<String> memberInitializations = new ArrayList<>();
        List<String> processedElements = new ArrayList<>();

        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");
            if (processedElements.contains(testId)) {
                continue;
            } else {
                processedElements.add(testId);
            }
            ControlType type = getType(testId);
            String name = getName(testId);
            if (type != ControlType.COMBOBOX) {
                String content = "";
                if (!List.of(ControlType.TABLE, ControlType.LIST, ControlType.PANEL).contains(type)) {
                    content = String.format("this.%s = new %s(webDriver, ExtendedBy.testId(\"%s\"))", name, type.getType(),
                            testId);
                } else if (type == ControlType.TABLE) {
                    String tableTypeName = getUpperCaseInitial(name);
                    content = String.format("this.%s = new %s(new Locator(webDriver, ExtendedBy.testId(\"%s\")))", name, tableTypeName,
                            testId);
                } else if (type == ControlType.LIST) {
                    String listTypeName = getUpperCaseInitial(name);
                    content = String.format("this.%s = new %s(new Locator(webDriver, ExtendedBy.testId(\"%s\")))", name, listTypeName,
                            testId);
                } else if (type == ControlType.PANEL) {
                    String panelTypeName = getUpperCaseInitial(name);
                    content = String.format("this.%s = new %s(webDriver)", name, panelTypeName);
                }
                memberInitializations.add(content);
            }
        }

        for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
            String content;
            if (entry.getValue().size() == 1) {
                content = String.format("this.%s = new SelectComboBox(webDriver, ExtendedBy.testId(\"%s\"))",
                        entry.getKey(),
                        getFromList(entry.getValue(), "button"));

                updateImportMap(imports, ControlType.SELECT_COMBOBOX);

            } else {
                content = String.format("this.%s = new CustomComboBox(\n"
                                + "\t\t\tpage.getByTestId(\"%s\"),\n"
                                + "\t\t\tpage.getByTestId(\"%s\"),\n"
                                + "\t\t\tpage.getByTestId(\"%s\")\n"
                                + "\t\t)",
                        entry.getKey(),
                        getFromList(entry.getValue(), "button"),
                        getFromList(entry.getValue(), "text"),
                        getFromList(entry.getValue(), "list"));

                updateImportMap(imports, ControlType.CUSTOM_COMBOBOX);
            }
            memberInitializations.add(content);
        }
        return memberInitializations;
    }

    private static List<String> getListMemberInitializations(List<WebElement> elements, Map<String, List<String>> comboBoxes,
                                    List<String> imports) {

        List<String> memberInitializations = new ArrayList<>();
        List<String> processedElements = new ArrayList<>();

        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");
            if (processedElements.contains(testId)) {
                continue;
            } else {
                processedElements.add(testId);
            }
            ControlType type = getType(testId);
            String name = getName(testId);

            if (type != ControlType.COMBOBOX) {
                String content;
                if (!List.of(ControlType.TABLE, ControlType.LIST, ControlType.PANEL, ControlType.COMBOBOX).contains(type)) {
                    content = getStandardRepeatingControl(name, testId, type);
                    memberInitializations.add(content);
                } else if (type == ControlType.TABLE) {
                    log.logAssert(false, "Embedded tables not supported.");
                } else if (type == ControlType.LIST) {
                    log.logAssert(false, "Embedded lists not supported.");
                } else if (type == ControlType.PANEL) {
                    log.logAssert(false, "Embedded panels not supported.");
                }
            }
        }

        for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
            String content;
            if (entry.getValue().size() == 1) {
                content = getStandardRepeatingControl(entry.getKey(), getFromList(entry.getValue(), "button"),
                        ControlType.SELECT_COMBOBOX);
                memberInitializations.add(content);

                updateImportMap(imports, ControlType.SELECT_COMBOBOX);
            } else {
                content = String.format("this.%s = new CustomComboBox(\n"
                                + "\t\t\tpage.getByTestId(\"%s\"),\n"
                                + "\t\t\tpage.getByTestId(\"%s\"),\n"
                                + "\t\t\tpage.getByTestId(\"%s\")\n"
                                + "\t\t)",
                        entry.getKey(),
                        getFromList(entry.getValue(), "button"),
                        getFromList(entry.getValue(), "text"),
                        getFromList(entry.getValue(), "list"));

                updateImportMap(imports, ControlType.CUSTOM_COMBOBOX);
                memberInitializations.add(content);
            }
        }
        return memberInitializations;
    }

    private static String getStandardRepeatingControl(String name, String testId, ControlType type) {
        return String.format("this.%s = new RepeatingControl<>(\n" +
                        "\t\t\tlocator,\n" +
                        "\t\t\t\"%s\",\n" +
                        "\t\t\tLocatorMethod.DATA_TEST_ID,\n" +
                        "\t\t\t%s::new,\n" +
                        "\t\t\trowLocatorPattern,\n" +
                        "\t\t\thasHeader\n" +
                        "\t\t)",
                name, testId, type.getType());
    }

    private static String getCustomRepeatingControl(String name, String testId, String id2, String id3, String type) {
        return String.format("this.%s = new RepeatingControl<>(\n" +
                        "\t\t\tlocator,\n" +
                        "\t\t\t\"%s\",\n" +
                        "\t\t\t\"%s\",\n" +
                        "\t\t\t\"%s\",\n" +
                        "\t\t\tLocatorMethod.DATA_TEST_ID,\n" +
                        "\t\t\t%s::new,\n" +
                        "\t\t\trowLocatorPattern,\n" +
                        "\t\t\thasHeader\n" +
                        "\t\t)",
                name, testId, id2, id3, type);
    }

    private static List<String> getComboBoxClassMemberDeclarations(Map<String, List<String>> comboBoxes) {
        List<String> declarations = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
            declarations.add(String.format("public ComboBox %s", entry.getKey()));
        }
        return declarations;
    }

    private static List<String> getListComboBoxClassMemberDeclarations(Map<String, List<String>> comboBoxes) {
        List<String> declarations = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
            declarations.add(String.format("public RepeatingControl<ComboBox> %s", entry.getKey()));
        }
        return declarations;
    }

    private static List<String> getClassMemberDeclarationsForElements(List<WebElement> elements, List<String> processedElements,
                                            List<String> imports, Map<String, List<String>> comboBoxes) {

        List<String> declarations = new ArrayList<>();
        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");
            if (processedElements.contains(testId)) {
                continue;
            } else {
                processedElements.add(testId);
            }
            ControlType type = getType(testId);

            updateImportMap(imports, type);

            String name = getName(testId);
            if (type == ControlType.COMBOBOX) {
                if (comboBoxes.containsKey(name)) {
                    comboBoxes.get(name).add(testId);
                } else {
                    comboBoxes.put(name, Arrays.asList(testId));
                }
            } else {
                String content = "";
                if (type == ControlType.TABLE) {
                    String tableTypeName = name.substring(0, 1).toUpperCase() + name.substring(1);
                    content += String.format("public %s %s", tableTypeName, name);
                } else if (type == ControlType.PANEL) {
                    String panelTypeName = name.substring(0, 1).toUpperCase() + name.substring(1);
                    content += String.format("public %s %s", panelTypeName, name);
                } else if (type == ControlType.LIST) {
                    String listTypeName = name.substring(0, 1).toUpperCase() + name.substring(1);
                    content += String.format("public %s %s", listTypeName, name);
                } else {
                    content += String.format("public %s %s", type.getType(), name);
                }
                declarations.add(content);
            }
        }
        return declarations;
    }

    private static List<String> getListClassMemberDeclarationForElement(
            List<WebElement> elements, List<String> processedElements, List<String> imports, Map<String, List<String>> comboBoxes
        ) {

        List<String> declarations = new ArrayList<>();

        for (WebElement element : elements) {
            String testId = element.getDomAttribute("data-testid");
            if (processedElements.contains(testId)) {
                continue;
            } else {
                processedElements.add(testId);
            }
            ControlType type = getType(testId);

            log.logAssert(
                    importMap.containsKey(type) && !List.of(ControlType.TABLE, ControlType.PANEL, ControlType.LIST).contains(type),
                    "Unsupported type: " + type
            );
            updateImportMap(imports, type);

            String name = getName(testId);
            if (type == ControlType.COMBOBOX) {
                if (comboBoxes.containsKey(name)) {
                    comboBoxes.get(name).add(testId);
                } else {
                    comboBoxes.put(name, Arrays.asList(testId));
                }
            } else if (type == ControlType.TABLE) {
                log.logAssert(false, "Embedded tables not supported.");
            } else if (type == ControlType.PANEL) {
                log.logAssert(false, "Embedded panels not supported.");
            } else if (type == ControlType.LIST) {
                log.logAssert(false, "Embedded lists not supported.");
            } else {
                declarations.add(String.format("public RepeatingControl<%s> %s", type.getType(), name));
            }
        }
        return declarations;
    }

    private static Map<ElementType, List<WebElement>> getAllTaggedElementsForPage(String pageUrl, TargetPortal targetPortal) {

        Map<ElementType, List<WebElement>> elementMap = new HashMap<>();
        WebDriver page = getTargetPage(targetPortal, pageUrl);

        scanElementsForTarget(elementMap, page);

        while (scanAgain()) {
            scanElementsForTarget(elementMap, page);
        }
        return elementMap;
    }

    private static WebDriver getTargetPage(TargetPortal targetPortal, String pageUrl) {
        String path = String.format(sitePathTemplate, targetPortal.getPackageName());
        Class<?> siteClass = ObjectHelper.getClass(path, targetPortal.getSiteName());
        Site portal = (Site) ObjectHelper.getInstance(siteClass);
        loginIfNeeded(siteClass, portal);
        WebDriver page = portal.webDriver;

        String url = String.format("%s%s", portal.baseUrl, pageUrl);
        page.get(url);
        SystemHelper.sleep(1000);

        return page;
    }

    private static void loginIfNeeded(Class<?> cls, Site portal) {
        Method loginPageMethod = ObjectHelper.getMethod(cls, "loginPage");
        if (loginPageMethod != null) {
            Object loginPage = ObjectHelper.invokeMethod(portal, loginPageMethod);
            if (loginPage != null) {
                Method signInMethod = ObjectHelper.getMethod(loginPage.getClass(), "signIn");
                if (signInMethod != null) {
                    ObjectHelper.invokeMethod(loginPage, signInMethod);
                }
            }
        }
    }

    private static void scanElementsForTarget(Map<ElementType, List<WebElement>> elementMap, WebDriver webDriver) {
        List<WebElement> elements;
        elements = webDriver.findElements(By.xpath(
                "//*[not(ancestor::*[starts-with(@data-testid, 'Panel-')]) "
                        + "and not(ancestor::*[starts-with(@data-testid, 'List-')])"
                        + "and not(ancestor::tbody) "
                        + "and @data-testid]"));
        elementMap.put(ElementType.GENERAL, elements);

        elements = webDriver.findElements(By.xpath(
                "//*[not(ancestor::*[starts-with(@data-testid, 'Panel-')]) and ancestor::table and @data-testid] " +
                        "| //*[starts-with(@data-testid, 'Table-')]"));
        elementMap.put(ElementType.GENERAL_TABLE_ROW, elements);

        elements = webDriver.findElements(By.xpath(
                "//*[not(ancestor::*[starts-with(@data-testid, 'Panel-')]) "
                        + "and (ancestor::*[starts-with(@data-testid, 'List-')] or //*[starts-with(@data-testid, 'List-')]) "
                        + "and @data-testid]"));
        elementMap.put(ElementType.GENERAL_LIST, elements);

        elements = webDriver.findElements(By.xpath(
                "//*[ancestor::*[starts-with(@data-testid, 'Panel-')] "
                        + "and not(ancestor::tbody) "
                        + "and @data-testid]"));
        elementMap.put(ElementType.PANEL, elements);

        elements = webDriver.findElements(By.xpath(
                "//*[ancestor::*[starts-with(@data-testid, 'Panel-')] "
                        + "and ancestor::tbody "
                        + "and @data-testid]"));
        elementMap.put(ElementType.PANEL_TABLE_ROW, elements);

        elements = webDriver.findElements(By.xpath(
                "//*[ancestor::*[starts-with(@data-testid, 'Panel-')] "
                        + "and (ancestor::*[starts-with(@data-testid, 'List-')] or //*[starts-with(@data-testid, 'List-')]) "
                        + "and @data-testid]"));
        elementMap.put(ElementType.PANEL_LIST, elements);
    }

    private static List<WebElement> getElementsForPanel(WebElement elementHandle) {
        return elementHandle.findElements(By.xpath(".//*[not(ancestor::tbody) "
                + "and not(ancestor::*[starts-with(@data-testid, 'List-')]) "
                + "and @data-testid]"));
    }

    private static List<WebElement> getElementsForList(WebElement elementHandle, List<WebElement> listElements) {

        WebElement parentList = listElements.stream().filter(item ->
                item.getDomAttribute("data-testid").equals(elementHandle.getDomAttribute("data-testid")))
                .findFirst()
                .orElse(null);

        if (parentList != null) {
            return parentList.findElements(By.xpath(".//*[@data-testid]"));
        }
        return null;
    }

    private static String getPackageName(TargetPortal targetPortal, String targetPage) {
        return String.format(sitePagePathTemplate, targetPortal.getPackageName(), targetPage.toLowerCase());
    }

    private static String getPackage(TargetPortal targetPortal, String targetPage) {
        String packageName = String.format(sitePagePathTemplate, targetPortal.getPackageName(), targetPage.toLowerCase());
        return String.format("package %s;\n\n", packageName);
    }

    private static String getClassAnnotation() {
        return "@SuppressWarnings({\"checkstyle:AbbreviationAsWordInName\", \"checkstyle:MemberName\", " + "\"checkstyle:LineLength\"})";
    }

    private static String getPageClass(TargetPortal targetPortal, String pageName) {
        return String.format("abstract class %s<T> extends Base%sPage<T>", pageName, targetPortal.getBaseName());
    }

    private static String getPanelClass(String pageName) {
        return String.format("abstract class %s extends PanelControl", pageName);
    }

    private static String getListClass(String pageName) {
        return String.format("abstract class %s extends ListControl", pageName);
    }

    private static List<String> getCoreImports(TargetPortal targetPortal) {
        String basePage = String.format("Base%sPage", targetPortal.getBaseName());
        String basePageImport = String.format(sitePagePathTemplate, targetPortal.getPackageName(), basePage);
        String siteImport = String.format(sitePathTemplate, targetPortal.getPackageName())
                + "." + targetPortal.getSiteName();

        return new ArrayList<>(
                List.of(
                        String.format("import %s", basePageImport),
                        String.format("import %s", siteImport),
                        "import ui.core.ExtendedBy"
                )
        );
    }

    private static List<String> getCorePanelImports() {
        return new ArrayList<>(
                List.of(
                    "import org.openqa.selenium.WebDriver",
                    "import ui.core.ExtendedBy",
                    "import ui.core.controls.PanelControl"
                )
        );
    }

    private static List<String> getCoreListImports() {
        String listControlImport = coreControlsPath + "ListControl";
        return new ArrayList<>(
                List.of(
                        String.format("import %s", listControlImport),
                        "import ui.core.Locator",
                        "import ui.core.controls.RepeatingControl",
                        "import ui.core.enums.LocatorMethod"
                )
        );
    }

    /*
    In order for this to work, need to add the following to IntelliJ, Help->Edit custom VM options and restart.
    -Deditable.java.test.console=true
     */
    private static boolean scanAgain() {
        System.out.println("\n\nScan again for more controls?\nIf yes, expose new controls on page now and then enter 'y'."
                + "\nOtherwise, press ENTER now.\n");
        Scanner in = new Scanner(System.in);
        String userChoice = in.nextLine();
        return userChoice != null && userChoice.equalsIgnoreCase("y");
    }

    private static String getFromList(List<String> testIds, String filter) {
        return testIds.stream().filter(item -> item.endsWith(filter)).findFirst().orElse(null);
    }

    private static ControlType getType(String attribute) {
        String type = attribute.split("-")[0];
        if (type.equals("CheckBox") || type.equals("RadioButton")) {
            type = "FlagControl";
        }
        return ControlType.getForType(type);
    }

    private static String getName(String attribute) {
        log.logAssert(attribute.contains("-"), "Bad data-testid: " + attribute);
        String[] items = attribute.split("-");
        String name = items[0] + items[1];
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private static String getUpperCaseInitial(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private static void updateImportMap(List<String> imports, ControlType controlType) {
        if (controlType == ControlType.PANEL) return;

        log.logAssert(importMap.containsKey(controlType), "Unsupported type: " + controlType);

        if (!StringHelper.containsString(importMap.get(controlType), imports)) {
            imports.add(importMap.get(controlType));
        }
    }

}
