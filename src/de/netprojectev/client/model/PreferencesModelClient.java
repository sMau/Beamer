package de.netprojectev.client.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.ConstantsClient;
import de.netprojectev.client.gui.main.MainClientGUIWindow.UpdateAutoModeStateListener;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.Misc;

public class PreferencesModelClient {

	public interface PriorityListChangedListener {
		public void update();
	}
	
	public interface ThemeListChangedListener {
		public void update();
	}
	
	private static final Logger log = LoggerBuilder.createLogger(PreferencesModelClient.class);
	
	
	private final ClientMessageProxy proxy;
	private final HashMap<UUID, Theme> themes;
	private final HashMap<UUID, Priority> prios;
	
	private final ArrayList<UUID> allPrioritiesList;
	private final ArrayList<UUID> allThemesList;
	
	private static Properties props;
	
	private ThemeListChangedListener themeListChangeListener;
	private PriorityListChangedListener priorityChangedListener;
	private UpdateAutoModeStateListener autoModeStateListener;
	
	private boolean automode;
	private boolean fullscreen;
	private boolean liveTickerRunning;
	
	
	public PreferencesModelClient(ClientMessageProxy proxy) {

		this.proxy = proxy;
		this.themes = new HashMap<>();
		this.prios = new HashMap<>();
		this.allPrioritiesList = new ArrayList<>();
		this.allThemesList = new ArrayList<>();
	}
	
	public static String getPropertyByKey(String key) {
		log.debug("Getting property: " + key);
		return props.getProperty(key);
		
	}
	
	public static void setProperty(String key, String value) {
		log.debug("Setting property: " + key + ", to: " + value);
		props.setProperty(key, value);
	}
	
	public void themeAdded(Theme theme) {
		themes.put(theme.getId(), theme);
		if(!allThemesList.contains(theme.getId())) {
			allThemesList.add(theme.getId());
		}
		themeListChangeListener.update();
		log.debug("Theme added id: " + theme.getId());
	}
	
	public void themeRemoved(UUID theme) {
		themes.remove(theme);
		allThemesList.remove(theme);
		themeListChangeListener.update();
		log.debug("Theme removed, id: " + theme);
	}

	public void prioAdded(Priority prio) {
		prios.put(prio.getId(), prio);
		if(!allPrioritiesList.contains(prio.getId())) {
			allPrioritiesList.add(prio.getId());
		}
		priorityChangedListener.update();
		log.debug("Priority added id: " + prio.getId());
	}
	
	public void prioRemoved(UUID prio) {
		prios.remove(prio);
		allPrioritiesList.remove(prio);
		priorityChangedListener.update();
		log.debug("Priority removed, id: " + prio);
	}
	
	public int priorityCount() {
		return allPrioritiesList.size();
	}
	
	public int themeCount() {
		return allThemesList.size();
	}
	
	public Priority getPriorityAt(int pos) throws PriorityDoesNotExistException {
		return getPriorityByID(allPrioritiesList.get(pos));
	}
	
	public Theme getThemeAt(int pos) throws ThemeDoesNotExistException {
		return getThemeByID(allThemesList.get(pos));
	}
	
	public Priority getPriorityByID(UUID id) throws PriorityDoesNotExistException {
		Priority prio = prios.get(id);
		if(prio == null) {
			throw new PriorityDoesNotExistException("The priority is not available. ID: " + id);
		} else {
			return prio;
		}
		
	}
	
	public Theme getThemeByID(UUID id) throws ThemeDoesNotExistException {
		Theme theme = themes.get(id);
		if(theme == null) {
			throw new ThemeDoesNotExistException("The theme is not available. ID: " + id);
		} else {
			return theme;
		}
		
	}
	
	public void enableAutomode() {
		automode = true;
		autoModeStateListener.update();
		log.debug("automode toggled, new value value: " + automode);
	}
	
	public void disableAutomode() {
		automode = false;
		autoModeStateListener.update();
		log.debug("automode toggled, new value value: " + automode);
	}
	
	public void toggleFullscreen() {
		fullscreen = !fullscreen;
		log.debug("fullscreen toggled, new value value: " + fullscreen);

	}
	
	public void toggleLiveTickerRunning() {
		liveTickerRunning = !liveTickerRunning;
		log.debug("liveTickerRunning toggled, new value value: " + liveTickerRunning);

	}

	
	public boolean isAutomode() {
		return automode;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public boolean isLiveTickerRunning() {
		return liveTickerRunning;
	}

	public void setAutoModeStateListener(UpdateAutoModeStateListener autoModeStateListener) {
		this.autoModeStateListener = autoModeStateListener;
	}
	
	
	public static void saveProperties() throws IOException {
		log.info("Saving properties");
		Misc.savePropertiesToDisk(props, ConstantsClient.CLIENT_SAVE_PATH, ConstantsClient.CLIENT_FILENAME_PRIORITIES);
	}
	
	public static void loadProperties() throws IOException {
		log.info("Loading properties");
		props = new Properties(Misc.generateClientDefaultProps());
		Properties propsLoaded = Misc.loadPropertiesFromDisk(ConstantsClient.CLIENT_SAVE_PATH, ConstantsClient.CLIENT_FILENAME_PRIORITIES);
		props.putAll(propsLoaded);
	}

	public void setThemeListChangeListener(ThemeListChangedListener themeListChangeListener) {
		this.themeListChangeListener = themeListChangeListener;
	}

	public void setPriorityChangedListener(PriorityListChangedListener priorityChangedListener) {
		this.priorityChangedListener = priorityChangedListener;
	}
	
}
