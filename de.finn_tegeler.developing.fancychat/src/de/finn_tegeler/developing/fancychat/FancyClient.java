package de.finn_tegeler.developing.fancychat;

import de.finn_tegeler.developing.fancychat.lib.Client;

public class FancyClient extends Client {

	
	private String name;
	public FancyClient(String name, String ip, int port) {
		super(ip, port);
		this.name = name;
		
		System.out.println("Created client @ " + ip + ":" + port);
	}

	@Override
	public void processMessage(String message) {
		System.out.println(name + " IN: " + message);
	}
}
