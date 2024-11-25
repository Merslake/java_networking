package client;

public class ClientRunner {

	public ClientRunner() {
		System.out.println("Creating clients...");
		runNewClient();
		pause(2000);
		runNewClient();
		pause(2000);
		runNewClient();
	}

	private void pause(int pauseLength) {
		try {
			Thread.sleep(pauseLength);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	private void runNewClient() {
		RunnableMultiClient runnableClient = new RunnableMultiClient();
		new Thread(runnableClient).start();
	}
	
	public static void main(String[] args) {
		System.out.println("Creating and running multiple clients");
		new ClientRunner();
		
	}



}
