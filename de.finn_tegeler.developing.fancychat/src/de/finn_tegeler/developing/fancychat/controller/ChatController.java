package de.finn_tegeler.developing.fancychat.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.finn_tegeler.developing.fancychat.ChatManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

	@FXML
	private Button sendButton;
	@FXML
	private TextField chatTextField;
	@FXML
	private TextArea textArea;
	@FXML
	public void initialize() {
		chatTextField.setOnAction(e-> sendChat(chatTextField.getText()));
		sendButton.setOnAction(e -> sendChat(chatTextField.getText()));
	}

	private void sendChat(String text) {
		System.out.println("Sending "+text );
		ChatManager.getInstance().sendMessage(text);
		chatTextField.clear();
		textArea.appendText("Du: "+text+"\n");
	}

}
