package de.netprojectev.server;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.util.Date;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.misc.Misc;

public class ConstantsServer {
	
	/*
	 * PATH AND FOLDERS
	 */
	
	public static final String SAVE_PATH = System.getProperty("user.home") + "/.beamermanager_server/";
	
	public static final String CACHE_PATH = "cache/";
	
	public static final String FILENAME_MEDIAFILES = "mediafiles.brm";
	public static final String FILENAME_PRIORITIES = "priorities.brm";
	public static final String FILENAME_THEMES = "themes.brm";
	public static final String FILENAME_LIVETICKER = "ticker.brm";
	public static final String FILENAME_DEFAULT_PROPERTIES = "default_properties.cfg";
	public static final String FILENAME_PROPERTIES = "properties.cfg";
	public static final String FILENAME_LOGALL = "log_beamer_server_";
	
	public static final String CACHE_PATH_IMAGES = "cache/images/";
	public static final String CACHE_PATH_VIDEOS = "cache/videos/";
	public static final String CACHE_PATH_THEMESLIDES = "cache/themeslides/";
	
	
	/*
	 * DEFAULTS
	 */
	
	public static final String DEFAULT_LOW_TICKER_SPEED = "40";
	public static final String DEFAULT_MEDIUM_TICKER_SPEED = "30";
	public static final String DEFAULT_HIGH_TICKER_SPEED = "20";
	
	public static final int DEFAULT_PREVIEW_SCAL_WIDTH = 640;
	
	public static final Priority NO_PRIORITY = new Priority("no priority", 0);
	
	public static final String DEFAULT_PW = "";	
	public static final String DEFAULT_HEARTBEAT_INTERVALL = "30";	
	
	public static final int DEFAULT_COUNTDOWN_FONTCOLOR = -16777216;
	public static final int DEFAULT_COUNTDOWN_FONTSIZE = 32;
	public static final String DEFAULT_COUNTDOWN_FONTTYPE = "Arial";
	
	public static final int DEFAULT_TICKER_SPEED = 40; //means 25 FPS
	public static final String DEFAULT_TICKER_SEPERATOR = " +++ ";
	public static final int DEFAULT_TICKER_FONTSIZE = 32;
	public static final String DEFAULT_TICKER_FONTTYPE = "Arial";
	public static final int DEFAULT_TICKER_FONTCOLOR = -16777216;
	public static final int DEFAULT_TICKER_BACKGROUND_COLOR = Color.WHITE.getRGB();
	public static final float DEFAULT_TICKER_BACKGROUND_ALPHA = 0.3f;

	
	/*
	 * PROPERTY KEYS
	 */
	
		
	public static final String PROP_TICKER_SPEED = "ticker.speed";
	public static final String PROP_TICKER_FONTSIZE = "ticker.fontSize";
	public static final String PROP_TICKER_FONTTYPE = "ticker.fontType";
	public static final String PROP_TICKER_FONTCOLOR = "ticker.fontColor";
	public static final String PROP_TICKER_SEPERATOR = "ticker.seperator";
	public static final String PROP_TICKER_BACKGROUND_COLOR = "ticker.backgroundColor";
	public static final String PROP_TICKER_BACKGROUND_ALPHA = "ticker.backgroundAlpha";
	
	public static final String PROP_PW = "password";
	public static final String PROP_HEARTBEAT_INTERVALL = "timeout";
	
	public static final String PROP_COUNTDOWN_FONTCOLOR = "countdown.fontColor";
	public static final String PROP_COUNTDOWN_FONTSIZE = "countdown.fontSize";
	public static final String PROP_COUNTDOWN_FONTTYPE = "countdown.fontType";
	
	/*
	 * MISC
	 */
	
	public static final int NUMBER_OF_WORKER_THREADS = 3;
	
	public static final String[] FONT_FAMILIES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	
	public static final Date TIMESTAMP = new Date();
}
