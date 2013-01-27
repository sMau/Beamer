package de.netprojectev.netty.testing2;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TestServer server = new TestServer();
		server.bind();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TestClient client = new TestClient();
		
		client.connect();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.writeMessage((byte) 17);
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.writeMessage((byte) 16);
	}

}
