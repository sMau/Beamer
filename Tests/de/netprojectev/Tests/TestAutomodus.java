package de.netprojectev.Tests;

import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.MediaHandler.DisplayHandler;
import de.netprojectev.Misc.Constants;

public class TestAutomodus {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Automodus Test
		 */
		DisplayHandler displayHandler = DisplayHandler.getInstance();
		
		MediaFile[] testFiles = new MediaFile[3];
		MediaFile testFile0 = new ImageFile("0", "0", Constants.DEFAULT_PRIORITY);
		MediaFile testFile1 = new ImageFile("1", "1", Constants.DEFAULT_PRIORITY);
		MediaFile testFile2 = new ImageFile("2", "2", Constants.DEFAULT_PRIORITY);
		testFiles[0] = testFile0;
		testFiles[1] = testFile1;
		testFiles[2] = testFile2;

		
		displayHandler.add(testFiles);
		displayHandler.startAutomodus();

	}

}
