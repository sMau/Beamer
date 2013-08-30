package de.netprojectev.server.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.networking.MessageProxyServer;

public class PreferencesModelServer {

	private static final Logger log = LoggerBuilder.createLogger(PreferencesModelServer.class);

	private static final int DEFAULT_PRIO_TIME = 1;

	private static Properties props;
	private final MessageProxyServer proxy;
	private final HashMap<UUID, Priority> prios;
	private final HashMap<UUID, Theme> themes;

	private Priority defaultPriority;

	public PreferencesModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		prios = new HashMap<>();
		themes = new HashMap<>();
		props = new Properties(Misc.generateServerDefaultProps());

		try {
			loadProperties();
		} catch (IOException e) {
			log.warn("Error loading properties.", e);
		}
		Priority defaultPrio = new Priority("default", DEFAULT_PRIO_TIME);
		defaultPrio.setDefaultPriority(true);
		addPriority(defaultPrio);
		this.defaultPriority = defaultPrio;
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
		if (prios.get(id) == null) {
			throw new PriorityDoesNotExistException("Priority does not exist. ID: " + id);
		} else {
			log.debug("Getting priority: " + id);
			return prios.get(id);
		}
	}

	public Theme getThemeById(UUID id) throws ThemeDoesNotExistException {
		if (themes.get(id) == null) {
			throw new ThemeDoesNotExistException("Theme does not exist. ID: " + id);
		} else {
			log.debug("Getting theme: " + id);
			return themes.get(id);
		}

	}

	public static void saveProperties() throws IOException {
		log.info("Saving properties");
		Misc.savePropertiesToDisk(props, ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_PROPERTIES);
	}

	public static void loadProperties() throws IOException {
		log.info("Loading properties");
		props = new Properties(Misc.generateServerDefaultProps());
		Properties propsLoaded = Misc.loadPropertiesFromDisk(ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_PROPERTIES);
		props.putAll(propsLoaded);
	}

	public HashMap<UUID, Priority> getPrios() {
		return prios;
	}

	public HashMap<UUID, Theme> getThemes() {
		return themes;
	}

	public Priority getDefaultPriority() {
		return defaultPriority;
	}

	public void setDefaultPriority(Priority defaultPriority) {
		this.defaultPriority = defaultPriority;
	}

	public static Properties getProps() {
		return props;
	}

	public void serializeMediaDatabase() throws IOException {
		Misc.saveToFile(proxy.getMediaModel().getAllMediaFiles(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_MEDIAFILES);
	}

	public void serializeTickerDatabase() throws IOException {
		Misc.saveToFile(proxy.getTickerModel().getElements(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_LIVETICKER);
	}

	public void serializePriorityDatabase() throws IOException {
		Misc.saveToFile(this.getPrios(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_PRIORITIES);
	}

	public void serializeThemeDatabase() throws IOException {
		Misc.saveToFile(this.getThemes(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_THEMES);
	}

	public void serializeAll() throws IOException {
		serializeMediaDatabase();
		serializeTickerDatabase();
		serializePriorityDatabase();
		serializeThemeDatabase();
	}
	
	@SuppressWarnings("unchecked")
	public void deserializeAll() {
		
		HashMap<UUID, ServerMediaFile> allMedia = null;
		try {
			allMedia = (HashMap<UUID, ServerMediaFile>) Misc.loadFromFile(ConstantsServer.FILENAME_MEDIAFILES, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<UUID, ServerTickerElement> allTickerElements = null;
		try {
			allTickerElements = (HashMap<UUID, ServerTickerElement>) Misc.loadFromFile(ConstantsServer.FILENAME_LIVETICKER, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<UUID, Priority> allPriorities = null;
		try {
			allPriorities = (HashMap<UUID, Priority>) Misc.loadFromFile(ConstantsServer.FILENAME_PRIORITIES, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<UUID, Theme> allThemes = null;
		try {
			allThemes = (HashMap<UUID, Theme>) Misc.loadFromFile(ConstantsServer.FILENAME_THEMES, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(allMedia != null) {
			for (UUID id : allMedia.keySet()) {
				proxy.getMediaModel().addMediaFile(allMedia.get(id));
			}

		}
		
		if(allTickerElements != null) {
			for (UUID id : allTickerElements.keySet()) {
				proxy.getTickerModel().addTickerElement(allTickerElements.get(id));
			}
			
		}
		
		if(allPriorities != null) {
			for (UUID id : allPriorities.keySet()) {
				addPriority(allPriorities.get(id));
			}
				
		}
		
		if(allThemes != null) {
			for (UUID id : allThemes.keySet()) {
				addTheme(allThemes.get(id));
			}
	
		}
		
	}

}
