package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

public class ClientHandler implements Runnable {

	private Socket clientSocket;
	private int serverPort;
	private int clientNumber;

	public ClientHandler(Socket clientSocket, int serverPort, int clientNumber) {
		this.clientSocket = clientSocket;
		this.serverPort = serverPort;
		this.clientNumber = clientNumber;

	}

	@Override
	public void run() {
		try {
			processClientRequest(clientSocket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Process client request method
	private void processClientRequest(Socket clientSocket) throws IOException {
		// Stream is buffered to in and out
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

		// Get request from the client

		String request;
		String response = "Thank you for making a request to port" + serverPort + ". Your ID was: ";
		String clientID = "";

		boolean keepRunning = true;

		// Boolean controlled while loop.
		while (keepRunning) {

			System.out.println(clientNumber + ": waiting for request from client...");

			// Checks whether socket is connected, otherwise breaking loop.
			if ((request = in.readLine()) == null) {
				keepRunning = false;
			}

			System.out.println(clientNumber + ": Got request from client: \"" + request + "\".");
			if (request != null) {
				clientID = request.substring(0, 8);
			}
			
			
			//Send request back to client
			out.println(response + clientID + ".");
			System.out.println("Sending this response to client:" + response);
		}

		// Resources tidy
		in.close();
		out.close();
		System.out.println(clientNumber + ": No further client requests.");
	}

}
