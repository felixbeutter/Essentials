package ssl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import essentials.SimpleLog;

public class SSLServer {

	SimpleLog log;
	SSLServerSocket serverSocket;

	final static String PATH = "res\\ssl\\sslserver\\";
	String name = "SSL server", keystore = "server.keystore", password = "";
	int port = 443, id = 0;

	public SSLServer() {

		log = new SimpleLog();

		String message = "Started " + name + " on port " + port;

		String s = "";
		for (int i = 0; i < message.length(); i++)
			s = s + "=";

		log.info(s);
		log.info(message);
		log.info(s);

		File configFile = new File(PATH + "config.properties");
		Properties config = new Properties();

		if (configFile.exists()) {

			try {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(configFile));
				config.load(bis);
				bis.close();
				port = Integer.parseInt(config.getProperty("port"));
				keystore = config.getProperty("keystore");
				password = config.getProperty("password");
				log.info("Successfully read config and set values (port: " + port + ", keystore: " + keystore + ")");
			} catch (IOException e) {
				log.error("IOException occurred while reading config file (config.properties)!");
				log.logStackTrace(e);
				log.warning("Could not read config!");
			}

		} else {
			log.error("Config file (config.properties) does not exist! Default values will be used...");
			log.warning("Could not read config!");
		}

		if (keystore == null || !new File(PATH + keystore).exists()) {
			log.fatal("Missing keystore file! Couldn't create server. Shutting down...");
			System.exit(1);
		}

		System.setProperty("javax.net.ssl.keyStore", PATH + keystore);
		System.setProperty("javax.net.ssl.keyStorePassword", password);
		log.info("Set system properties (keystore and password)");

		try {
			SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			serverSocket = (SSLServerSocket) factory.createServerSocket(port);
		} catch (IOException e) {
			log.fatal("Fatal error occurred while creating server! Shutting down...");
			log.logStackTrace(e);
			System.exit(1);
		}

		while (true) {

			SSLSocket client = null;

			try {
				client = (SSLSocket) serverSocket.accept();
			} catch (IOException e) {
				log.error("Error occurred while accepting a client (id: " + id + ")! Closing socket...");
				log.logStackTrace(e);
			}

			if (client != null)
				new ClientThread(client, id, log).start();

			id++;
		}
	}

	public static void main(String[] args) {
		new SSLServer();
	}
}
