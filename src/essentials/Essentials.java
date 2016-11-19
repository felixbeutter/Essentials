package essentials;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Essentials {

	/**
	 * Adds a Component to a Container using the {@link GridBagLayout}.
	 * 
	 * @param container
	 *            Container to which the Component will be added
	 * @param layout
	 *            Used GridBagLayout object
	 * @param component
	 *            Component which will be added to the Container
	 * @param x
	 *            x coordinate of the Component in the grid
	 * @param y
	 *            y coordinate of the Component in the grid
	 * @param width
	 *            width of the Component
	 * @param height
	 *            height of the Component
	 * @param weightx
	 *            x weight of the Component
	 * @param weighty
	 *            y weight of the component
	 * @param insets
	 *            Insets which defines the distances around the Component
	 * @return boolean false if exception occurred
	 */
	public static boolean addComponent(Container container, GridBagLayout layout, Component component, int x, int y,
			int width, int height, double weightx, double weighty, Insets insets) {

		if (insets == null)
			insets = new Insets(0, 0, 0, 0);

		try {
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = x;
			constraints.gridy = y;
			constraints.gridwidth = width;
			constraints.gridheight = height;
			constraints.weightx = weightx;
			constraints.weighty = weighty;
			constraints.insets = insets;
			layout.setConstraints(component, constraints);
			container.add(component);
		} catch (Exception e) {
			return false;
		}
		return true;
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

		// TODO Not working properly! (Check for bugs)
		//
		// InputStream is = new BufferedInputStream(new FileInputStream(file));
		//
		// try {
		// byte[] c = new byte[1024];
		// int count = 0;
		// int readChars = 0;
		// boolean empty = true;
		//
		// while ((readChars = is.read(c)) != -1) {
		// empty = false;
		//
		// for (int i = 0; i < readChars; ++i) {
		//
		// if (c[i] == '\n')
		// ++count;
		// }
		// }
		//
		// return (count == 0 && !empty) ? 1 : count;
		// } finally {
		// is.close();
		// }
	}

	/**
	 * Send HTTP requests to a webserver and fetch the answer
	 * 
	 * @param url
	 *            The URL you want to send a request to
	 * @return The answer from that URL
	 * @throws IOException
	 *             if connection failed
	 */
	public static String sendHTTPRequest(URL url) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		String answer = "";
		String line = "";

		while (null != (line = br.readLine())) {
			answer = answer + line + "\n";
		}

		br.close();
		return answer;
	}

	/**
	 * Downloads a File from an URL and saves it to the computer
	 * 
	 * @param url
	 *            The URL of the File
	 * @param saveFile
	 *            The path where the File should be saved
	 * @return success
	 * @throws IOException
	 *             if HTTP error code is returned
	 * @throws FileNotFoundException
	 *             if file has not be found
	 */
	public static boolean downloadFileFromURL(URL url, File saveFile) throws IOException, FileNotFoundException {

		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		c.connect();

		BufferedInputStream in = new BufferedInputStream(c.getInputStream());
		OutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
		byte[] b = new byte[256];
		int n = 0;

		while ((n = in.read(b)) >= 0) {
			out.write(b, 0, n);
		}

		out.flush();
		out.close();
		return true;
	}

	/**
	 * Copies a BufferedImage
	 * 
	 * @param image
	 *            The image that should be copied
	 * @return The copied image
	 */
	public static BufferedImage copyBufferedImage(BufferedImage image) {

		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	/**
	 * Search for all IPs on the local network using the arp -a command. Works
	 * only on Windows machines
	 * 
	 * @param includeLocalhost
	 *            If true, the first entry will be 'localhost'
	 * @return A String[] containing the IPs
	 * @throws IOException
	 *             if command can not be executed
	 */
	public static String[] searchIPs(boolean includeLocalhost) throws IOException {

		if (includeLocalhost) {
			String[] ips = searchIPs(false);
			String[] ips2 = new String[ips.length + 1];
			ips2[0] = "localhost";

			for (int i = 1; i < ips2.length; i++) {
				ips2[i] = ips[i - 1];
			}

			return ips2;
		}

		String[] line = null;
		String[] ips = null;
		String answer = "";

		try {
			Process p = Runtime.getRuntime().exec("arp -a");
			InputStream is = p.getInputStream();
			int c;

			while ((c = is.read()) != -1) {
				answer = answer + (char) c;
			}

			line = answer.split(Pattern.quote("\n"));
			int length = line.length;
			String[] line2 = new String[length];
			ips = new String[line.length - 3];

			for (int i = 3; i < line.length; i++) {
				line2[i - 2] = line[i];
				ips[i - 3] = line[i].substring(0, 17).trim();

			}
		} catch (NegativeArraySizeException e) {
			return new String[0];
		}
		return ips;
	}

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
	 * Returns the MAC address of the network interface currently in use
	 * 
	 * @return MAC address
	 */
	public static String getMac() {

		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}

			return sb.toString();
		} catch (UnknownHostException | SocketException e) {
			return null;
		}
	}

	/**
	 * Returns the IP address of the network interface currently in use
	 * 
	 * @return MAC address
	 */
	public static String getIp() {

		try {
			InetAddress ip = InetAddress.getLocalHost();
			return ip.getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}
}