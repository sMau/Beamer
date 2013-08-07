package de.netprojectev.tests.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.networking.MessageProxyServer;

public class ServerMessageProxyTest {
	
	private MessageProxyServer proxy;
	private ServerMediaFile media1 = new VideoFile("Vid1", new File("/home/samu"));
	private ServerMediaFile media2 = new VideoFile("Vid2", new File("/home/samu"));
	private ServerMediaFile media3 = new VideoFile("Vid3", new File("/home/samu"));
	private ServerMediaFile media4 = new VideoFile("Vid4", new File("/home/samu"));
	
	@Before
	public void setUp() {
		proxy = new MessageProxyServer();
	}
	
	@Test
	public void testReceiveMessage1() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException {
		
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media1), null);
		assertEquals(media1, proxy.getMediaModel().getNext());
		assertEquals(media1, proxy.getMediaModel().getNext());
		
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media2), null);
		
		ServerMediaFile next = proxy.getMediaModel().getNext();
		
		assertTrue(next.equals(media1) || next.equals(media2));
		
	}
	
	@Test
	public void testReceiveMessage2() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException {
		
		ServerTickerElement elt1 = new ServerTickerElement("test12");
		ServerTickerElement elt2 = new ServerTickerElement("test34");
		
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT, elt1), null);
		assertEquals(elt1, proxy.getTickerModel().getElementByID(elt1.getId()));
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT, elt1), null);
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT, elt2), null);
		assertEquals(elt1, proxy.getTickerModel().getElementByID(elt1.getId()));
		assertEquals(elt2, proxy.getTickerModel().getElementByID(elt2.getId()));
		
	}
	
	@Test
	public void testReceiveMessage3() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException {
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media1), null);
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media2), null);
		ServerMediaFile next = proxy.getMediaModel().getNext();
		assertTrue(next.equals(media1) || next.equals(media2));
		assertNull(proxy.getCurrentFile());
		proxy.receiveMessage(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE), null);
		assertTrue(proxy.getCurrentFile().equals(media1) || proxy.getCurrentFile().equals(media2));
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media3), null);
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media4), null);
		proxy.receiveMessage(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, media1.getId()), null);
		proxy.receiveMessage(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, media2.getId()), null);
		assertTrue(proxy.getCurrentFile().equals(media1) || proxy.getCurrentFile().equals(media2));
		proxy.receiveMessage(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE), null);
		assertTrue(proxy.getCurrentFile().equals(media3) || proxy.getCurrentFile().equals(media4));
	}
	
	@Test
	public void testReceiveMessage4() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException {
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media1), null);
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media2), null);
		
		ServerMediaFile next = proxy.getMediaModel().getNext();
		assertTrue(next.equals(media1) || next.equals(media2));

		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media3), null);
		proxy.receiveMessage(new Message(OpCode.CTS_ADD_MEDIA_FILE, media4), null);
		
		assertNull(proxy.getCurrentFile());
		
		proxy.receiveMessage(new Message(OpCode.CTS_QUEUE_MEDIA_FILE, media1.getId()), null);
		proxy.receiveMessage(new Message(OpCode.CTS_QUEUE_MEDIA_FILE, media2.getId()), null);
		proxy.receiveMessage(new Message(OpCode.CTS_QUEUE_MEDIA_FILE, media3.getId()), null);

		proxy.receiveMessage(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE), null);
		assertEquals(media1, proxy.getCurrentFile());
		proxy.receiveMessage(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE), null);
		assertEquals(media2, proxy.getCurrentFile());
		proxy.receiveMessage(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE), null);
		assertEquals(media3, proxy.getCurrentFile());		
		
		proxy.receiveMessage(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, media3.getId()), null);
		proxy.receiveMessage(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, media1.getId()), null);
		proxy.receiveMessage(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, media4.getId()), null);
		
		proxy.receiveMessage(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE), null);
		assertEquals(media2, proxy.getCurrentFile());
	}
	
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testReceiveMessage5() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException {
		proxy.receiveMessage(new Message(OpCode.CTS_QUEUE_MEDIA_FILE, media1.getId()), null);
	}
	
	@Test(expected=MediaListsEmptyException.class)
	public void testReceiveMessage6() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException {
		proxy.receiveMessage(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE), null);
	}
	

}
