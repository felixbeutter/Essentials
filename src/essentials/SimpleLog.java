package essentials;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SimpleLog {

	private File file;
	private boolean useTimestamp = true;
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");

	boolean dummy = false;

	/**
	 * Constructor of 'SimpleLog' class, which creates the log file.
	 * 
	 * @param file
	 *            The File where the log will be saved to
	 * @param useTimestamp
	 *            If true, there will be a timestamp in front of every entry
	 * @throws IOException
	 *             if file does not exists and can not be created
	 */
	public SimpleLog(File file, boolean useTimestamp) throws IOException {

		this.file = file;
		this.useTimestamp = useTimestamp;

		if (!file.exists())
			file.createNewFile();
	}

	/**
	 * Will create a dummy-log that does not log anything but writes to console
	 */
	public SimpleLog() {
		dummy = true;
	}

	/**
	 * Add a new entry to the log file.
	 *
	 * @param string
	 *            This String will be written to the log file
	 * @throws IOException
	 *             if could not write to file
	 */
	public void log(String string) throws IOException {

		if (useTimestamp) {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			string = (CharSequence) DATE_FORMAT.format(timestamp) + " " + string;
		}

		if (!dummy)
			FileUtils.writeToFile(file, string + "\n");

		System.out.println(string);
	}

	/**
	 * Add a new debug entry to the log file.
	 * 
	 * @param string
	 *            This String will be written to the log file
	 * @throws IOException
	 *             if could not write to file
	 */
	public void debug(String string) throws IOException {
		log("DEBUG: " + string);
	}

	/**
	 * Add a new info entry to the log file.
	 * 
	 * @param string
	 *            This String will be written to the log file
	 * @throws IOException
	 *             if could not write to file
	 */
	public void info(String string) throws IOException {
		log("INFO: " + string);
	}

	/**
	 * Add a new warning entry to the log file.
	 * 
	 * @param string
	 *            This String will be written to the log file
	 * @throws IOException
	 *             if could not write to file
	 */
	public void warning(String string) throws IOException {
		log("WARNING: " + string);
	}

	/**
	 * Add a new error entry to the log file.
	 * 
	 * @param string
	 *            This String will be written to the log file
	 * @throws IOException
	 *             if could not write to file
	 */
	public void error(String string) throws IOException {
		log("ERROR: " + string);
	}

	/**
	 * Add a new fatal error entry to the log file.
	 * 
	 * @param string
	 *            This String will be written to the log file
	 * @throws IOException
	 *             if could not write to file
	 */
	public void fatal(String string) throws IOException {
		log("FATAL ERROR: " + string);
	}

	/**
	 * Add a new StackTrace to the log file.
	 * 
	 * @param exception
	 *            The Exception, whose StackTrace should be logged
	 * @throws IOException
	 *             if could not write to file
	 */
	public void logStackTrace(Exception exception) throws IOException {

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		exception.printStackTrace(printWriter);
		String string = stringWriter.toString();

		if (useTimestamp) {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			string = (CharSequence) DATE_FORMAT.format(timestamp) + " " + string;
		}

		if (!dummy)
			FileUtils.writeToFile(file, string + "\n");

		exception.printStackTrace();
	}

	/**
	 * Print a startup message to the log file.
	 * 
	 * @param string
	 *            The startup message
	 * @throws IOException
	 *             if could not write to file
	 */
	public void startUpMessage(String string) throws IOException {

		if (useTimestamp) {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			string = (CharSequence) DATE_FORMAT.format(timestamp) + " " + string;
		}
		
		String line = "";
		for (int i = 0; i < string.length(); i++) 
			line = line + "=";
		
		string = line + "\n" + string + "\n" + line;
		
		if (!dummy)
			FileUtils.writeToFile(file, string + "\n");

		System.out.println(string);
	}
}
