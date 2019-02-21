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
		clients.remove(_translate(ip, port));

		System.out.println("Client closed @ " + ip + ":" + port);
	}

	@Override
	public void processMessage(String ip, int port, String message) {
		ClientObject client = _translate(ip, port);

		if (client.getName() == null) {
			client.setName(message);
			System.out.println("Client named @ " + ip + ":" + port + " -> " + message);
		} else {
			System.out.println("Incoming message from " + ip + ":" + port + ": " + message);
			if(message == "list") {
				_send(client, "TEST");
			} else {
				clients.stream().filter(c -> !c.equals(client)).forEach(c -> send(c.getIp(), c.getPort(), message));
			}
		}
	}

	@Override
	public void processNewConnection(String ip, int port) {
		clients.add(new ClientObject(null, ip, port));

		System.out.println("New client @ " + ip + ":" + port);
	}
	
	private void _send(ClientObject client, String message) {
		send(client.getIp(), client.getPort(), message);
	}
}
