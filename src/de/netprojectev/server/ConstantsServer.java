package de.netprojectev.server;

public class ConstantsServer {
	
	public static final String SERVER_FILENAME_MEDIAFILES = "mediafiles_server.brm";
	public static final String SERVER_FILENAME_PRIORITIES = "priorities_server.brm";
	public static final String SERVER_FILENAME_THEMES = "themes_server.brm";
	public static final String SERVER_FILENAME_LIVETICKER = "ticker_server.brm";
	public static final String SERVER_FILENAME_DEFAULT_PROPERTIES = "default_properties_server.cfg";
	public static final String SERVER_FILENAME_PROPERTIES = "properties_server.cfg";
	public static final String SERVER_FILENAME_LOGALL = "log_beamer_server_";
	
	public static final String SERVER_SAVE_PATH = System.getProperty("user.home") + "/.beamermanager_server/";
	public static final String SERVER_CACHE_FOLDER = "cache/";
	
	public static final String DEFAULT_SERVER_PW = "";	
	public static final String DEFAULT_SERVER_TIMEOUT = "30";	

	public static final String PROP_SERVER_PW = "server.general.password";
	public static final String PROP_SERVER_TIMEOUT = "server.general.timeout";

}
