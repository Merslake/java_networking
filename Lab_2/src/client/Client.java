package client;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;





public class Client {
	private static final int NUM_OF_REQUESTS = 3;
	private static final long TIME_TO_SLEEP_BETWEEN_CLIENT_REQUESTS = 2000;
	private String host;
	private int port;
	private String clientID;

	// Client constructor
	public Client(String host, int port) {
		clientID = generateClientID();
		System.out.println("Client created with ID: \"" + clientID + "\"");
		this.host = host;
		this.port = port;
	}
	

	private String generateClientID() {
		clientID = LocalTime.now().format(DateTimeFormatter.ofPattern("mmssSSS"));
		return clientID;
	}


	public void makeRequest() throws IOException {
		System.out.println("Lab 2 Trying to open socket to server...");

		Socket socket = null;

		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException uhe) {
			throw new RuntimeException("Unknown host: \"" + host + "\".", uhe);
		} catch (IOException ioe) {
			throw new RuntimeException("Could not open the port " + port + " on host: " + host + ".", ioe);
		}

		System.out.println("Successfully opened connection to server...");

		makeRequests(socket);

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Socket successfully closed.");
	}

	private void makeRequests(Socket socket) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Send request to the server
		String request = clientID + " : GET";
		String response;

		for (int i = 0; i < NUM_OF_REQUESTS; i++) {
			out.println(request + " number " + (i + 1));
			System.out.println("Sent " + request + " request to server.");

			// Get response from the server
			System.out.print("Response: \"");
			response = in.readLine();
			System.out.println(response + "\"");

			try {
				Thread.sleep(TIME_TO_SLEEP_BETWEEN_CLIENT_REQUESTS);
				System.out.println("Sleeping...");
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}

		}

		in.close();
		out.close();
	}

	public static void main(String[] args) {
		
		System.out.println("Starting Lab 2 socket-based client...");
		Client client = new Client("localhost", 8000);
		try {
			client.makeRequest();
		} catch (IOException ioe) {
			throw new RuntimeException("ERROR: dealing with IO on socket", ioe);
		}
	}

}
