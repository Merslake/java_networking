package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class Server {
	private int port; 
	
	public Server (int port) {
		this.port = port;
	}
	
	private void serveRequest() throws IOException {
		System.out.println("Waiting for request from client...");
		
		
		//Define server on port
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO catch block
			e.printStackTrace();
		}
		
		
		//Client socket 
		clientSocket = serverSocket.accept();		
		System.out.println("Accepted client: \"" + clientSocket.getInetAddress() + "\"");
		
		
		
		processClientRequest(clientSocket);
		
		try {
			serverSocket.close();
		} catch (IOException ioe) {
			throw new RuntimeException("ERROR: dealing with IO on socket", ioe);
		}


	}
	
	//Process client request method

	private void processClientRequest(Socket clientSocket) throws IOException {
		//Stream is buffered to in and out
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		
		//Get request from the client
		
		String request;
		String response;
		
		boolean keepRunning = true;
		
		//Boolean controlled while loop.
		while (keepRunning) {
			
			//Checks whether socket is connected, otherwise breaking loop.
			if ((request = in.readLine()) == null) {
				keepRunning = false;
			}
			
			System.out.println("Got request from client: \"" + request + "\".");
			
			
			//Send a response to client
			response = "You made a request to port " + port + " at " + LocalTime.now() + ".";
			out.println(response);
		}
		
		
		//Resources tidy
		in.close();
		out.close();
		System.out.println("No further client requests.");
	}

	public static void main(String[] args) {
		System.out.println("Starting socket based server...");
		//New server instantiated with port 8000.
		Server server = new Server(8000);
		
		try {
			server.serveRequest();
		} catch (IOException ioe) {
			throw new RuntimeException("Error: dealing with IO on socket", ioe);
		}
		

	}

}
