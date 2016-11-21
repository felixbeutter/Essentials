package ssl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;

import essentials.SimpleLog;

public class ClientThread extends Thread {

	SSLSocket socket;
	SimpleLog log;
	BufferedReader reader;
	DataOutputStream writer;

	int id;

	public void run() {

		log.info("Client " + id + " connected!");

		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new DataOutputStream(socket.getOutputStream());
			log.info("Client session " + id + " successfully initialized");

			// TODO server purpose

		} catch (IOException e) {
			log.error("Error occurred while initializing session in client " + id + "!");
			log.logStackTrace(e);
		}

		try {
			socket.close();
			log.debug("Closed socket of client " + id);
		} catch (IOException e) {
			log.warning("Error occurred while closing socket of client  " + id);
			log.logStackTrace(e);
		}

		log.info("Client " + id + " disconnected");
	}

	public ClientThread(SSLSocket socket, int id, SimpleLog log) {

		this.socket = socket;
		this.id = id;
		this.log = log;
	}
}
