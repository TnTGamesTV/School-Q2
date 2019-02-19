package de.finn_tegeler.developing.fancychat;

public class ChatManager {

	private static ChatManager INSTANCE;
	
	public static ChatManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ChatManager();
		}
		
		return INSTANCE;
	}
	
	public FancyClient client;
	
	public void init(String ip, int port) {
		if(client == null) {
			client = new FancyClient("client", ip, port);
		}
	}
	
	public void sendMessage(String message) {
		
	}
} 
