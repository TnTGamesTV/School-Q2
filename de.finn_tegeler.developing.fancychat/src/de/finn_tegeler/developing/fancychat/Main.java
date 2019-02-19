package de.finn_tegeler.developing.fancychat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		if (args.length > 0) {
			// Server
		} else {
			// Client
		}
		FancyServer server = new FancyServer(60000);
		FancyClient client = new FancyClient("client1", "10.147.64.21", 60000);
		FancyClient client2 = new FancyClient("client2", "10.147.64.21", 60000);
		client.send("HALLO");
		Application.launch(Main.class, args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
		stage.setTitle("FXML Welcome");
		stage.setScene(new Scene(root));
		stage.setFullScreen(true);
		stage.setResizable(false);
		stage.show();
	}
}
