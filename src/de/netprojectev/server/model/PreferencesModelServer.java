package de.netprojectev.server.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.media.Theme;
import de.netprojectev.server.exceptions.PriorityDoesNotExistException;
import de.netprojectev.server.exceptions.ThemeDoesNotExistException;
import de.netprojectev.server.networking.AuthHandlerServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class PreferencesModelServer {
	
	private static final Logger log = LoggerBuilder.createLogger(PreferencesModelServer.class);
	
	private static Properties props;
	private final MessageProxyServer proxy;
	private HashMap<UUID, Priority> prios;
	private HashMap<UUID, Theme> themes;
	
	public PreferencesModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		prios = new HashMap<>();
		themes = new HashMap<>();
		try {
			loadProperties();
		} catch (IOException e) {
			log.warn("Error loading properties.", e);
		}
	}
	
	public static String getPropertyByKey(String key) {
		log.debug("Getting property: " + key);
		return props.getProperty(key);
		
	}
	
	public static void setProperty(String key, String value) {
		log.debug("Setting property: " + key + ", to: " + value);
		props.setProperty(key, value);
	}
	
	public UUID addPriority(Priority priority) {
		log.debug("Adding priority: " + priority);
		prios.put(priority.getId(), priority);
		return priority.getId();
	}
	
	public UUID addTheme(Theme theme) {
		log.debug("Adding theme: " + theme);
		themes.put(theme.getId(), theme);
		return theme.getId();
	}
	
	public void removePriority(UUID id) {
		log.debug("Removing priority: " + id);
		prios.remove(id);
	}
	
	public void removeTheme(UUID id) {
		log.debug("Removing theme: " + id);
		themes.remove(id);
	}
	
	public Priority getPriorityById(UUID id) throws PriorityDoesNotExistException {
		if(prios.get(id) == null) {
			throw new PriorityDoesNotExistException("Priority does not exist. ID: " + id);
		} else {
			log.debug("Getting priority: " + id);
			return prios.get(id);
		}
	}
	
	public Theme getThemeById(UUID id) throws ThemeDoesNotExistException{
		if(themes.get(id) == null) {
			throw new ThemeDoesNotExistException("Theme does not exist. ID: " + id);
		} else {
			log.debug("Getting theme: " + id);
			return themes.get(id);
		}
		
	}
	
	public static void saveProperties() throws IOException {
		log.info("Saving properties");
		Misc.savePropertiesToDisk(props, ConstantsServer.SAVE_PATH, ConstantsServer.SERVER_FILENAME_PROPERTIES);
	}
	
	public static void loadProperties() throws IOException {
		log.info("Loading properties");
		props = new Properties(generateDefaultsProperties());
		Properties propsLoaded = Misc.loadPropertiesFromDisk(ConstantsServer.SAVE_PATH, ConstantsServer.SERVER_FILENAME_PROPERTIES);
		props.putAll(propsLoaded);
	}
	
	private static Properties generateDefaultsProperties() {
		Properties defaults = new Properties();
		
		defaults.setProperty(ConstantsServer.PROP_SERVER_PW, ConstantsServer.DEFAULT_SERVER_PW);
		defaults.setProperty(ConstantsServer.PROP_TICKER_SEPERATOR, ConstantsServer.DEFAULT_TICKER_SEPERATOR);
		
		return defaults;
	}

}
