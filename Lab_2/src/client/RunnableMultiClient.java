package client;

import java.io.IOException;

public class RunnableMultiClient implements Runnable {

	@Override
	public void run() {
		Client client = new Client("localhost", 8000);
		try {
			client.makeRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
