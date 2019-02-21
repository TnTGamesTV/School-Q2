package de.finn_tegeler.developing.fancychat.controller;

import java.awt.font.TextHitInfo;
import java.io.IOException;

import com.sun.prism.Graphics;

import de.finn_tegeler.developing.fancychat.ChatManager;
import de.finn_tegeler.developing.fancychat.networking.NetworkManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private Button connectButton;
	@FXML
	private TextField ipTextField, portTextField;
	private Stage primaryStage;

	@FXML
	public void initialize() {
		portTextField.setText("60000");
		ipTextField.setText(NetworkManager.getLocalIP());
		connectButton.setOnAction((e) -> {
			ChatManager.getInstance().init(ipTextField.getText(), Integer.parseInt(portTextField.getText()));

			TextInputDialog textInputDialog = new TextInputDialog();
			textInputDialog.setHeaderText("Welcome");
			textInputDialog.setContentText("Your Name");
			textInputDialog.setGraphic(null);
			textInputDialog.setTitle("FancyChat");
			textInputDialog.showAndWait().ifPresent(text->ChatManager.getInstance().sendMessage(text));
			
			changeScene("chat.fxml");
		});
	}

	public void changeScene(String fxml) {
		Parent pane;
		if (primaryStage == null) {
			primaryStage = (Stage) portTextField.getScene().getWindow();
		}
		try {
			pane = FXMLLoader.load(getClass().getResource(fxml));
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
