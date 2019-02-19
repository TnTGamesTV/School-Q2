package de.finn_tegeler.developing.fancychat;

public class Main {
	
	public static void main(String[] args) {
		if(args.length > 0) {
			//Server
		} else {
			//Client
		}
		
		
		
		FancyServer server = new FancyServer(60000);

		FancyClient client = new FancyClient("client1", "10.147.64.21", 60000);
		FancyClient client2 = new FancyClient("client2", "10.147.64.21", 60000);
		
		client.send("HALLO");
	}
}
