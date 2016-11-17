package essentials;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SimpleLog {

	static File file;
	static boolean timestamp;
	SimpleDateFormat dateFormat;
	boolean dummy = false;

	/**
	 * Constructor of 'SimpleLog' class, which creates the log file
	 * 
	 * @param file
	 *            The <code>File</code> where the Log should be saved to
	 * @param useSameFile
	 *            If false, there will be a new file for every launch
	 * @param useTimestamp
	 *            If true, there will be a timestamp in front of every entry
	 */
	public SimpleLog(File file, boolean useSameFile, boolean useTimestamp) {

		dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		SimpleLog.timestamp = useTimestamp;

		if (!useSameFile)
			SimpleLog.file = new File(file.getPath() + "_" + dateFormat.format(time) + ".txt");
		else
			SimpleLog.file = file;

		if (!file.exists())

			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Couldn't log to file.");
				e.printStackTrace();
			}
	}

	/**
	 * Will create a dummy-log that does not log anything
	 */
	public SimpleLog() {
		dummy = true;
	}

	/**
	 * Add a new entry to the logfile
	 * 
	 * @param text
	 *            The String, that will be written into the log file
	 * @return false, if an IOException has occurred
	 */
	public boolean log(String text) {
		
		if (dummy) {
			System.out.println(text);
			return true;
		}
		
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			if (timestamp) {
				out.append((CharSequence) dateFormat.format(time) + " ");
				System.out.print(dateFormat.format(time) + " ");
			}

			out.append(text + "\n");
			out.close();
			System.out.println(text);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Add a new debug entry to the logfile
	 * 
	 * @param text
	 *            The String, that will be written into the log file
	 * @return false, if an IOException has occurred
	 */
	public boolean debug(String text) {
		
		text = "DEBUG: " + text;
		
		if (dummy) {
			System.out.println(text);
			return true;
		}

		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			if (timestamp) {
				out.append((CharSequence) dateFormat.format(time) + " ");
				System.out.print(dateFormat.format(time) + " ");
			}

			out.append(text + "\n");
			out.close();
			System.out.println(text);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Add a new info entry to the logfile
	 * 
	 * @param text
	 *            The String, that will be written into the log file
	 * @return false, if an IOException has occurred
	 */
	public boolean info(String text) {
		
		text = "INFO: " + text;
		
		if (dummy) {
			System.out.println(text);
			return true;
		}

		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			if (timestamp) {
				out.append((CharSequence) dateFormat.format(time) + " ");
				System.out.print(dateFormat.format(time) + " ");
			}

			out.append(text + "\n");
			out.close();
			System.out.println(text);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Add a new warning entry to the logfile
	 * 
	 * @param text
	 *            The String, that will be written into the log file
	 * @return false, if an IOException has occurred
	 */
	public boolean warning(String text) {
		
		text = "WARNING: " + text;
		
		if (dummy) {
			System.out.println(text);
			return true;
		}

		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			if (timestamp) {
				out.append((CharSequence) dateFormat.format(time) + " ");
				System.out.print(dateFormat.format(time) + " ");
			}

			out.append(text + "\n");
			out.close();
			System.out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Add a new StackTrace to the logfile
	 * 
	 * @param e
	 *            The Exception, whose StackTrace should be logged
	 * @return false, if an IOException has occurred
	 */
	public boolean logStackTrace(Exception e) {
		
		if (dummy) {
			e.printStackTrace();
			return true;
		}
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			if (timestamp) {
				out.append((CharSequence) dateFormat.format(time) + " ");
				System.out.print(dateFormat.format(time) + "  ");
			}
			
			String s = sw.toString();
			out.append(s + "\n");
			out.close();
			System.err.println(s);
		} catch (IOException f) {
			f.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Add a new error entry to the logfile
	 * 
	 * @param text
	 *            The String, that will be written into the log file
	 * @return false, if an IOException has occurred
	 */
	public boolean error(String text) {

		text = "ERROR: " + text;
		
		if (dummy) {
			System.out.println(text);
			return true;
		}
		
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			if (timestamp) {
				out.append((CharSequence) dateFormat.format(time) + " ");
				System.out.print(dateFormat.format(time) + " ");
			}

			out.append(text + "\n");
			out.close();
			System.out.println(text);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Add a new fatal error entry to the logfile
	 * 
	 * @param text
	 *            The String, that will be written into the log file
	 * @return false, if an IOException has occurred
	 */
	public boolean fatal(String text) {

		text = "FATAL ERROR: " + text;
		
		if (dummy) {
			System.out.println(text);
			return true;
		}
		
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			if (timestamp) {
				out.append((CharSequence) dateFormat.format(time) + " ");
				System.out.print(dateFormat.format(time) + " ");
			}

			out.append(text + "\n");
			out.close();
			System.out.println(text);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Print a startup message to the file. The timestamp will be centered in the box
	 * 
	 * @param text
	 *            The startup message
	 * @return success
	 */
	public boolean startupMessage(String text) {
		
		if (dummy) {
			System.out.println(text);
			return true;
		}
		
		try {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			FileWriter out = new FileWriter(file, true);
			
			for (int i = 0; i < text.length(); i++) {
				out.append("=");
				System.out.print("=");
			}
			
			out.append("\n" + text + "\n");
			System.out.println("\n" + text);
			
			if (timestamp) {
				String text2 = "";

				for (int i = 0; i < (text.length() - (((CharSequence) dateFormat.format(time)).length())); i += 2)
					text2 += " ";

				text2 += (CharSequence) dateFormat.format(time);
				out.append(text2 + "\n");
				System.out.println(text2);
			}
			
			for (int i = 0; i < text.length(); i++) {
				out.append("=");
				System.out.print("=");
			}
			
			out.append("\n");
			System.out.println("\n");
			out.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
