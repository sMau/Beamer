package de.netprojectev.tests.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.datastructures.media.ServerMediaFile;
import de.netprojectev.server.datastructures.media.VideoFile;
import de.netprojectev.server.model.MediaDoesNotExsistException;
import de.netprojectev.server.model.MediaListsEmptyException;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.server.networking.UnkownMessageException;

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
	public void testReceiveMessage1() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media1));
		assertEquals(media1, proxy.getMediaModel().getNext());
		assertEquals(media1, proxy.getMediaModel().getNext());
		
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media2));
		
		ServerMediaFile next = proxy.getMediaModel().getNext();
		
		assertTrue(next.equals(media1) || next.equals(media2));
		
	}
	
	@Test
	public void testReceiveMessage2() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		
		TickerElement elt1 = new TickerElement("test12");
		TickerElement elt2 = new TickerElement("test34");
		
		proxy.receiveMessage(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt1));
		assertEquals(elt1, proxy.getTickerModel().getElementByID(elt1.getId()));
		proxy.receiveMessage(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt1));
		proxy.receiveMessage(new Message(OpCode.ADD_LIVE_TICKER_ELEMENT, elt2));
		assertEquals(elt1, proxy.getTickerModel().getElementByID(elt1.getId()));
		assertEquals(elt2, proxy.getTickerModel().getElementByID(elt2.getId()));
		
	}
	
	@Test
	public void testReceiveMessage3() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media1));
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media2));
		ServerMediaFile next = proxy.getMediaModel().getNext();
		assertTrue(next.equals(media1) || next.equals(media2));
		assertNull(proxy.getCurrentFile());
		proxy.receiveMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
		assertTrue(proxy.getCurrentFile().equals(media1) || proxy.getCurrentFile().equals(media2));
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media3));
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media4));
		proxy.receiveMessage(new Message(OpCode.REMOVE_MEDIA_FILE, media1.getId()));
		proxy.receiveMessage(new Message(OpCode.REMOVE_MEDIA_FILE, media2.getId()));
		assertTrue(proxy.getCurrentFile().equals(media1) || proxy.getCurrentFile().equals(media2));
		proxy.receiveMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
		assertTrue(proxy.getCurrentFile().equals(media3) || proxy.getCurrentFile().equals(media4));
	}
	
	@Test
	public void testReceiveMessage4() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media1));
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media2));
		
		ServerMediaFile next = proxy.getMediaModel().getNext();
		assertTrue(next.equals(media1) || next.equals(media2));

		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media3));
		proxy.receiveMessage(new Message(OpCode.ADD_MEDIA_FILE, media4));
		
		assertNull(proxy.getCurrentFile());
		
		proxy.receiveMessage(new Message(OpCode.QUEUE_MEDIA_FILE, media1.getId()));
		proxy.receiveMessage(new Message(OpCode.QUEUE_MEDIA_FILE, media2.getId()));
		proxy.receiveMessage(new Message(OpCode.QUEUE_MEDIA_FILE, media3.getId()));

		proxy.receiveMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
		assertEquals(media1, proxy.getCurrentFile());
		proxy.receiveMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
		assertEquals(media2, proxy.getCurrentFile());
		proxy.receiveMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
		assertEquals(media3, proxy.getCurrentFile());		
		
		proxy.receiveMessage(new Message(OpCode.REMOVE_MEDIA_FILE, media3.getId()));
		proxy.receiveMessage(new Message(OpCode.REMOVE_MEDIA_FILE, media1.getId()));
		proxy.receiveMessage(new Message(OpCode.REMOVE_MEDIA_FILE, media4.getId()));
		
		proxy.receiveMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
		assertEquals(media2, proxy.getCurrentFile());
	}
	
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testReceiveMessage5() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		proxy.receiveMessage(new Message(OpCode.QUEUE_MEDIA_FILE, media1.getId()));
	}
	
	@Test(expected=MediaListsEmptyException.class)
	public void testReceiveMessage6() throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		proxy.receiveMessage(new Message(OpCode.SHOW_NEXT_MEDIA_FILE));
	}
	

}
