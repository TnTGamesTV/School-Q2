package de.finn_tegeler.developing.fancychat;

import java.util.ArrayList;
import java.util.List;

import de.finn_tegeler.developing.fancychat.lib.Server;

public class FancyServer extends Server {

	List<ClientObject> clients = new ArrayList<>();

	public FancyServer(int port) {
		super(port);
	}

	@Override
	public void processNewConnection(String ip, int port) {
		clients.add(new ClientObject(ip, port));
	}

	@Override
	public void processMessage(String ip, int port, String message) {
		ClientObject client = _translate(ip, port);

		clients.forEach((c) -> {
			if (!c.equals(client)) {
				send(c.getIp(), c.getPort(), message);
			}
		});
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

}
