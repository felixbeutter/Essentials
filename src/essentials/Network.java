package essentials;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
import java.util.regex.Pattern;

public class Network {

	/**
	 * Downloads a File from an URL and saves it.
	 * 
	 * @param url
	 *            The URL of the File
	 * @param file
	 *            The File
	 * @throws IOException
	 *             if something went wrong
	 */
	public static void downloadFileFromURL(URL url, File file) throws IOException {

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();

		if (!file.exists())
			file.createNewFile();

		BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
		byte[] bytes = new byte[256];
		int i = 0;

		while ((i = inputStream.read(bytes)) >= 0)
			outputStream.write(bytes, 0, i);

		outputStream.flush();
		outputStream.close();
	}

	/**
	 * Returns the IP address of the network interface currently in use.
	 * 
	 * @return IP address
	 */
	public static String getIP() {

		try {

			InetAddress ip = InetAddress.getLocalHost();
			return ip.getHostAddress();

		} catch (UnknownHostException e) {
			return null;
		}
	}

	/**
	 * Returns the MAC address of the network interface currently in use.
	 * 
	 * @return MAC address
	 */
	public static String getMAC() {

		try {

			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < mac.length; i++) {
				builder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}

			return builder.toString();
		} catch (UnknownHostException | SocketException e) {
			return null;
		}
	}

	/**
	 * Returns all IPs on the local network using the arp -a command. Works only on
	 * Windows machines.
	 * 
	 * @param includeLocalhost
	 *            If true, the first entry will be 'localhost'
	 * @return A String[] containing the IPs
	 * @throws IOException
	 *             if command can not be executed
	 */
	public static String[] getIPs(boolean includeLocalhost) throws IOException {

		if (includeLocalhost) {

			String[] ips = getIPs(false);
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
	 * Send HTTP requests to a webserver and fetch the answer.
	 * 
	 * @param url
	 *            The URL you want to send a request to
	 * @return The answer from that URL
	 * @throws IOException
	 *             if connection failed
	 */
	public static String sendHTTPRequest(URL url) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String answer = "";
		String line = "";

		while (null != (line = reader.readLine())) {
			answer = answer + line + "\n";
		}

		reader.close();
		return answer;
	}
}