package de.finn_tegeler.developing.fancychat.networking;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Finn Tegeler
 */
public class NetworkManager {
	
	public static String getLocalIP() {
		try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			return socket.getLocalAddress().getHostAddress();
		}
		catch (Exception e) {
			return null;
		}
	}
}
