package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

	public static void createFile(String path, String fileName, String content) {
		try {
			String fullPath = path + fileName;
			FileWriter fw = new FileWriter(fullPath);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFileExists(String filePath, String fileName) {
		String file = String.format("%s/%s", filePath, fileName);
		return isFileExists(file);
	}

	public static boolean isFileExists(String file) {
		return Files.exists(Paths.get(file));
	}


}
