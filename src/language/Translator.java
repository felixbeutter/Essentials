package language;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import json.JSONArray;

/**
 * @author Johannes Gro�
 *
 */
public class Translator {

	private String langFrom, langTo;

	/**
	 * Default from language = "en"
	 * 
	 * Default to language = "de"
	 * 
	 */
	public Translator() {
		this.langFrom = "en";
		this.langTo = "de";
	}

	/**
	 * Default from language = "en"
	 * 
	 * @param langTo
	 */
	public Translator(String langTo) {
		this.langFrom = "en";
		this.langTo = langTo;
	}

	/**
	 * @param langFrom
	 * @param langTo
	 */
	public Translator(String langFrom, String langTo) {
		this.langFrom = langFrom;
		this.langTo = langTo;
	}

	/**
	 * Translates the given text via goolgle translator
	 * 
	 * @param text
	 *            The text to be translated
	 * @return The translated text
	 */

	public String translateViaGoogle(String text) throws Exception {

		String url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" + this.langFrom
				+ "&tl=" + this.langTo + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return parseResult(response.toString());
	}

	/**
	 * Translates the given text via goolgle translator
	 * 
	 * @param langFrom
	 *            The language shortcut from the given language
	 * 
	 * @param langTo
	 *            The language shortcut of the result translation
	 * 
	 * @param text
	 *            The text to be translated
	 * 
	 * @return The translated text
	 */

	public String translateViaGoogle(String langFrom, String langTo, String text) throws Exception {

		String url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl=" + this.langFrom
				+ "&tl=" + this.langTo + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return parseResult(response.toString());
	}

	/**
	 * @return the langFrom
	 */
	public String getLangFrom() {
		return langFrom;
	}

	/**
	 * @param langFrom
	 *            the langFrom to set
	 */
	public void setLangFrom(String langFrom) {
		this.langFrom = langFrom;
	}

	/**
	 * @return the langTo
	 */
	public String getLangTo() {
		return langTo;
	}

	/**
	 * @param langTo
	 *            the langTo to set
	 */
	public void setLangTo(String langTo) {
		this.langTo = langTo;
	}

	/**
	 * Parse the json result to String
	 * 
	 * @param inputJson
	 *            the json to parse
	 * 
	 * @return the message of the given json
	 */

	private String parseResult(String inputJson) throws Exception {
		JSONArray jsonArray = new JSONArray(inputJson);
		JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
		JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

		return jsonArray3.get(0).toString();
	}

}
