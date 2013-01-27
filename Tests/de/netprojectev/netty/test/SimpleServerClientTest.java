package de.netprojectev.netty.test;

import de.netprojectev.networking.client.Client;
import de.netprojectev.networking.server.Server;

public class SimpleServerClientTest {

	public static void main(String[] args) throws InterruptedException {
		Server serv = new Server(9999);
		Thread.sleep(1000);
		Client cl1 = new Client("localhost", 9999, "cl1");
		Thread.sleep(1000);
		Client cl2 = new Client("localhost", 9999, "cl2");

	}
	
}
