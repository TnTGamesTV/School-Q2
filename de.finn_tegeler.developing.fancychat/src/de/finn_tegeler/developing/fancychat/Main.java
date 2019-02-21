package de.finn_tegeler.developing.fancychat;

import de.finn_tegeler.developing.fancychat.networking.NetworkManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	/* Server vars */
	private static FancyServer server;
	
	public static void main(String[] args) {
		if (args.length > 0) {
			// Server
			server = new FancyServer(60001);
			System.out.println("Server @ " + NetworkManager.getLocalIP() + ":60000");
		} else {
			// Client
			Application.launch(Main.class, args);
		}
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
