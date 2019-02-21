package de.finn_tegeler.developing.fancychat;

import java.util.ArrayList;
import java.util.List;

import de.finn_tegeler.developing.fancychat.lib.FancyChatLoggedInListener;

public class ChatManager {
	
	private static List<FancyChatLoggedInListener> loggedInListeners = new ArrayList<>();
	private static ChatManager INSTANCE;
	
	public static ChatManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChatManager();
		}
		return INSTANCE;
	}
	
	public FancyClient client;
	public String name;
	
	public void close() {
		if (client != null) {
			client.close();
		} else {
			throw new IllegalStateException("Not connected");
		}
	}
	
	public void init(String ip, int port) {
		if (client == null) {
			client = new FancyClient("client", ip, port);
			
			loggedInListeners.forEach((e) -> {
				e.loggedIn();
			});
		}
	}
	
	public static void registerLoggedInListener(FancyChatLoggedInListener listener) {
		loggedInListeners.add(listener);
	}
	
	public void sendMessage(String message) {
		if (client != null) {
			client.send(message);
		} else {
			throw new IllegalStateException("Not connected");
		}
	}
}
