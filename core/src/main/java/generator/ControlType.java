package generator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ControlType {
	LABEL("Label"),
	TEXTBOX("TextBox"),
	BUTTON("Button"),
	CHECKBOX("CheckBox"),
	RADIOBUTTON("RadioButton"),
	FLAGCONTROL("FlagControl"),
	COMBOBOX("ComboBox"),
	SELECT_COMBOBOX("SelectComboBox"),
	CUSTOM_COMBOBOX("CustomComboBox"),
	TABGROUP("TabGroup"),
	TABLE("Table"),
	LIST("List"),
	PANEL("Panel")
	;

	private final String type;

	public static ControlType getForType(String type) {
		return Arrays.stream(values()).filter(item -> item.getType().equals(type)).findFirst().orElse(null);
	}
}
