package de.finn_tegeler.developing.fancychat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController {
	@FXML
	private Button connectButton;
	@FXML
	private TextField ipTextField,portTextField;
	@FXML
	public void initialize() {
		System.out.println("initialize");
		portTextField.setText("60000");
		connectButton.setOnAction(e -> System.out.println("asdf"));
	}
}
