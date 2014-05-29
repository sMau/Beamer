package de.netprojectev.client.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.datastructures.Priority;
import de.netprojectev.datastructures.Theme;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.utils.LoggerBuilder;

public abstract class PreferencesModelClient {

	private static final Logger log = LoggerBuilder.createLogger(PreferencesModelClient.class);
	
	public interface UpdateAutoModeStateListener {
		public void update(boolean fullsync);
	}
	
	public interface PriorityListChangedListener {
		public void update();
	}

	public interface ThemeListChangedListener {
		public void update();
	}

	public interface LiveTickerStateListener {
		public void update();
	}
	
	public interface FullscreenStateListener {
		public void update();
	}
	
	private ThemeListChangedListener themeListChangeListener = new ThemeListChangedListener() {
		@Override
		public void update() {
		}
	};
	private ArrayList<PriorityListChangedListener> priorityChangedListeners = new ArrayList<PriorityListChangedListener>();
	private UpdateAutoModeStateListener autoModeStateListener = new UpdateAutoModeStateListener() {
		@Override
		public void update(boolean fullsync) {
		}
	};
	
	private LiveTickerStateListener liveTickerStateListener = new LiveTickerStateListener() {
		@Override
		public void update() {
		}
	};
	private FullscreenStateListener fullscreenStateListener = new FullscreenStateListener() {
		@Override
		public void update() {
		}
	};

	private boolean automode;
	private boolean fullscreen;
	private boolean liveTickerRunning;
	

	private final MessageProxyClient proxy;
	private final HashMap<UUID, Theme> themes;
	private final HashMap<UUID, Priority> prios;

	private final ArrayList<UUID> allPrioritiesList;
	private final ArrayList<UUID> allThemesList;

	private Priority defaultPriority;
	
	protected Properties clientProperties;
	protected Properties serverProperties;
	
	private String[] serverFonts;
	
	public PreferencesModelClient(MessageProxyClient proxy) {

		this.proxy = proxy;
		this.themes = new HashMap<UUID, Theme>();
		this.prios = new HashMap<UUID, Priority>();
		this.allPrioritiesList = new ArrayList<UUID>();
		this.allThemesList = new ArrayList<UUID>();
	}
	

	public abstract void saveProperties() throws IOException;

	public abstract void loadProperties() throws IOException;

	public String getClientPropertyByKey(String key) {
		return clientProperties.getProperty(key);
	}

	public void setClientProperty(String key, String value) {
		log.debug("Setting property: " + key + ", to: " + value);
		clientProperties.setProperty(key, value);
	}
	
	public void initServerProperties(Properties props) {
		serverProperties = props;
	}
	
	public String getServerPropertyByKey(String key) {
		return serverProperties.getProperty(key);
	}
	
	public void serverPropertyUpdated(String key, String value) {
		serverProperties.put(key, value);
	}

	public void themeAdded(Theme theme) {
		themes.put(theme.getId(), theme);
		if (!allThemesList.contains(theme.getId())) {
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
		if (!allPrioritiesList.contains(prio.getId())) {
			allPrioritiesList.add(prio.getId());
		}
		if(prio.isDefaultPriority()) {
			defaultPriority = prio;
		}
		updateAllPrioChangedListeners();
		log.debug("Priority added id: " + prio.getId());
	}

	public void prioRemoved(UUID prio) {
		prios.remove(prio);
		allPrioritiesList.remove(prio);
		updateAllPrioChangedListeners();
		log.debug("Priority removed, id: " + prio);
	}
	
	public Priority[] prioritiesAsArray() throws PriorityDoesNotExistException {
		UUID[] allIDs = allPrioritiesList.toArray(new UUID[allPrioritiesList.size()]);
		Priority[] allPrios = new Priority[allIDs.length];
		for(int i = 0 ; i < allIDs.length; i++) {
			allPrios[i] = getPriorityByID(allIDs[i]);
		}
		return allPrios;
	}

	public Theme[] themesAsArray() throws ThemeDoesNotExistException {
		UUID[] allIDs = allThemesList.toArray(new UUID[allThemesList.size()]);
		Theme[] allThemes = new Theme[allIDs.length];
		for(int i = 0 ; i < allIDs.length; i++) {
			allThemes[i] = getThemeByID(allIDs[i]);
		}
		return allThemes;
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
		if (prio == null) {
			throw new PriorityDoesNotExistException("The priority is not available. ID: " + id);
		} else {
			return prio;
		}

	}

	public Theme getThemeByID(UUID id) throws ThemeDoesNotExistException {
		Theme theme = themes.get(id);
		if (theme == null) {
			throw new ThemeDoesNotExistException("The theme is not available. ID: " + id);
		} else {
			return theme;
		}

	}

	public void enableAutomode(boolean fullsync) {
		automode = true;
		autoModeStateListener.update(fullsync);
		log.debug("automode toggled, new value value: " + automode);
	}

	public void disableAutomode() {
		automode = false;
		autoModeStateListener.update(false);
		log.debug("automode toggled, new value value: " + automode);
	}

	public void enableFullscreen() {
		fullscreen = true;
		fullscreenStateListener.update();
	}
	
	public void disableFullscreen() {
		fullscreen = false;
		fullscreenStateListener.update();
	}
	
	public void enableLiveTicker() {
		liveTickerRunning = true;
		liveTickerStateListener.update();
	}
	
	public void disableLiveTicker() {
		liveTickerRunning = false;
		liveTickerStateListener.update();
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

	public void setThemeListChangeListener(ThemeListChangedListener themeListChangeListener) {
		this.themeListChangeListener = themeListChangeListener;
	}

	public void addPriorityChangedListener(PriorityListChangedListener priorityChangedListener) {
		if(!priorityChangedListeners.contains(priorityChangedListener)) {
			this.priorityChangedListeners.add(priorityChangedListener);
		}
	}
	
	private void updateAllPrioChangedListeners() {
		for(int i = 0; i < priorityChangedListeners.size(); i++) {
			priorityChangedListeners.get(i).update();
		}
	}

	public Priority getDefaultPriority() {
		return defaultPriority;
	}

	public void setLiveTickerStateListener(LiveTickerStateListener liveTickerStateListener) {
		this.liveTickerStateListener = liveTickerStateListener;
	}

	public void setFullscreenStateListener(FullscreenStateListener fullscreenStateListener) {
		this.fullscreenStateListener = fullscreenStateListener;
	}

	public MessageProxyClient getProxy() {
		return proxy;
	}

	public String[] getServerFonts() {
		return serverFonts;
	}

	public void setServerFonts(String[] serverFonts) {
		this.serverFonts = serverFonts;
	}
	
	
}
