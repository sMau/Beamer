package de.netprojectev.client;

import java.awt.GraphicsEnvironment;
import java.util.Date;

import de.netprojectev.misc.Misc;

public class ConstantsClient {

	/*
	 * PATH AND FOLDERS
	 */
	
	public static final String SAVE_PATH = System.getProperty("user.home") + "/.beamermanager_client/";	
	
	public static final String FILENAME_PROPERTIES = "properties.cfg";
	public static final String FILENAME_DEFAULT_PROPERTIES = "default_properties.cfg"; 
	public static final String FILENAME_LOGALL = "log_beamer_client_";
	
	/*
	 * DEFAULTS
	 */
		
	public static final int DEFAULT_PREVIEW_SCALE_WIDTH = 640;
	
	public static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP = 20;
	public static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT = 20;
	public static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE = 32;
	public static final String DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE = "Arial";
	public static final int DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR = -16777216;

	/*
	 * PROPERTY KEYS
	 */
	
	
	public static final String PROP_PREVIEW_SCALE_WIDTH = "general.previewWidth";
	
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP = "themeslideCreator.defaults.marginTop";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT = "themeslideCreator.defaults.marginLeft";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE = "themeslideCreator.defaults.fontSize";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE = "themeslideCreator.defaults.fontType";
	public static final String PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR = "themeslideCreator.defaults.fontColor";
	
	/*
	 * MISC
	 */
	public static final String[] FONT_SIZES = Misc.generateFontSizes();
	public static final String[] FONT_FAMILIES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	public static final String[] FONT_SIZES_COUNTDOWN = Misc.generateFontSizesCountdown();
	
	public static final Date TIMESTAMP = new Date();

}
