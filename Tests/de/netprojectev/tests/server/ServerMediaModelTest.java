package de.netprojectev.tests.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.model.MediaModelServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class ServerMediaModelTest {

	private MessageProxyServer proxy;
	private MediaModelServer mediaModel;
	
	private VideoFile media1 = new VideoFile("Vid1", new File("/home/samu"));
	private VideoFile media2 = new VideoFile("Vid2", new File("/home/samu"));
	private VideoFile media3 = new VideoFile("Vid3", new File("/home/samu"));
	private VideoFile media4 = new VideoFile("Vid4", new File("/home/samu"));
	
	@Before
	public void setUp() {
		proxy = new MessageProxyServer();
		mediaModel = proxy.getMediaModel();
		
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testAddTickerElementException() throws MediaDoesNotExsistException {
		assertEquals(media1, mediaModel.getMediaFileById(UUID.randomUUID()));
	}
	
	@Test
	public void testAddMediaFile() throws MediaDoesNotExsistException {
		UUID id1 = mediaModel.addMediaFile(media1);
		UUID id2 = mediaModel.addMediaFile(media2);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		assertEquals(media2, mediaModel.getMediaFileById(id2));
		
		UUID id3 = mediaModel.addMediaFile(media3);
		UUID id4 = mediaModel.addMediaFile(media4);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		assertEquals(media2, mediaModel.getMediaFileById(id2));
		assertEquals(media3, mediaModel.getMediaFileById(id3));
		assertEquals(media4, mediaModel.getMediaFileById(id4));
	}
	
	@Test
	public void testAddMediaFileCollision() throws MediaDoesNotExsistException {
		
		UUID id1 = mediaModel.addMediaFile(media1);
		UUID id2 = mediaModel.addMediaFile(media2);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		assertEquals(media2, mediaModel.getMediaFileById(id2));
		mediaModel.addMediaFile(media1);
		mediaModel.addMediaFile(media2);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		assertEquals(media2, mediaModel.getMediaFileById(id2));
		mediaModel.addMediaFile(media1);
		mediaModel.addMediaFile(media1);
		mediaModel.addMediaFile(media1);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		assertEquals(media2, mediaModel.getMediaFileById(id2));
	}
	
	
	@Test
	public void testRemoveMediaFile() throws MediaDoesNotExsistException {
		
		UUID id1 = mediaModel.addMediaFile(media1);
		UUID id2 = mediaModel.addMediaFile(media2);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		assertEquals(media2, mediaModel.getMediaFileById(id2));

		mediaModel.removeMediaFile(id1);
		assertEquals(media2, mediaModel.getMediaFileById(id2));
		mediaModel.addMediaFile(media1);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		assertEquals(media2, mediaModel.getMediaFileById(id2));

	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testRemoveMediaFile2() throws MediaDoesNotExsistException {
		
		UUID id1 = mediaModel.addMediaFile(media1);
		assertEquals(media1, mediaModel.getMediaFileById(id1));
		mediaModel.removeMediaFile(id1);
		mediaModel.getMediaFileById(id1);
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testQueueMediaFile1() throws MediaDoesNotExsistException {
		mediaModel.queue(UUID.randomUUID());
	}

	@Test
	public void testQueueMediaFile2() throws MediaDoesNotExsistException, MediaListsEmptyException {
		UUID id1 = mediaModel.addMediaFile(media1);
		UUID id2 = mediaModel.addMediaFile(media2);
		UUID id3 = mediaModel.addMediaFile(media3);
		UUID id4 = mediaModel.addMediaFile(media4);
		mediaModel.queue(id3);
		mediaModel.queue(id4);
		assertEquals(media3, mediaModel.getNext());
		mediaModel.queue(id2);
		mediaModel.queue(id1);
		assertEquals(media4, mediaModel.getNext());
		assertEquals(media2, mediaModel.getNext());
		mediaModel.queue(id3);
		assertEquals(media1, mediaModel.getNext());
		assertEquals(media3, mediaModel.getNext());
	}
	
	@Test 
	public void testQueueMediaFileChaining() throws MediaDoesNotExsistException, MediaListsEmptyException {
		UUID id1 = mediaModel.addMediaFile(media1);
		UUID id2 = mediaModel.addMediaFile(media2);
		mediaModel.queue(id1);
		assertEquals(media1, mediaModel.getNext());
		mediaModel.queue(id2);
		assertEquals(media2, mediaModel.getNext());
		mediaModel.queue(id1);
		mediaModel.queue(id1);
		mediaModel.queue(id1);
		mediaModel.queue(id1);
		assertEquals(media1, mediaModel.getNext());
		assertEquals(media1, mediaModel.getNext());
		assertEquals(media1, mediaModel.getNext());
		assertEquals(media1, mediaModel.getNext());
	}
	
	@Test(expected=MediaListsEmptyException.class)
	public void testGetNext1() throws MediaDoesNotExsistException, MediaListsEmptyException {
		mediaModel.getNext();
	}
	
	@Test
	public void testGetNext2() throws MediaDoesNotExsistException, MediaListsEmptyException {
		mediaModel.addMediaFile(media1);
		mediaModel.addMediaFile(media2);

		ServerMediaFile tmp = mediaModel.getNext();
		assertTrue(media1.equals(tmp) || media2.equals(tmp));
		tmp = mediaModel.getNext();
		assertTrue(media1.equals(tmp) || media2.equals(tmp));
		UUID id3 = mediaModel.addMediaFile(media3);
		tmp = mediaModel.getNext();
		assertTrue(media1.equals(tmp) || media2.equals(tmp) || media3.equals(tmp));
		mediaModel.queue(id3);
		assertEquals(media3, mediaModel.getNext());
		tmp = mediaModel.getNext();
		assertTrue(media1.equals(tmp) || media2.equals(tmp) || media3.equals(tmp));
	}
	
	@Test
	public void testGetNext3() throws MediaDoesNotExsistException, MediaListsEmptyException {
		UUID id1 = mediaModel.addMediaFile(media1);
		UUID id2 = mediaModel.addMediaFile(media2);
		mediaModel.addMediaFile(media3);
		ServerMediaFile tmp = mediaModel.getNext();
		assertTrue(media1.equals(tmp) || media2.equals(tmp) || media3.equals(tmp));	
		
		tmp = mediaModel.getNext();
		assertTrue(media1.equals(tmp) || media2.equals(tmp) || media3.equals(tmp));	
		
		tmp = mediaModel.getNext();
		assertTrue(media1.equals(tmp) || media2.equals(tmp) || media3.equals(tmp));
		
		mediaModel.removeMediaFile(id1);
		mediaModel.removeMediaFile(id2);
		assertEquals(media3, mediaModel.getNext());
		
		id2 = mediaModel.addMediaFile(media2);
		mediaModel.queue(id2);
		assertEquals(media2, mediaModel.getNext());
		
		tmp = mediaModel.getNext();
		assertTrue(media2.equals(tmp) || media3.equals(tmp));
		
		tmp = mediaModel.getNext();
		assertTrue(media2.equals(tmp) || media3.equals(tmp));
		
		mediaModel.removeMediaFile(id2);
		assertEquals(media3, mediaModel.getNext());
	}
}
