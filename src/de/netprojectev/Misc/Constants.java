package de.netprojectev.Misc;

import java.awt.GraphicsEnvironment;

import de.netprojectev.Media.Priority;

/**
 * 
 * Class for constants and globals not containing to a specific class, for easy and centralized editing.
 * @author samu
 */
public class Constants {

	/*
	 * Misc
	 */
	
	public static final String[] FONT_FAMILIES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	public static final String[] FONT_SIZES = Misc.generateFontSizes();

	
	/*
	 * Files and Paths
	 */
	
	public static final String FILENAME_MEDIAFILES = "mediafiles.brm";
	public static final String FILENAME_PRIORITIES = "priorities.brm";
	public static final String FILENAME_THEMES = "themes.brm";
	public static final String FILENAME_SETTINGS = "settings.brm";
	public static final String FILENAME_LIVETICKER = "ticker.brm";
	
	public static final String SAVE_PATH = System.getProperty("user.home") + "/.beamermanager/";
	public static final String FOLDER_THEMESLIDE_CACHE = "themeslidecache/";
	
	/*
	 * Defaults
	 */
	public static final int DEFAULT_TICKER_SPEED = 50;
	public static final int DEFAULT_PREVIEW_SCALE_WIDTH = 640;
	public static final int DEFAULT_AUTOMODUSTIMING_5MINUTES = 300000;

	public static final Priority DEFAULT_PRIORITY = new Priority("default", 3);
	
	public static final String DEFAULT_SEPERATOR = " +++ ";
	
	
	
	/*
	 * Property Keys
	 */
	
	public static final String PROP_PREVIEW_SCALE_WIDTH = "previewWidth";
	public static final String PROP_SCREEN_NUMBER_FULLSCREEN = "screenNumberFullscreen";
	public static final String PROP_TICKER_SPEED = "tickerSpeed";
	public static final String PROP_TICKER_FONTSIZE = "tickerFontSize";
	public static final String PROP_TICKER_FONTTYPE = "tickerFontType";
	public static final String PROP_TICKER_FONTCOLOR = "tickerFontColor";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE = "themeslideCreatorFontSize";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE = "themeslideCreatorFontType";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR = "themeslideCreatorFontColor";
	

}
