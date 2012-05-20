package de.netprojectev.Misc;

import java.awt.GraphicsEnvironment;

import de.netprojectev.Media.Priority;

/**
 * 
 * Klasse für Konstanten.
 *
 */
public class Constants {

	//public static final Priority DEFAULT_PRIORITY = new Priority("default", 5); //echte Default Priority
	public static final Priority DEFAULT_PRIORITY = new Priority("default", 1); //for testing
	public static final int DEFAULT_AUTOMODUSTIMING_5MINUTES = 300000;
	
	public static final String SEPERATOR = " +++ ";
	
	public static final int DEFAULT_TICKER_SPEED = 50;
	
	public static final int DEFAULT_SCALE_WIDTH = 640;
	
	public static final String[] fontsFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	
	public static final String[] fontSizes = Misc.generateFontSizes();

	public static final String savePath = System.getProperty("user.home") + "/.beamermanager/";
	

}
