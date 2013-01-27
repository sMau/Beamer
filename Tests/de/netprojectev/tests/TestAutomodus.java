package de.netprojectev.tests;

import de.netprojectev.media.server.ImageFile;
import de.netprojectev.media.server.ServerMediaFile;
import de.netprojectev.mediahandler.DisplayHandler;
import de.netprojectev.misc.Constants;

public class TestAutomodus {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Automodus Test
		 */
		DisplayHandler displayHandler = DisplayHandler.getInstance();
		
		ServerMediaFile[] testFiles = new ServerMediaFile[3];
		ServerMediaFile testFile0 = new ImageFile("0", "0", Constants.DEFAULT_PRIORITY);
		ServerMediaFile testFile1 = new ImageFile("1", "1", Constants.DEFAULT_PRIORITY);
		ServerMediaFile testFile2 = new ImageFile("2", "2", Constants.DEFAULT_PRIORITY);
		testFiles[0] = testFile0;
		testFiles[1] = testFile1;
		testFiles[2] = testFile2;

		
		displayHandler.add(testFiles);
		displayHandler.startAutomodus();

	}

}
