package de.netprojectev.Misc;

import java.awt.GraphicsEnvironment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;

import de.netprojectev.Media.Priority;

/**
 * 
 * Class for constants and globals not containing to a specific class, for easy and centralized editing.
 * @author samu
 */
public class Constants {

	
	/*
	 * Files and Paths
	 */
	
	public static final String FILENAME_MEDIAFILES = "mediafiles.brm";
	public static final String FILENAME_PRIORITIES = "priorities.brm";
	public static final String FILENAME_THEMES = "themes.brm";
	public static final String FILENAME_LIVETICKER = "ticker.brm";
	public static final String FILENAME_PROPERTIES = "properties.txt";
	public static final String FILENAME_LOGALL = "log_beamer_";
	
	public static final String SAVE_PATH = System.getProperty("user.home") + "/.beamermanager/";
	public static final String FOLDER_THEMESLIDE_CACHE = "themeslidecache/";
	
	/*
	 * Misc
	 */
	
	public static final String[] FONT_FAMILIES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	public static final String[] FONT_SIZES = Misc.generateFontSizes();
		
	public static final Date TIMESTAMP = new Date(); 
	
	/*
	 * Defaults
	 */
	
	public static final Priority DEFAULT_PRIORITY = new Priority("default", 3);

	
	protected static final int DEFAULT_PREVIEW_SCALE_WIDTH = 640;
	
	protected static final int DEFAULT_SCREEN_NUMBER_FULLSCREEN = 1;
	
	protected static final int DEFAULT_TICKER_SPEED = 50;
	protected static final String DEFAULT_TICKER_SEPERATOR = " +++ ";
	protected static final int DEFAULT_TICKER_FONTSIZE = 32;
	protected static final String DEFAULT_TICKER_FONTTYPE = "Arial";
	protected static final int DEFAULT_TICKER_FONTCOLOR = -16777216;
	
	protected static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP = 20;
	protected static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT = 20;
	protected static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE = 32;
	protected static final String DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE = "Arial";
	protected static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR = -16777216;
	
	protected static final int DEFAULT_COUNTDOWN_FONTCOLOR = -16777216;
	protected static final int DEFAULT_COUNTDOWN_FONTSIZE = 32;
	protected static final String DEFAULT_COUNTDOWN_FONTTYPE = "Arial";

	
	/*
	 * Property Keys
	 */
	
	public static final String PROP_PREVIEW_SCALE_WIDTH = "general.previewWidth";
	
	public static final String PROP_SCREEN_NUMBER_FULLSCREEN = "general.screenNumberFullscreen";
	
	public static final String PROP_TICKER_SPEED = "ticker.speed";
	public static final String PROP_TICKER_FONTSIZE = "ticker.fontSize";
	public static final String PROP_TICKER_FONTTYPE = "ticker.fontType";
	public static final String PROP_TICKER_FONTCOLOR = "ticker.fontColor";
	public static final String PROP_TICKER_SEPERATOR = "ticker.seperator";
	
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP = "themeslideCreator.defaults.marginTop";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT = "themeslideCreator.defaults.marginLeft";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE = "themeslideCreator.defaults.fontSize";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE = "themeslideCreator.defaults.fontType";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR = "themeslideCreator.defaults.fontColor";
	
	public static final String PROP_COUNTDOWN_FONTCOLOR = "countdown.fontColor";
	public static final String PROP_COUNTDOWN_FONTSIZE = "countdown.fontSize";
	public static final String PROP_COUNTDOWN_FONTTYPE = "countdown.fontType";

}
