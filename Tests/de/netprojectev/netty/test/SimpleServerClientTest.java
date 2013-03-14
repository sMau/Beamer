package de.netprojectev.netty.test;

import de.netprojectev.client.Client;

public class SimpleServerClientTest {

	public static void main(String[] args) throws InterruptedException {
		//Server serv = new Server(9999);
		Thread.sleep(1000);
		Client cl1 = new Client("localhost", 9999, "cl1");
		Thread.sleep(1000);
		Client cl2 = new Client("localhost", 9999, "cl2");

	}
	
}
