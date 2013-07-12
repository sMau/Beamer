package de.netprojectev.tests.client;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.client.Client;
import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.Server;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientHandlerTest {

	private Server server;
	private Client client;
	private ServerMediaFile media1 = new VideoFile("Vid1", new File("/home/samu"));
	private ServerMediaFile media2 = new VideoFile("Vid2", new File("/home/samu"));
	private ServerTickerElement elt123 = new ServerTickerElement("Test123");
	private ServerTickerElement elt456 = new ServerTickerElement("Test456");
	
	@Before
	public void setUp() throws InterruptedException {
		server = new Server(11111); 
		Thread.sleep(50);
		client = new Client("127.0.0.1", 11111, new LoginData("clienttest1", ""));
		client.connect();
	}
	
	@Test
	public void testReceiveMessage() {
		client.getProxy().sendMessageToServer(new Message(OpCode.CTS_ADD_MEDIA_FILE, media1));
		client.getProxy().sendMessageToServer(new Message(OpCode.CTS_ADD_MEDIA_FILE, media2));
		client.getProxy().sendMessageToServer(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT, elt123));
		client.getProxy().sendMessageToServer(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT, elt456));
		//TODO
	}
	
	
}
