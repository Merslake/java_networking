package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClientGUI {

	public void setupGUI() {

		// Instantiate and configure JFrame
		JFrame window = new JFrame();
		window.setName("Client Control");
		window.setVisible(true);
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Instantiate Two panels
		JPanel upperPanel = new JPanel();
		JPanel lowerPanel = new JPanel();

		// Buttons and text elements
		JButton sendRequestButton = new JButton("Send Request");
		JTextArea text = new JTextArea("Responses will appear here");

		// Arrange and validate
		window.add(upperPanel, BorderLayout.NORTH);
		window.add(lowerPanel, BorderLayout.SOUTH);
		upperPanel.add(sendRequestButton);
		lowerPanel.add(text);
		window.validate();

		// Setup new client for GUI window
		Client buttonClient = new Client("localhost", 8000);

		sendRequestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					buttonClient.makeRequest();

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

			}
		});

		
		while (true) {
			text.append(buttonClient.getLatestResponse());

			
		}
		
	}

	public static void main(String[] args) {
		ClientGUI guiWindow = new ClientGUI();
		guiWindow.setupGUI();

	}

}
