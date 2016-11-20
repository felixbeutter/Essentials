package essentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	/**
	 * Counts the number of lines in the given File. Empty lines will be skipped
	 * 
	 * @param file
	 *            The File to count the lines of
	 * @return amount of lines
	 * @throws IOException
	 *             if file is not found or can't be read
	 */
	public static int countFileLines(File file) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file));
		String s = br.readLine();
		int i = 0;

		while (s != null) {
			if (!s.trim().equals(""))
				i++;
			s = br.readLine();
		}

		br.close();
		return i;
	}

	/**
	 * Writes a String to the end of a File.
	 * 
	 * @param text
	 *            The String, that will be written to the File
	 * @param file
	 *            The File that should be written to
	 * @return boolean false if exception occurred
	 */
	public static boolean printStringToFile(String text, File file) {

		try {
			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.append(text);
			fileWriter.close();
			System.out.println("Wrote '" + text + "' into '" + file.getPath() + "'");
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Reads a given File and returns the text
	 * 
	 * @param file
	 *            The File to read
	 * @return The content of the file
	 * @throws IOException
	 *             if file isn't found or can't be read
	 */

	public static String readFile(File file) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
		StringBuilder sb = new StringBuilder();

		try {
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}

	/**
	 * Reads a given File and returns the text as String[]. Elements are
	 * seperated by lines
	 * 
	 * @param file
	 *            The File to read
	 * @return The content of the file as String[]
	 * @throws IOException
	 *             if file isn't found or can't be read
	 */

	public static String[] readFileToArray(File file) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
		String[] array = new String[countFileLines(file)];

		try {
			String line = br.readLine();
			int i = 0;

			while (line != null) {
				array[i] = line;
				line = br.readLine();
				i++;
			}
		} finally {
			br.close();
		}
		return array;
	}
}
