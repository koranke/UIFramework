package generator;

import utilities.Log;
import utilities.StringHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class GeneratorBase {
	protected static final Log log = Log.getInstance();
	protected static Map<ControlType, String> importMap;

	protected static String getPanelClass(String pageName) {
		return String.format("abstract class %s extends PanelControl", pageName);
	}

	protected static String getListClass(String pageName) {
		return String.format("abstract class %s extends ListControl<%s>", pageName, pageName);
	}

	protected static List<String> getClassAnnotations() {
		return List.of(
				"@SuppressWarnings({\"checkstyle:AbbreviationAsWordInName\", \"checkstyle:MemberName\", " + "\"checkstyle:LineLength\"})",
				"@Getter",
				"@Accessors(fluent = true)"
		);
	}

	protected static String getListAnnotation() {
		return "@SuppressWarnings({\"checkstyle:AbbreviationAsWordInName\", \"checkstyle:MemberName\", " + "\"checkstyle:LineLength\"})";
	}

	protected static List<String> getComboBoxClassMemberDeclarations(Map<String, List<String>> comboBoxes) {
		List<String> declarations = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
			declarations.add(String.format("private ComboBox %s", entry.getKey()));
		}
		return declarations;
	}

	protected static List<String> getListComboBoxClassMemberDeclarations(Map<String, List<String>> comboBoxes) {
		List<String> declarations = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
			declarations.add(String.format("private RepeatingControl<ComboBox> %s", entry.getKey()));
		}
		return declarations;
	}

	protected static List<String> getClassMemberDeclarationsForElements(List<AttributeElement> elements, List<String> processedElements,
																	  List<String> imports, Map<String, List<String>> comboBoxes) {

		List<String> declarations = new ArrayList<>();
		for (AttributeElement element : elements) {
			String testId = element.getAttribute("data-testid");
			log.debug("Processing element: " + testId);
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
					content = String.format("private %s %s", tableTypeName, name);
				} else if (type == ControlType.PANEL) {
					String panelTypeName = name.substring(0, 1).toUpperCase() + name.substring(1);
					content = String.format("private %s %s", panelTypeName, name);
				} else if (type == ControlType.LIST) {
					String listTypeName = name.substring(0, 1).toUpperCase() + name.substring(1);
					content = String.format("private %s %s", listTypeName, name);
				} else {
					content = String.format("private %s %s", type.getType(), name);
				}
				declarations.add(content);
			}
		}
		return declarations;
	}

	protected static List<String> getListClassMemberDeclarationForElement(
			List<AttributeElement> elements, List<String> processedElements, List<String> imports, Map<String, List<String>> comboBoxes
	) {

		List<String> declarations = new ArrayList<>();

		for (AttributeElement element : elements) {
			String testId = element.getAttribute("data-testid");
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
				declarations.add(String.format("private RepeatingControl<%s> %s", type.getType(), name));
			}
		}
		return declarations;
	}

	protected static List<String> getListMemberInitializations(List<AttributeElement> elements, Map<String, List<String>> comboBoxes,
															 List<String> imports) {

		List<String> memberInitializations = new ArrayList<>();
		List<String> processedElements = new ArrayList<>();

		for (AttributeElement element : elements) {
			String testId = element.getAttribute("data-testid");
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

	protected static String getStandardRepeatingControl(String name, String testId, ControlType type) {
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

	protected static String getCustomRepeatingControl(String name, String testId, String id2, String id3, String type) {
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


	protected static List<String> getListLabelSearchMethods(String listName, List<AttributeElement> elements) {
		List<String> memberMethods = new ArrayList<>();
		List<String> processedElements = new ArrayList<>();

		for (AttributeElement element : elements) {
			String testId = element.getAttribute("data-testid");
			if (processedElements.contains(testId)) {
				continue;
			} else {
				processedElements.add(testId);
			}
			ControlType type = getType(testId);
			String name = getName(testId);

			if (type == ControlType.LABEL) {
				memberMethods.add(getListLabelSearchMethod(listName, name));
			}

		}

		return memberMethods;
	}

	protected static List<String> getListMemberAccessorMethods(List<AttributeElement> elements, Map<String, List<String>> comboBoxes) {
		List<String> memberMethods = new ArrayList<>();
		List<String> processedElements = new ArrayList<>();

		for (AttributeElement element : elements) {
			String testId = element.getAttribute("data-testid");
			if (processedElements.contains(testId)) {
				continue;
			} else {
				processedElements.add(testId);
			}
			ControlType type = getType(testId);
			String name = getName(testId);

			if (!List.of(ControlType.TABLE, ControlType.LIST, ControlType.PANEL, ControlType.COMBOBOX).contains(type)) {
				memberMethods.add(getListMemberAccessorMethod(type.getType(), name));
			}
		}
		for (Map.Entry<String, List<String>> entry : comboBoxes.entrySet()) {
			memberMethods.add(getListMemberAccessorMethod("ComboBox", entry.getKey()));
		}

		return memberMethods;
	}

	protected static String getListLabelSearchMethod(String listName, String name) {
		return String.format(
				"\tpublic %s using%s() {\n" +
						"\t\tthis.searchLabel = %s;\n" +
						"\t\treturn this;\n" +
						"\t}"
				, listName, getUpperCaseInitial(name), name
		);
	}

	protected static String getListMemberAccessorMethod(String type, String name) {
		return String.format(
				"\tpublic %s %s() {\n" +
						"\t\treturn %s.get(currentRow);\n" +
						"\t}"
				, type, name, name
		);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/*
	In order for this to work, need to add the following to IntelliJ, Help->Edit custom VM options and restart.
	-Deditable.java.test.console=true
	 */
	 protected static boolean scanAgain() {
		System.out.println("\n\nScan again for more controls?\nIf yes, expose new controls on page now and then enter 'y'."
				+ "\nOtherwise, press ENTER now.\n");
		Scanner in = new Scanner(System.in);
		String userChoice = in.nextLine();
		return userChoice != null && userChoice.equalsIgnoreCase("y");
	}

	protected static String getFromList(List<String> testIds, String filter) {
		return testIds.stream().filter(item -> item.endsWith(filter)).findFirst().orElse(null);
	}

	protected static ControlType getType(String attribute) {
		String type = attribute.split("-")[0];
		if (type.equals("CheckBox") || type.equals("RadioButton")) {
			type = "FlagControl";
		}
		return ControlType.getForType(type);
	}

	protected static String getName(String attribute) {
		log.logAssert(attribute.contains("-"), "Bad data-testid: " + attribute);
		String[] items = attribute.split("-");
		String name = items[0] + items[1];
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	protected static String getUpperCaseInitial(String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1);
	}

	protected static void updateImportMap(List<String> imports, ControlType controlType) {
		if (controlType == ControlType.PANEL) return;

		log.logAssert(importMap.containsKey(controlType), "Unsupported type: " + controlType);

		if (importMap.get(controlType) != null && !StringHelper.containsString(importMap.get(controlType), imports)) {
			imports.add(importMap.get(controlType));
		}
	}

}
