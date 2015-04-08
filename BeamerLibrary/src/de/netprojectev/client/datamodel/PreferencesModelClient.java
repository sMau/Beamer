package de.netprojectev.client.datamodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.common.datastructures.Priority;
import de.netprojectev.common.datastructures.Theme;
import de.netprojectev.common.exceptions.PriorityDoesNotExistException;
import de.netprojectev.common.exceptions.ThemeDoesNotExistException;
import de.netprojectev.common.utils.LoggerBuilder;

public abstract class PreferencesModelClient {

	public interface FullscreenStateListener {
		void update();
	}

	public interface LiveTickerStateListener {
		void update();
	}

	public interface PriorityListChangedListener {
		void update();
	}

	public interface ThemeListChangedListener {
		void update();
	}

	public interface UpdateAutoModeStateListener {
		void update(boolean fullsync) throws PriorityDoesNotExistException;
	}

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(PreferencesModelClient.class);

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

	private UUID defaultPriority;

	protected Properties clientProperties;
	protected Properties serverProperties = new Properties();

	private String[] serverFonts;

	public PreferencesModelClient(MessageProxyClient proxy) {

		this.proxy = proxy;
		this.themes = new HashMap<>();
		this.prios = new HashMap<>();
		this.allPrioritiesList = new ArrayList<>();
		this.allThemesList = new ArrayList<>();
	}

	public void addPriorityChangedListener(PriorityListChangedListener priorityChangedListener) {
		if (!this.priorityChangedListeners.contains(priorityChangedListener)) {
			this.priorityChangedListeners.add(priorityChangedListener);
		}
	}

	public void disableAutomode() throws PriorityDoesNotExistException {
		this.automode = false;
		this.autoModeStateListener.update(false);
		log.fine("automode toggled, new value value: " + this.automode);
	}

	public void disableFullscreen() {
		this.fullscreen = false;
		this.fullscreenStateListener.update();
	}

	public void disableLiveTicker() {
		this.liveTickerRunning = false;
		this.liveTickerStateListener.update();
	}

	public void enableAutomode(boolean fullsync) throws PriorityDoesNotExistException {
		this.automode = true;
		this.autoModeStateListener.update(fullsync);
		log.fine("automode toggled, new value value: " + this.automode);
	}

	public void enableFullscreen() {
		this.fullscreen = true;
		this.fullscreenStateListener.update();
	}

	public void enableLiveTicker() {
		this.liveTickerRunning = true;
		this.liveTickerStateListener.update();
	}

	public String getClientPropertyByKey(String key) {
		return this.clientProperties.getProperty(key);
	}

	public UUID getDefaultPriority() {
		return this.defaultPriority;
	}

	public Priority getPriorityAt(int pos) throws PriorityDoesNotExistException {
		return getPriorityByID(this.allPrioritiesList.get(pos));
	}

	public Priority getPriorityByID(UUID id) throws PriorityDoesNotExistException {
		Priority prio = this.prios.get(id);
		if (prio == null) {
			throw new PriorityDoesNotExistException("The priority is not available. ID: " + id);
		} else {
			return prio;
		}

	}

	public MessageProxyClient getProxy() {
		return this.proxy;
	}

	public String[] getServerFonts() {
		return this.serverFonts;
	}

	public String getServerPropertyByKey(String key) {
		return this.serverProperties.getProperty(key);
	}

	public Theme getThemeAt(int pos) throws ThemeDoesNotExistException {
		return getThemeByID(this.allThemesList.get(pos));
	}

	public Theme getThemeByID(UUID id) throws ThemeDoesNotExistException {
		Theme theme = this.themes.get(id);
		if (theme == null) {
			throw new ThemeDoesNotExistException("The theme is not available. ID: " + id);
		} else {
			return theme;
		}

	}

	public void initServerProperties(Properties props) {
		this.serverProperties = props;
	}

	public boolean isAutomode() {
		return this.automode;
	}

	public boolean isFullscreen() {
		return this.fullscreen;
	}

	public boolean isLiveTickerRunning() {
		return this.liveTickerRunning;
	}

	public abstract void loadProperties() throws IOException;

	public void prioAdded(Priority prio) {
		this.prios.put(prio.getId(), prio);
		if (!this.allPrioritiesList.contains(prio.getId())) {
			this.allPrioritiesList.add(prio.getId());
		}
		if (prio.isDefaultPriority()) {
			this.defaultPriority = prio.getId();
		}
		updateAllPrioChangedListeners();
		log.fine("Priority added id: " + prio.getId());
	}

	public void prioRemoved(UUID prio) {
		this.prios.remove(prio);
		this.allPrioritiesList.remove(prio);
		updateAllPrioChangedListeners();
		log.fine("Priority removed, id: " + prio);
	}

	public Priority[] prioritiesAsArray() throws PriorityDoesNotExistException {
		UUID[] allIDs = this.allPrioritiesList.toArray(new UUID[this.allPrioritiesList.size()]);
		Priority[] allPrios = new Priority[allIDs.length];
		for (int i = 0; i < allIDs.length; i++) {
			allPrios[i] = getPriorityByID(allIDs[i]);
		}
		return allPrios;
	}

	public int priorityCount() {
		return this.allPrioritiesList.size();
	}

	public abstract void saveProperties() throws IOException;

	public void serverPropertyUpdated(String key, String value) {
		this.serverProperties.put(key, value);
	}

	public void setAutoModeStateListener(UpdateAutoModeStateListener autoModeStateListener) {
		this.autoModeStateListener = autoModeStateListener;
	}

	public void setClientProperty(String key, String value) {
		log.fine("Setting property: " + key + ", to: " + value);
		this.clientProperties.setProperty(key, value);
	}

	public void setFullscreenStateListener(FullscreenStateListener fullscreenStateListener) {
		this.fullscreenStateListener = fullscreenStateListener;
	}

	public void setLiveTickerStateListener(LiveTickerStateListener liveTickerStateListener) {
		this.liveTickerStateListener = liveTickerStateListener;
	}

	public void setServerFonts(String[] serverFonts) {
		this.serverFonts = serverFonts;
	}

	public void setThemeListChangeListener(ThemeListChangedListener themeListChangeListener) {
		this.themeListChangeListener = themeListChangeListener;
	}

	public void themeAdded(Theme theme) {
		this.themes.put(theme.getId(), theme);
		if (!this.allThemesList.contains(theme.getId())) {
			this.allThemesList.add(theme.getId());
		}
		this.themeListChangeListener.update();
		log.fine("Theme added id: " + theme.getId());
	}

	public int themeCount() {
		return this.allThemesList.size();
	}

	public void themeRemoved(UUID theme) {
		this.themes.remove(theme);
		this.allThemesList.remove(theme);
		this.themeListChangeListener.update();
		log.fine("Theme removed, id: " + theme);
	}

	public Theme[] themesAsArray() throws ThemeDoesNotExistException {
		UUID[] allIDs = this.allThemesList.toArray(new UUID[this.allThemesList.size()]);
		Theme[] allThemes = new Theme[allIDs.length];
		for (int i = 0; i < allIDs.length; i++) {
			allThemes[i] = getThemeByID(allIDs[i]);
		}
		return allThemes;
	}

	private void updateAllPrioChangedListeners() {
		for (int i = 0; i < this.priorityChangedListeners.size(); i++) {
			this.priorityChangedListeners.get(i).update();
		}
	}

}
