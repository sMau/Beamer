package de.netprojectev.tests.client;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.client.Client;
import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientMessageProxyTest {

	private Client client;

	private ServerMediaFile media1 = new VideoFile("Vid1", new File("/home/samu"));
	private ServerMediaFile media2 = new VideoFile("Vid2", new File("/home/samu"));
	private ServerTickerElement elt123 = new ServerTickerElement("Test123");
	private ServerTickerElement elt456 = new ServerTickerElement("Test456");
	
	private ClientMediaFile clmedia1 = new ClientMediaFile(media1);
	private ClientMediaFile clmedia2 = new ClientMediaFile(media2);
	private ClientTickerElement clelt1 = new ClientTickerElement(elt123);
	private ClientTickerElement clelt2 = new ClientTickerElement(elt456);
	
	@Before
	public void setUp() {
		client = new Client("127.0.0.1", 11111, new LoginData("test", ""));
	}
	
	@Test
	public void testReceiveMessage1() throws UnkownMessageException, MediaDoesNotExsistException {
		client.getProxy().receiveMessage(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, clmedia1));
		assertEquals(clmedia1, client.getProxy().getMediaModel().getMediaFileById(media1.getId()));
		client.getProxy().receiveMessage(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, clmedia2));
		assertEquals(clmedia1, client.getProxy().getMediaModel().getMediaFileById(media1.getId()));
		assertEquals(clmedia2, client.getProxy().getMediaModel().getMediaFileById(media2.getId()));
	}
	
	@Test
	public void testReceiveMessage2() throws UnkownMessageException, MediaDoesNotExsistException {
		
		client.getProxy().receiveMessage(new Message(OpCode.STC_ADD_LIVE_TICKER_ELEMENT_ACK, clelt1));
		assertEquals(clelt1, client.getProxy().getTickerModel().getElementByID(elt123.getId()));
		client.getProxy().receiveMessage(new Message(OpCode.STC_ADD_LIVE_TICKER_ELEMENT_ACK, clelt2));
		assertEquals(clelt1, client.getProxy().getTickerModel().getElementByID(elt123.getId()));
		assertEquals(clelt2, client.getProxy().getTickerModel().getElementByID(elt456.getId()));
	}
	
	
	
}
