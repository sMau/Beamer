package de.netprojectev.server.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.datastructures.Priority;
import de.netprojectev.datastructures.Theme;
import de.netprojectev.datastructures.TickerElement;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.utils.HelperMethods;
import de.netprojectev.utils.LoggerBuilder;

public class PreferencesModelServer {

	public static UUID getDefaultPriority() {
		return defaultPriority;
	}

	public static String getPropertyByKey(String key) {
		log.fine("Getting property: " + key);
		return props.getProperty(key);

	}

	public static Properties getProps() {
		return props;
	}

	public static void loadProperties() {
		log.info("Loading properties");
		props = new Properties(HelperMethods.generateServerDefaultProps());
		Properties propsLoaded = new Properties();
		try {
			propsLoaded = HelperMethods.loadPropertiesFromDisk(ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_PROPERTIES);
		} catch (IOException e) {
			log.log(Level.WARNING, "Properties could not be loaded from disk. Using defaults.", e);
		}
		props.putAll(propsLoaded);
	}

	public static void saveProperties() throws IOException {
		log.info("Saving properties");
		HelperMethods.savePropertiesToDisk(props, ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_PROPERTIES);
	}

	public static void setProperty(String key, String value) throws IOException {
		log.fine("Setting property: " + key + ", to: " + value);
		props.setProperty(key, value);
        saveProperties();
	}

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(PreferencesModelServer.class);

	private final MessageProxyServer proxy;

	private final HashMap<UUID, Priority> prios;

	private final HashMap<UUID, Theme> themes;

	private static final int DEFAULT_PRIO_TIME = 1;

	private static UUID defaultPriority;

	private static Properties props;

	public PreferencesModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		this.prios = new HashMap<UUID, Priority>();
		this.themes = new HashMap<UUID, Theme>();
	}

	public UUID addPriority(Priority priority) {
		log.fine("Adding priority: " + priority);
		this.prios.put(priority.getId(), priority);
		return priority.getId();
	}

	public UUID addTheme(Theme theme) {
		log.fine("Adding theme: " + theme);
		this.themes.put(theme.getId(), theme);
		return theme.getId();
	}

	@SuppressWarnings("unchecked")
	public void deserializeAll() {

		loadProperties();

		HashMap<UUID, ServerMediaFile> allMedia = null;
		try {
			allMedia = (HashMap<UUID, ServerMediaFile>) HelperMethods.loadFromFile(ConstantsServer.FILENAME_MEDIAFILES, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			log.log(Level.WARNING, "Error during deserialization", e);
		} catch (IOException e) {
			log.log(Level.WARNING, "Error during deserialization", e);
		}
		HashMap<UUID, TickerElement> allTickerElements = null;
		try {
			allTickerElements = (HashMap<UUID, TickerElement>) HelperMethods.loadFromFile(ConstantsServer.FILENAME_LIVETICKER, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			log.log(Level.WARNING, "Error during deserialization", e);
		} catch (IOException e) {
			log.log(Level.WARNING, "Error during deserialization", e);
		}
		HashMap<UUID, Priority> allPriorities = null;
		try {
			allPriorities = (HashMap<UUID, Priority>) HelperMethods.loadFromFile(ConstantsServer.FILENAME_PRIORITIES, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			log.log(Level.WARNING, ("Error during deserialization"), e);
		} catch (IOException e) {
			log.log(Level.WARNING, "Error during deserialization", e);
		}
		HashMap<UUID, Theme> allThemes = null;
		try {
			allThemes = (HashMap<UUID, Theme>) HelperMethods.loadFromFile(ConstantsServer.FILENAME_THEMES, ConstantsServer.SAVE_PATH);
		} catch (ClassNotFoundException e) {
			log.log(Level.WARNING, "Error during deserialization", e);
		} catch (IOException e) {
			log.log(Level.WARNING, "Error during deserialization", e);
		}

		if (allMedia != null) {
			for (UUID id : allMedia.keySet()) {
				this.proxy.getMediaModel().addMediaFile(allMedia.get(id));
			}

		}

		if (allTickerElements != null) {
			for (UUID id : allTickerElements.keySet()) {
				this.proxy.getTickerModel().addTickerElement(allTickerElements.get(id));
			}

		}

		boolean defaulPrioExists = false;
		if (allPriorities != null) {
			for (UUID id : allPriorities.keySet()) {

                log.severe("Prio: " + allPriorities.get(id).toString());
				if (allPriorities.get(id).isDefaultPriority()) {
					defaulPrioExists = true;
				}
				addPriority(allPriorities.get(id));
			}

		}

		if (!defaulPrioExists) {
			Priority defaultPrio = new Priority("default", DEFAULT_PRIO_TIME);
			defaultPrio.setDefaultPriority(true);
			addPriority(defaultPrio);
			defaultPriority = defaultPrio.getId();
            try {
                serializePriorityDatabase();
            } catch (IOException e) {
                log.log(Level.WARNING, "Error writing priorities to disk.", e);
            }

        }

		if (allThemes != null) {
			for (UUID id : allThemes.keySet()) {
				addTheme(allThemes.get(id));
			}

		}

	}

	public Priority getPriorityById(UUID id) throws PriorityDoesNotExistException {
		if (this.prios.get(id) == null) {
			throw new PriorityDoesNotExistException("Priority does not exist. ID: " + id);
		} else {
			log.fine("Getting priority: " + id);
			return this.prios.get(id);
		}
	}

	public HashMap<UUID, Priority> getPrios() {
		return this.prios;
	}

	public Theme getThemeById(UUID id) throws ThemeDoesNotExistException {
		if (this.themes.get(id) == null) {
			throw new ThemeDoesNotExistException("Theme does not exist. ID: " + id);
		} else {
			log.fine("Getting theme: " + id);
			return this.themes.get(id);
		}

	}

	public HashMap<UUID, Theme> getThemes() {
		return this.themes;
	}

	public void removePriority(UUID id) throws IOException {
		log.fine("Removing priority: " + id);
		for (UUID key : this.proxy.getMediaModel().getAllMediaFiles().keySet()) {
			ServerMediaFile curFile = this.proxy.getMediaModel().getAllMediaFiles().get(key);
			if (curFile.getPriorityID().equals(id)) {
				curFile.setPriority(defaultPriority);
				this.proxy.broadcastMessage(new Message(OpCode.STC_EDIT_MEDIA_FILE_ACK, new ClientMediaFile(curFile)));
			}
		}
		this.prios.remove(id);
	}

	public void removeTheme(UUID id) {
		log.fine("Removing theme: " + id);
		this.themes.remove(id);
	}

	public void serializeAll() throws IOException {
		serializeMediaDatabase();
		serializeTickerDatabase();
		serializePriorityDatabase();
		serializeThemeDatabase();
		saveProperties();
	}

	public void serializeMediaDatabase() throws IOException {
		HelperMethods.saveToFile(this.proxy.getMediaModel().getAllMediaFiles(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_MEDIAFILES);
	}

	public void serializePriorityDatabase() throws IOException {
		HelperMethods.saveToFile(this.getPrios(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_PRIORITIES);
	}

	public void serializeThemeDatabase() throws IOException {
		HelperMethods.saveToFile(this.getThemes(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_THEMES);
	}

	public void serializeTickerDatabase() throws IOException {
		HelperMethods.saveToFile(this.proxy.getTickerModel().getElements(), ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_LIVETICKER);
	}

}
