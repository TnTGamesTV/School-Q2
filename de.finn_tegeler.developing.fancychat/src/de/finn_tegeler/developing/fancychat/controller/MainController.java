package de.finn_tegeler.developing.fancychat.controller;

import de.finn_tegeler.developing.fancychat.ChatManager;
import de.finn_tegeler.developing.fancychat.networking.NetworkManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController {
	
	@FXML
	private Button		connectButton;
	@FXML
	private TextField	ipTextField, portTextField;
	
	@FXML
	public void initialize() {
		ipTextField.setText(NetworkManager.getLocalIP());
		portTextField.setText("60000");
		connectButton.setOnAction((e) -> {
			ChatManager.getInstance().init(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
		});
	}
}
