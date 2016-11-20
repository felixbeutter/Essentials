package essentials;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

	/**
	 * Returns the SHA-256 hash of a given string
	 * 
	 * @param string
	 *            the String to be hashed
	 * @return the hash of the String
	 */
	public static String hash(String string) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(string.getBytes());
			byte[] b = md.digest();
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < b.length; i++) {
				sb.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
