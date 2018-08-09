package essentials;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	/**
	 * Counts the number of lines in the given File.
	 * 
	 * @param file
	 *            The File to count the lines of
	 * @param skipEmptyLines
	 *            if true, empty lines will not be counted
	 * @return The amount of lines
	 * @throws IOException
	 *             if file is not found or can't be read
	 */
	public static int countFileLines(File file, boolean skipEmptyLines) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line = reader.readLine();
		int lines = 0;

		if (skipEmptyLines) {

			while (line != null) {

				if (!line.trim().equals(""))
					lines++;
				line = reader.readLine();
			}

		} else {

			while (line != null) {

				lines++;
				line = reader.readLine();
			}
		}

		reader.close();
		return lines;
	}

	/**
	 * Writes a String to the end of a File.
	 * 
	 * @param file
	 *            The File that will be written to
	 * @param string
	 *            The String
	 * @throws IOException
	 *             if something went wrong while writing to file
	 */
	public static void writeToFile(File file, String string) throws IOException {

		if (!file.exists())
			file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.append(string);
		writer.close();
	}

	/**
	 * Writes a char[] to the end of a File.
	 * 
	 * @param file
	 *            The File that will be written to
	 * @param characters
	 *            The char array
	 * @throws IOException
	 *             if something went wrong while writing to file
	 */
	public static void writeToFile(File file, char[] characters) throws IOException {
		writeToFile(file, new String(characters));
	}

	/**
	 * Writes a byte[] to the end of a File.
	 * 
	 * @param file
	 *            The File that will be written to
	 * @param bytes
	 *            The byte array
	 * @throws IOException
	 *             if something went wrong while writing to file
	 */
	public static void writeToFile(File file, byte[] bytes) throws IOException {
		writeToFile(file, new String(bytes));
	}

	/**
	 * Reads a File and returns the text as String. Lines are separated by blanks.
	 * 
	 * @param file
	 *            The File to read
	 * @return The content of the File as String
	 * @throws IOException
	 *             if file isn't found or can't be read
	 */
	public static String readFile(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		StringBuilder builder = new StringBuilder();

		String line = reader.readLine();

		while (line != null) {

			builder.append(line + " ");
			line = reader.readLine();
		}
		reader.close();

		return builder.toString();
	}

	/**
	 * Reads a given File and returns the text as String[]. Elements are seperated
	 * by lines.
	 * 
	 * @param file
	 *            The File to read
	 * @return The content of the file as String[]
	 * @throws IOException
	 *             if file isn't found or can't be read
	 */
	public static String[] readFileToArray(File file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
		String[] array = new String[countFileLines(file, false)];

		String line = reader.readLine();
		int i = 0;

		while (line != null) {

			array[i] = line;
			line = reader.readLine();
			i++;
		}

		reader.close();
		return array;
	}
}
