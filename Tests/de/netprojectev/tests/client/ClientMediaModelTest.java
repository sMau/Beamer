package de.netprojectev.tests.client;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.client.Client;
import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.networking.LoginData;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientMediaModelTest {
	
	
	private MediaModelClient mediaModel;
	
	
	private ClientMediaFile m1 = new ClientMediaFile(new VideoFile("Vid1", new File("/home/samu")));
	private ClientMediaFile m2 = new ClientMediaFile(new VideoFile("Vid2", new File("/home/samu")));
	private ClientMediaFile m3 = new ClientMediaFile(new VideoFile("Vid3", new File("/home/samu")));
	private ClientMediaFile m4 = new ClientMediaFile(new VideoFile("Vid4", new File("/home/samu")));
	
	@Before
	public void setUp() {
		Client client = new Client("", 0, new LoginData("", ""));
		
		mediaModel = client.getProxy().getMediaModel();
	}
	
	@Test
	public void testAddMediaFile() throws MediaDoesNotExsistException {
		mediaModel.addMediaFile(m1);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		mediaModel.addMediaFile(m2);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		assertEquals(m2, mediaModel.getMediaFileById(m2.getId()));
		mediaModel.addMediaFile(m3);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		assertEquals(m2, mediaModel.getMediaFileById(m2.getId()));
		assertEquals(m3, mediaModel.getMediaFileById(m3.getId()));
		mediaModel.addMediaFile(m4);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		assertEquals(m2, mediaModel.getMediaFileById(m2.getId()));
		assertEquals(m3, mediaModel.getMediaFileById(m3.getId()));
		assertEquals(m4, mediaModel.getMediaFileById(m4.getId()));
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testRemoveMediaFile1() throws MediaDoesNotExsistException {
		mediaModel.removeMediaFile(UUID.randomUUID());
	}

	@Test
	public void testRemoveMediaFile2() throws MediaDoesNotExsistException {
		mediaModel.addMediaFile(m1);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		mediaModel.addMediaFile(m2);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		assertEquals(m2, mediaModel.getMediaFileById(m2.getId()));
		mediaModel.addMediaFile(m3);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		assertEquals(m2, mediaModel.getMediaFileById(m2.getId()));
		assertEquals(m3, mediaModel.getMediaFileById(m3.getId()));
		
		mediaModel.removeMediaFile(m2.getId());
		
		mediaModel.addMediaFile(m4);
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		assertEquals(m3, mediaModel.getMediaFileById(m3.getId()));
		assertEquals(m4, mediaModel.getMediaFileById(m4.getId()));
		
		mediaModel.removeMediaFile(m3.getId());
		
		assertEquals(m1, mediaModel.getMediaFileById(m1.getId()));
		assertEquals(m4, mediaModel.getMediaFileById(m4.getId()));		
		
		mediaModel.removeMediaFile(m1.getId());
		assertEquals(1, mediaModel.getAllMedia().keySet().size());
		assertEquals(m4, mediaModel.getMediaFileById(m4.getId()));
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testQueue1() throws MediaDoesNotExsistException{
		mediaModel.queueMediaFile(UUID.randomUUID());
	}
	
	@Test
	public void testQueue2() throws MediaDoesNotExsistException{
		mediaModel.addMediaFile(m1);
		mediaModel.addMediaFile(m2);
		
		
		mediaModel.queueMediaFile(m1.getId());
		assertEquals(1, mediaModel.getCustomQueue().size());
		mediaModel.queueMediaFile(m2.getId());
		assertEquals(2, mediaModel.getCustomQueue().size());
		mediaModel.addMediaFile(m3);
		mediaModel.queueMediaFile(m3.getId());
		assertEquals(3, mediaModel.getCustomQueue().size());
	}
	
	@Test
	public void testDequeue() throws MediaDoesNotExsistException {
		mediaModel.dequeueFirstMediaFile();
		assertEquals(0, mediaModel.getCustomQueue().size());
		
		mediaModel.addMediaFile(m1);
		mediaModel.addMediaFile(m2);
		
		mediaModel.queueMediaFile(m1.getId());
		assertEquals(1, mediaModel.getCustomQueue().size());
		mediaModel.queueMediaFile(m2.getId());
		assertEquals(2, mediaModel.getCustomQueue().size());
		
		mediaModel.dequeueFirstMediaFile();
		assertEquals(1, mediaModel.getCustomQueue().size());
		
		mediaModel.dequeueFirstMediaFile();
		assertEquals(0, mediaModel.getCustomQueue().size());
		
		mediaModel.addMediaFile(m3);
		mediaModel.addMediaFile(m4);
		mediaModel.queueMediaFile(m1.getId());
		mediaModel.queueMediaFile(m2.getId());
		mediaModel.queueMediaFile(m2.getId());
		mediaModel.queueMediaFile(m1.getId());
		
		assertEquals(4, mediaModel.getCustomQueue().size());
	}
	
	@Test(expected=MediaDoesNotExsistException.class)
	public void testGetById() throws MediaDoesNotExsistException {
		mediaModel.getMediaFileById(UUID.randomUUID());
	}
}
