package utilities;

import java.util.List;

public class StringHelper {

	public static boolean containsString(String inputStr, List<String> items) {
		return items.stream().anyMatch(inputStr::contains);
	}

}
