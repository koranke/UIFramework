package utilities;

import java.util.concurrent.TimeUnit;

public class SystemHelper {

	public static void sleep(long milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (Exception e) {
			//do nothing
		}
	}
}
