package de.finn_tegeler.developing.fancychat;

public class ClientObject {

	public void setName(String name) {
		this.name = name;
	}

	private String ip;
	private int port;
	private String name;
	
	public ClientObject(String name, String ip, int port) {
		this.name = name;
		this.ip = ip;
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
}
