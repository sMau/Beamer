package de.netprojectev.tests.server;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.netprojectev.client.Client;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.Server;
import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.datastructures.media.ServerMediaFile;
import de.netprojectev.server.datastructures.media.VideoFile;
import de.netprojectev.server.exceptions.MediaDoesNotExsistException;

public class ServerHandlerTest {

	private Server serverInit;
	private Client client;
	private ServerMediaFile media1 = new VideoFile("Vid1", new File("/home/samu"));
	private ServerMediaFile media2 = new VideoFile("Vid2", new File("/home/samu"));
	private TickerElement elt123 = new TickerElement("Test123");
	private TickerElement elt456 = new TickerElement("Test456");
	private String clientName1 = "Samutest1";
	private String clientName2 = "Samutest2";
	private String clientName3 = "Samutest3";
	private String serverPw = "";
	
	@Before
	public void setUp() throws InterruptedException {
		
		serverInit = new Server(11111);
		Thread.sleep(50);
		
		assertTrue(serverInit.getProxy().getAllClients().size() == 0);
		
		client = new Client("127.0.0.1", 11111, new LoginData(clientName1, serverPw));
		client.connect();
		Thread.sleep(50);
	}
	
	@After
	public void tearDown() throws InterruptedException {
		Thread.sleep(50);
		serverInit.shutdownServer();
		Thread.sleep(50);
	}
	
	@Test
	public void testMessageReceivedHandler1() throws InterruptedException {
		
		client.sendMessageToServer(new Message(OpCode.ADD_MEDIA_FILE, media1));
		client.sendMessageToServer(new Message(OpCode.ADD_MEDIA_FILE, media2));
		client.sendMessageToServer(new Message(OpCode.SHOW_MEDIA_FILE, media1.getId()));
		Thread.sleep(50);
		assertEquals(serverInit.getProxy().getCurrentFile(), media1);
	}
	
	@Test
	public void testMessageReceivedHandler2() throws InterruptedException, MediaDoesNotExsistException {
		
		client.sendMessageToServer(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt123));
		client.sendMessageToServer(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt456));
		Thread.sleep(50);
		assertEquals(serverInit.getProxy().getTickerModel().getElementByID(elt123.getId()), elt123);
		assertEquals(serverInit.getProxy().getTickerModel().getElementByID(elt456.getId()), elt456);
	}
	
	@Test
	public void testMessageReceivedHandler3() throws InterruptedException, MediaDoesNotExsistException {
		
		client.sendMessageToServer(new Message(OpCode.ADD_MEDIA_FILE, media1));
		client.sendMessageToServer(new Message(OpCode.ADD_MEDIA_FILE, media2));
		Thread.sleep(50);
		client.sendMessageToServer(new Message(OpCode.REMOVE_MEDIA_FILE, media1.getId()));
		client.sendMessageToServer(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
		Thread.sleep(50);
		assertEquals(serverInit.getProxy().getCurrentFile(), media2);
		
		
		client.sendMessageToServer(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt123));
		client.sendMessageToServer(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt456));
		Thread.sleep(50);
		client.sendMessageToServer(new Message(OpCode.REMOVE_LIVE_TICKER_ELEMENT, elt123.getId()));
		
		Thread.sleep(50);
		assertEquals(serverInit.getProxy().getTickerModel().getElementByID(elt456.getId()), elt456);
		
	}
	
	@Test
	public void testAuth1() {
		assertTrue(serverInit.getProxy().getAllClients().size() == 1);
	}
	
	@Test
	public void testAuth2() throws InterruptedException {
		
		assertTrue(serverInit.getProxy().getAllClients().size() == 1);
		
		new Client("127.0.0.1", 11111, new LoginData(clientName2, serverPw)).connect();
		Thread.sleep(50);
		assertTrue(serverInit.getProxy().getAllClients().size() == 2);
		
		new Client("127.0.0.1", 11111, new LoginData(clientName3, serverPw)).connect();
		Thread.sleep(50);
		assertTrue(serverInit.getProxy().getAllClients().size() == 3);
		
	}
	
	@Test
	public void testAuth3() throws InterruptedException {
		
		assertTrue(serverInit.getProxy().getAllClients().size() == 1);
		
		Client client2 = new Client("127.0.0.1", 11111, new LoginData(clientName2, serverPw));
		client2.connect();
		Thread.sleep(50);
		
		client2.sendMessageToServer(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt123));
		
		assertTrue(serverInit.getProxy().getAllClients().size() == 2);
		
		Client client3 = new Client("127.0.0.1", 11111, new LoginData(clientName3, serverPw));
		client3.connect();
		Thread.sleep(50);
		
		assertTrue(serverInit.getProxy().getAllClients().size() == 3);
		
		client3.sendMessageToServer(new Message(OpCode.ADD_MEDIA_FILE, media2));
		assertTrue(serverInit.getProxy().getAllClients().size() == 3);
		client3.sendMessageToServer(new Message(OpCode.ADD_MEDIA_FILE, media1));
		
		Thread.sleep(50);

		client.sendMessageToServer(new Message(OpCode.REMOVE_MEDIA_FILE, media2.getId()));
		
		assertTrue(serverInit.getProxy().getAllClients().size() == 3);

	}
	
	@Test
	public void testDisc1() throws InterruptedException {
		assertTrue(serverInit.getProxy().getAllClients().size() == 1);
		
		Client client2 = new Client("127.0.0.1", 11111, new LoginData(clientName2, serverPw));
		client2.connect();
		Thread.sleep(50);
		assertTrue(serverInit.getProxy().getAllClients().size() == 2);
		
		new Client("127.0.0.1", 11111, new LoginData(clientName3, serverPw)).connect();
		Thread.sleep(50);
		assertTrue(serverInit.getProxy().getAllClients().size() == 3);
		
		client2.sendMessageToServer(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt123));
		client2.sendMessageToServer(new Message(OpCode.ADD_MEDIA_FILE, media2));
		
		client2.disconnect();
		Thread.sleep(50);
		assertTrue(serverInit.getProxy().getAllClients().size() == 2);
		
		client.sendMessageToServer(new Message(OpCode.REMOVE_MEDIA_FILE, media2.getId()));
		
		client.disconnect();
		Thread.sleep(50);
		assertTrue(serverInit.getProxy().getAllClients().size() == 1);
	}
	
	
	@Test
	public void testBroadcast1() throws InterruptedException {
		assertTrue(serverInit.getProxy().getAllClients().size() == 1);
		Client client2 = new Client("127.0.0.1", 11111, new LoginData(clientName2, serverPw));
		client2.connect();
		Client client3 = new Client("127.0.0.1", 11111, new LoginData(clientName3, serverPw));
		client3.connect();
		
		Thread.sleep(50);
		assertTrue(serverInit.getProxy().getAllClients().size() == 3);
		
		serverInit.getProxy().broadcastMessage(new Message(OpCode.CONNECTION_ACK));
		
		//TODO add the asserts after client implemenatation
		
	}
	
}
