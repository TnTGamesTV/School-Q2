package de.finn_tegeler.developing.fancychat;

public class ClientObject {

	private String ip;
	private int port;
	
	public ClientObject(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
}
