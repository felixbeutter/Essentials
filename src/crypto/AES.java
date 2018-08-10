package crypto;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	/**
	 * Encrypts a string with a key using AES. If you set hashAlgorithm to null make
	 * sure the key is 16 characters long.
	 * 
	 * @param key
	 *            This String will be used for encryption
	 * @param string
	 *            This String will be encrypted
	 * @param hashAlgorithm
	 *            The key will be hashed with this algorithm (set to null if key
	 *            should not be hashed)
	 * @return The encrypted String
	 * @throws Exception
	 *             if something went wrong while encrypting
	 */
	public static String encrypt(String key, String string, String hashAlgorithm) throws Exception {

		byte[] bytes = key.getBytes();

		if (hashAlgorithm != null) {

			MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
			bytes = messageDigest.digest(key.getBytes());
			bytes = Arrays.copyOf(bytes, 16);
		}

		Key secret = new SecretKeySpec(bytes, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secret);

		return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes()));
	}

	/**
	 * Encrypts a string with a key using AES. The key will be hashed with SHA-256
	 * beforehand.
	 * 
	 * @param key
	 *            This String will be used for encryption
	 * @param string
	 *            This String will be encrypted
	 * @return The encrypted String
	 * @throws Exception
	 *             if something went wrong while encrypting
	 */
	public static String encrypt(String key, String string) throws Exception {
		return encrypt(key, string, "SHA-256");
	}

	/**
	 * Decrypts an encrypted string with a key using AES. If you set hashAlgorithm
	 * to null make sure the key is 16 characters long.
	 * 
	 * @param key
	 *            This String will be used for decryption
	 * @param string
	 *            This String will be decrypted
	 * @param hashAlgorithm
	 *            The key will be hashed with this algorithm (set to null if key
	 *            should not be hashed)
	 * @return The decrypted String
	 * @throws Exception
	 *             if something went wrong while decrypting
	 */
	public static String decrypt(String key, String string, String hashAlgorithm) throws Exception {

		MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm);
		byte[] bytes = messageDigest.digest(key.getBytes());
		bytes = Arrays.copyOf(bytes, 16);

		Key secret = new SecretKeySpec(bytes, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secret);

		return new String(cipher.doFinal(Base64.getDecoder().decode(string)));
	}

	/**
	 * Decrypts an encrypted string with a key using AES. The key will be hashed
	 * with SHA-256 beforehand.
	 * 
	 * @param key
	 *            This String will be used for decryption
	 * @param string
	 *            This String will be decrypted
	 * @return The decrypted String
	 * @throws Exception
	 *             if something went wrong while decrypting
	 */
	public static String decrypt(String key, String string) throws Exception {
		return decrypt(key, string, "SHA-256");
	}
}
