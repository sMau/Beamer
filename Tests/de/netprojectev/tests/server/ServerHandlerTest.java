package de.netprojectev.tests.server;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.netprojectev.client.networking.Client;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.ServerInit;
import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.datastructures.media.ServerMediaFile;
import de.netprojectev.server.datastructures.media.VideoFile;
import de.netprojectev.server.model.MediaDoesNotExsistException;

public class ServerHandlerTest {

	private ServerInit serverInit;
	private Client client;
	private ServerMediaFile media1 = new VideoFile("Vid1", new File("/home/samu"));
	private ServerMediaFile media2 = new VideoFile("Vid2", new File("/home/samu"));
	private TickerElement elt123 = new TickerElement("Test123");
	private TickerElement elt456 = new TickerElement("Test456");
	
	@Before
	public void setUp() throws InterruptedException {
		
		serverInit = new ServerInit(11111);
		Thread.sleep(50);
		client = new Client("127.0.0.1", 11111, "Samutest");
		Thread.sleep(50);
	}
	
	@After
	public void tearDown() throws InterruptedException {
		serverInit.releaseNetworkRessources();
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
	
}
