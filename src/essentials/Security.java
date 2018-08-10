package essentials;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {

	/**
	 * Returns the hash of a given String.
	 * 
	 * @param string
	 *            The String
	 * @param hashAlgorithm
	 *            This hash algorithm will be used
	 * @return The hashed String
	 * @throws NoSuchAlgorithmException
	 *             if the given hash algorithm does not exist or is not available
	 */
	public static String hash(String string, String hashAlgorithm) throws NoSuchAlgorithmException {

		MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
		messageDigest.update(string.getBytes());
		byte[] bytes = messageDigest.digest();
		StringBuffer builder = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return builder.toString();
	}

	/**
	 * Returns the hash of a given String using the SHA-256 hash algorithm.
	 * 
	 * @param string
	 *            The String
	 * @return The hashed String
	 * @throws NoSuchAlgorithmException
	 *             if the SHA-256 hash algorithm is not available
	 */
	public static String hash(String string) throws NoSuchAlgorithmException {
		return hash(string, "SHA-256");
	}
}
