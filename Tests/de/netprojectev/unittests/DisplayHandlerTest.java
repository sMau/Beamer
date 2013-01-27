package de.netprojectev.unittests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.netprojectev.gui.manager.ManagerFrame;
import de.netprojectev.media.server.ServerMediaFile;
import de.netprojectev.media.server.VideoFile;
import de.netprojectev.mediahandler.DisplayHandler;
import de.netprojectev.mediahandler.MediaHandler;
import de.netprojectev.misc.Constants;

public class DisplayHandlerTest {

	
	DisplayHandler displayHandler;
	ServerMediaFile[] testFiles;
	ServerMediaFile[] testFiles2;
	ServerMediaFile[] testFiles3;
	ServerMediaFile[] testFiles4;
	ServerMediaFile[] testFiles5;
	ServerMediaFile testFile0;
	ServerMediaFile testFile1;
	ServerMediaFile testFile2;
	ServerMediaFile testFile3;
	ServerMediaFile testFile4;
	ServerMediaFile testFile5;
	
	@Before
	public void setUp() {
		
	
		displayHandler = DisplayHandler.getInstance();
		testFiles = new ServerMediaFile[3];
		testFiles2 = new ServerMediaFile[1];
		testFiles3 = new ServerMediaFile[2];
		testFiles4 = new ServerMediaFile[3];
		testFiles5 = new ServerMediaFile[2];

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
		
		
		displayHandler.add(testFiles);
		
		
	}
	

	@Test
	public void testStartShuffle() {
		
		displayHandler.add(testFiles4);
		
		ServerMediaFile[] tmp =  new ServerMediaFile[6];
		tmp = displayHandler.getPlayingFiles().toArray(new ServerMediaFile[0]);
		
		displayHandler.startShuffle();
		assertEquals(true, displayHandler.getIsShufflingEnabled());
		
		assertEquals(6, displayHandler.getPlayingFiles().size());
		/*
		 * !!!!!
		 * !!!!!
		 * !!!!!
		 * Schlägt vielleicht bei einem von tausend mal fehl, da der shuffle das original liefert
		 */
		assertFalse(tmp.equals( displayHandler.getPlayingFiles().toArray(new ServerMediaFile[0])));
		
	}
	

	@Test
	public void testStopShuffle() {
		
		displayHandler.add(testFiles4);
		ServerMediaFile[] tmp =  new ServerMediaFile[6];
		tmp = displayHandler.getPlayingFiles().toArray(new ServerMediaFile[0]);
		MediaHandler.getInstance().add(tmp);
		displayHandler.startShuffle();
		assertEquals(true, displayHandler.getIsShufflingEnabled());
		displayHandler.stopShuffle();
		assertEquals(false, displayHandler.getIsShufflingEnabled());
		assertEquals(6, displayHandler.getPlayingFiles().size());
		assertArrayEquals(tmp, displayHandler.getPlayingFiles().toArray(new ServerMediaFile[0]));

	}	
	
	
	@Test
	public void testShow() {
		displayHandler.show(testFile0);
		assertEquals(testFile0, displayHandler.getCurrentMediaFile());
		displayHandler.show(testFile2);
		assertEquals(testFile2, displayHandler.getCurrentMediaFile());

	}

	@Test
	public void testShowNext() {
		displayHandler.show(testFile1);
		assertEquals(testFile1, displayHandler.getCurrentMediaFile());
		displayHandler.showNext();
		assertEquals(testFile2, displayHandler.getCurrentMediaFile());

		
	}
	
	@Test
	public void testShowNext3() {
		displayHandler.show(testFile2);
		assertEquals(testFile2, displayHandler.getCurrentMediaFile());
		displayHandler.showNext();
		assertEquals(testFile0, displayHandler.getCurrentMediaFile());
		
	}

	@Test
	public void testShowBefore() {
		displayHandler.show(testFile2);
		assertEquals(testFile2, displayHandler.getCurrentMediaFile());
		displayHandler.showPrevious();
		assertEquals(testFile1, displayHandler.getCurrentMediaFile());
	}
	

	
	@Test
	public void testShowBefore3() {
		displayHandler.show(testFile0);
		assertEquals(testFile0, displayHandler.getCurrentMediaFile());
		displayHandler.showPrevious();
		assertEquals(testFile2, displayHandler.getCurrentMediaFile());
	}
	
	@Test
	public void testAdd() {
		
		
		displayHandler.add(testFiles);
		assertEquals(testFiles.length, displayHandler.getPlayingFiles().size());
	
	}
	
	@Test
	public void testAdd2() {
		
		
		displayHandler.add(testFiles2);
		assertEquals(testFile3, displayHandler.getPlayingFiles().get(3));
	
	}
	
	@Test
	public void testRemove() {

		displayHandler.remove(testFiles3);

		assertEquals(1, displayHandler.getPlayingFiles().size());
		assertEquals(testFile1, displayHandler.getPlayingFiles().getLast());

	}

	@Test
	public void testRemove2() {
	
		displayHandler.remove(testFiles3);
		
		assertEquals(testFile1, displayHandler.getPlayingFiles().getFirst());

	}
	
	
	/*
	 * All up and down tests in Display Handler are depracted, as it now only depends on mediahandler
	 */
	@Test
	@Ignore
	public void testUp() {
	
		displayHandler.add(testFiles2);
		displayHandler.up(testFiles2);
		
		assertEquals(testFile3, displayHandler.getPlayingFiles().get(2));
		assertEquals(4, displayHandler.getPlayingFiles().size());
		
	}
	
	@Test
	@Ignore
	public void testUp2() {
		
		displayHandler.add(testFiles4);
		displayHandler.up(testFiles5);
		
		assertEquals(testFile2, displayHandler.getPlayingFiles().get(1));
		assertEquals(testFile4, displayHandler.getPlayingFiles().get(2));
		assertEquals(6, displayHandler.getPlayingFiles().size());
		
	}
	
	@Test
	@Ignore
	public void testUp3() {
		
		displayHandler.setIsShufflingEnabled(true);
		
		displayHandler.add(testFiles4);
		
		LinkedList<ServerMediaFile> tmp = displayHandler.getPlayingFiles();
		
		displayHandler.up(testFiles5);
		
		assertEquals(tmp, displayHandler.getPlayingFiles());
		
	}

	@Test
	@Ignore
	public void testDown() {
		
		displayHandler.add(testFiles4);
		displayHandler.down(testFiles5);
		
		assertEquals(testFile2, displayHandler.getPlayingFiles().get(3));
		assertEquals(testFile4, displayHandler.getPlayingFiles().get(4));
		assertEquals(6, displayHandler.getPlayingFiles().size());
		
	}
	

	@Test
	@Ignore
	public void testDown2() {
	
		displayHandler.add(testFiles4);
		displayHandler.down(testFiles2);
		
		assertEquals(testFile3, displayHandler.getPlayingFiles().get(4));
		assertEquals(6, displayHandler.getPlayingFiles().size());
		
	}
	
	@Test
	@Ignore
	public void testDown3() {
		
		displayHandler.setIsShufflingEnabled(true);
		
		displayHandler.add(testFiles4);
		
		LinkedList<ServerMediaFile> tmp = displayHandler.getPlayingFiles();
		
		displayHandler.down(testFiles5);
		
		assertEquals(tmp, displayHandler.getPlayingFiles());
		
	}

}
