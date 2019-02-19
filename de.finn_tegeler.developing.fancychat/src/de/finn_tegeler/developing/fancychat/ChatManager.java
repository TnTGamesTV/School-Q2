package de.finn_tegeler.developing.fancychat;

public class ChatManager {
	
	private static ChatManager INSTANCE;
	
	public static ChatManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ChatManager();
		}
		return INSTANCE;
	}
	
	public FancyClient client;
	
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
		}
	}
	
	public void sendMessage(String message) {
		if (client != null) {
			client.send(message);
		} else {
			throw new IllegalStateException("Not connected");
		}
	}
}
