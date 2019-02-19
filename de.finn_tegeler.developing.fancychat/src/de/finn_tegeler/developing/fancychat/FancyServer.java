package de.finn_tegeler.developing.fancychat;

import de.finn_tegeler.developing.fancychat.lib.Server;

import java.util.ArrayList;
import java.util.List;

public class FancyServer extends Server {
	
	List<ClientObject> clients = new ArrayList<>();
	
	public FancyServer(int port) {
		super(port);
	}
	
	private ClientObject _translate(String ip, int port) {
		return clients.stream().filter(e -> e.getIp().equals(ip)).filter(e -> e.getPort() == port).findFirst()
		        .orElse(null);
	}
	
	@Override
	public void processClosedConnection(String ip, int port) {
		clients.forEach((e) -> {
			if (e.getIp().equals(ip) && e.getPort() == port) {
				clients.remove(e);
			}
		});
	}
	
	@Override
	public void processMessage(String ip, int port, String message) {
		ClientObject client = _translate(ip, port);
		clients.forEach((c) -> {
			if (!c.equals(client)) {
				send(c.getIp(), c.getPort(), message);
			}
		});
		System.out.println("Incoming message from " + ip + ":" + port + ": " + message);
	}
	
	@Override
	public void processNewConnection(String ip, int port) {
		clients.add(new ClientObject(ip, port));
	}
}
