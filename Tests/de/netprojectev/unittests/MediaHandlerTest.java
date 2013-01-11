package de.netprojectev.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.netprojectev.media.MediaFile;
import de.netprojectev.media.VideoFile;
import de.netprojectev.mediahandler.MediaHandler;

public class MediaHandlerTest {

	MediaHandler mediaHandler;
	MediaFile[] testFiles;
	MediaFile[] testFiles2;
	MediaFile[] testFiles3;
	MediaFile[] testFiles4;
	MediaFile[] testFiles5;
	MediaFile testFile0;
	MediaFile testFile1;
	MediaFile testFile2;
	MediaFile testFile3;
	MediaFile testFile4;
	MediaFile testFile5;

	@Before
	public void setUp() {

		MediaHandler.reset();
		
		mediaHandler = MediaHandler.getInstance();
		testFiles = new MediaFile[3];
		testFiles2 = new MediaFile[1];
		testFiles3 = new MediaFile[2];
		testFiles4 = new MediaFile[3];
		testFiles5 = new MediaFile[2];

		testFile0 = new VideoFile("0", "0");
		testFile1 = new VideoFile("1", "1");
		testFile2 = new VideoFile("2", "2");
		testFile3 = new VideoFile("3", "3");
		testFile4 = new VideoFile("4", "4");
		testFile5 = new VideoFile("5", "5");

		testFiles[0] = this.testFile0;
		testFiles[1] = this.testFile1;
		testFiles[2] = this.testFile2;

		testFiles2[0] = this.testFile3;

		testFiles3[0] = this.testFile0;
		testFiles3[1] = this.testFile2;
		
		testFiles4[0] = this.testFile3;
		testFiles4[1] = this.testFile4;
		testFiles4[2] = this.testFile5;

		testFiles5[0] = this.testFile2;
		testFiles5[1] = this.testFile4;
		
		
		mediaHandler.add(this.testFiles);
		

	}

	@Test
	public void testAdd() {
		
		assertEquals(testFiles.length, mediaHandler.getMediaFiles().size());		
		
	}

	@Test
	public void testAdd2() {

		this.mediaHandler.add(testFiles2);
		assertEquals(4, mediaHandler.getMediaFiles().size());
		assertEquals(testFile3, mediaHandler.getMediaFiles().getLast());

	}


	@Test
	public void testRemove() {

		this.mediaHandler.remove(testFiles3, true);

		assertEquals(1, mediaHandler.getMediaFiles().size());
		assertEquals(testFile1, mediaHandler.getMediaFiles().getLast());

	}

	@Test
	public void testRemove2() {
	
		this.mediaHandler.remove(testFiles3, true);
		
		assertEquals(testFile1, mediaHandler.getMediaFiles().getFirst());

	}

	@Test
	public void testUp() {
	
		mediaHandler.add(testFiles2);
		mediaHandler.up(testFiles2);
		
		assertEquals(testFile3, mediaHandler.getMediaFiles().get(2));
		assertEquals(4, mediaHandler.getMediaFiles().size());
		
	}
	
	@Test
	public void testUp2() {
		
		mediaHandler.add(testFiles4);
		mediaHandler.up(testFiles5);
		
		assertEquals(testFile2, mediaHandler.getMediaFiles().get(1));
		assertEquals(testFile4, mediaHandler.getMediaFiles().get(2));
		assertEquals(6, mediaHandler.getMediaFiles().size());
		
	}

	@Test
	public void testDown() {
	
		mediaHandler.add(testFiles4);
		mediaHandler.down(testFiles2);
		
		assertEquals(testFile3, mediaHandler.getMediaFiles().get(4));
		assertEquals(6, mediaHandler.getMediaFiles().size());
		
	}
	
	
	
	


}
