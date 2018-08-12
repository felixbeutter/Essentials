package ssl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import essentials.SimpleLog;

public class SSLClient {

	SimpleLog log;
	SSLServerSocket serverSocket;
	BufferedReader reader;
	DataOutputStream writer;

	final static String PATH = "res\\ssl\\sslclient\\";
	String truststore = "client.truststore", password = "", ip = "127.0.0.1";
	int port = 443, id = 0;

	public SSLClient() {
		log = new SimpleLog();
		File configFile = new File(PATH + "config.properties");
		Properties config = new Properties();

		if (configFile.exists()) {

			try {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(configFile));
				config.load(bis);
				bis.close();
				ip = config.getProperty("ip");
				port = Integer.parseInt(config.getProperty("port"));
				truststore = config.getProperty("truststore");
				password = config.getProperty("password");
				log.info(
						"Successfully read config and set values (port: " + port + ", truststore: " + truststore + ")");
			} catch (IOException e) {
				log.error("IOException occurred while reading config file (config.properties)!");
				log.logStackTrace(e);
				log.warning("Could not read config!");
			}

		} else {
			log.error("Config file (config.properties) does not exist! Default values will be used...");
			log.warning("Could not read config!");
		}

		System.setProperty("javax.net.ssl.trustStore", PATH + config.getProperty("truststore"));
		System.setProperty("javax.net.ssl.trustStorePassword", config.getProperty("password"));

		try {
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) factory.createSocket(ip, port);

			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new DataOutputStream(socket.getOutputStream());
				log.info("Session successfully initialized");

				// TODO client purpose

			} catch (IOException e) {
				log.error("Error occurred while initializing session in client " + id + "!");
				log.logStackTrace(e);
			}
		} catch (IOException e) {
			log.fatal("IOException occurred while connecting to server! Shutting down...");
			log.logStackTrace(e);
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		new SSLClient();
	}
}
