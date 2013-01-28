package de.netprojectev.tests;

import java.util.Date;

import de.netprojectev.misc.Constants;
import de.netprojectev.server.datastructures.media.ImageFile;
import de.netprojectev.server.datastructures.media.ServerMediaFile;
import de.netprojectev.server.model.DisplayMediaModel;

public class TestShowFileAt {
	
	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		/*
		 * Automodus Test
		 */
		DisplayMediaModel displayHandler = DisplayMediaModel.getInstance();
		
		ServerMediaFile[] testFiles = new ServerMediaFile[3];
		ServerMediaFile testFile0 = new ImageFile("0", "0", Constants.DEFAULT_PRIORITY);
		ServerMediaFile testFile1 = new ImageFile("1", "1", Constants.DEFAULT_PRIORITY);
		ServerMediaFile testFile2 = new ImageFile("2", "2", Constants.DEFAULT_PRIORITY);
		testFiles[0] = testFile0;
		testFiles[1] = testFile1;
		testFiles[2] = testFile2;
		
		displayHandler.add(testFiles);
		
		Date date = new Date();
		date.setSeconds(date.getSeconds() + 30);
		
		displayHandler.showFileAt(testFile1, date);

	}

}