package essentials;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Essentials {

	/**
	 * Assembles a String[] to one String, in which the parts are separated by
	 * blanks
	 * 
	 * @param array
	 *            The String array that will be assembled
	 * @return The assembled String
	 */
	public static String getAssembledStringArray(String[] array) {

		String string = "";

		for (String part : array) {
			string = string + part + " ";
		}

		return string.substring(0, string.length() - 1);
	}

	/**
	 * Checks if a String is included in a String[]
	 * 
	 * @param array
	 *            String array
	 * @param string
	 *            String
	 * @return true if included, false if not
	 */
	public static boolean isIncluded(String[] array, String string) {

		for (String s : array) {
			if (s.equals(string))
				return true;
		}

		return false;
	}

	/**
	 * Writes a String (with a timestamp) to a File.
	 * 
	 * @deprecated {@link SimpleLog} handles this much more comfortably and
	 *             professionally
	 * @param text
	 *            The String, that will be written to the file
	 * @param file
	 *            The File where the text will be saved to
	 * @param printTimestamp
	 *            If true, there will be a timestamp in front of the text
	 * @return boolean false if exception occurred
	 */
	public static boolean log(String text, File file, boolean printTimestamp) {

		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			if (!file.exists())
				file.createNewFile();

			FileWriter fileWriter = new FileWriter(file, true);

			if (printTimestamp)
				fileWriter.append((CharSequence) simpleDateFormat.format(timestamp) + " " + text + "\n");
			else
				fileWriter.append(text + "\n");

			fileWriter.close();
			System.out.println(simpleDateFormat.format(timestamp) + " " + text);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}