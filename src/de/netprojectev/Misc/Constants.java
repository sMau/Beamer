package de.netprojectev.Misc;

import java.awt.GraphicsEnvironment;

import de.netprojectev.Media.Priority;

/**
 * 
 * Class for constants and globals not containing to a specific class, for easy and centralized editing.
 * @author samu
 */
public class Constants {

	//public static final Priority DEFAULT_PRIORITY = new Priority("default", 3); //echte Default Priority
	public static final Priority DEFAULT_PRIORITY = new Priority("default", 1); //for testing
	public static final int DEFAULT_AUTOMODUSTIMING_5MINUTES = 300000;
	
	public static final String SEPERATOR = " +++ ";
	
	public static final int DEFAULT_TICKER_SPEED = 50;
	
	public static final int DEFAULT_PREVIEW_SCALE_WIDTH = 640;
	
	public static final String[] FONT_FAMILIES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	
	public static final String[] FONT_SIZES = Misc.generateFontSizes();

	public static final String SAVE_PATH = System.getProperty("user.home") + "/.beamermanager/";
	
	public static final String FILENAME_MEDIAFILES = "mediafiles.brm";
	public static final String FILENAME_PRIORITIES = "priorities.brm";
	public static final String FILENAME_THEMES = "themes.brm";
	public static final String FILENAME_SETTINGS = "settings.brm";
	public static final String FILENAME_LIVETICKER = "ticker.brm";

}
