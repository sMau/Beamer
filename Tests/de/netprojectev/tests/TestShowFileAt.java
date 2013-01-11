package de.netprojectev.tests;

import java.util.Date;

import de.netprojectev.media.ImageFile;
import de.netprojectev.media.MediaFile;
import de.netprojectev.mediahandler.DisplayHandler;
import de.netprojectev.misc.Constants;

public class TestShowFileAt {
	
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
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
		
		Date date = new Date();
		date.setSeconds(date.getSeconds() + 30);
		
		displayHandler.showFileAt(testFile1, date);

	}

}
