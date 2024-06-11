package utilities;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RandomData {
	public static Faker en = new Faker();
	public static Faker jp = new Faker(new Locale("ja"));

	public static String getRandomString(int length) {
		return en.regexify(String.format("[a-zA-Z0-9]{%d}", length));
	}

	public static String getRandomStringInJapanese(int length) {
		StringBuilder output = new StringBuilder();
		while (output.length() < length) {
			output.append(RandomData.jp.lorem().word()).append(" ");
		}
		return output.toString().trim().substring(0, length - 1);
	}

	public static int getRandomInt(int min, int max) {
		if (min == max) {
			return max;
		}
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min.");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static int getRandomInt(int max) {
		return (int) Math.floor((Math.random() * max));
	}

	public static String getRandomIntString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(Character.valueOf((char) ('0' + getRandomInt(10))));
		}
		return sb.toString();
	}

	public static String getRandomPastDateTime(int daysRange) {
		Date fromDate = Date.from(Instant.now().minus(daysRange, ChronoUnit.DAYS));
		Date toDate = Date.from(Instant.now());
		return RandomData.en.date().between(fromDate, toDate).toInstant()
				.truncatedTo(ChronoUnit.MINUTES).toString();
	}

	public static List<String> getListOfRandomString(int listSize, int stringSize) {
		List<String> stringList = new ArrayList<>();
		for (int i = 0; i < listSize; i++) {
			stringList.add(getRandomString(stringSize));
		}
		return stringList;
	}

	public static <T> T getRandomItemFromList(T[] items) {
		return getRandomItemFromList(List.of(items));
	}

	public static <T> T getRandomItemFromList(List<T> items) {
		assert items != null : "Failed to get random item.  List is null.";
		return items.get(RandomData.getRandomInt(items.size()));
	}

	public static String getRandomPassword() {
		String upperCaseLetters = RandomStringUtils.random(3, 65, 90, true, true);
		String lowerCaseLetters = RandomStringUtils.random(3, 97, 122, true, true);
		String numbers = RandomStringUtils.randomNumeric(4);
		String specialChar = RandomStringUtils.random(2, 58, 64, false, false);
		return upperCaseLetters
				.concat(lowerCaseLetters)
				.concat(numbers)
				.concat(specialChar);
	}

}
