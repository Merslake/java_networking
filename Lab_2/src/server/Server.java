package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class Server implements Runnable {
	private static final int SERVER_LIFETIME = 40;
	private static final int MILLISECONDS = 1000;
	private int serverPort;
	private int clientNumber = 0;
	private boolean isStopped = false;
	private ServerSocket serverSocket;

	public Server(int port) {
		this.serverPort = port;
	}

	@Override
	public void run() {
		try {
			serveRequest();
		} catch (IOException ioe) {
			throw new RuntimeException("Error dealing with IO On socket", ioe);
		}

	}

	private void serveRequest() throws IOException {
		System.out.println("Waiting for request from client...");

		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		Socket clientSocket = null;

		while (!isStopped()) {
			try {
				clientSocket = serverSocket.accept();
				clientNumber++;
				System.out.println(
						clientNumber + ": opened socket from client \"" + clientSocket.getInetAddress() + "\"");
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server stopped.");
					return;
				}
				throw new RuntimeException("Error: accepting client connection", e);
			}
		}
		System.out.println("Accepted client: \"" + clientSocket.getInetAddress() + "\"");

		ClientHandler clientHandler = new ClientHandler(clientSocket, serverPort, clientNumber);
		new Thread(clientHandler).start();

		// Close server socket when no longer required.
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server socket suucessfully closed by serve request.");

	}

	private boolean isStopped() {
		return isStopped;
	}

	private void stop() {
		System.out.println("... stopping server and closing socket...");
		isStopped = true;
		try {
			serverSocket.close();
		} catch (IOException ioe) {
			throw new RuntimeException("ERROR: closing server via stop() method.", ioe);
		}
		System.out.println("...closed socket...");
	}

	public static void main(String[] args) {
		System.out.println("Starting Lab 2 socket based server...");
		Server server = new Server(8000);
		new Thread(server).start();
		System.out.println("Pausing main thread...");
		try {
			Thread.sleep(SERVER_LIFETIME * MILLISECONDS);
		} catch (InterruptedException ie) {
			System.out.println("ERROR with sleeping server?");
			ie.printStackTrace();
		}
		System.out.println("Stopping server...");
		server.stop();
	}


}
