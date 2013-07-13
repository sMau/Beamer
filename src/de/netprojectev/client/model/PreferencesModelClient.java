package de.netprojectev.client.model;

import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.misc.LoggerBuilder;

public class PreferencesModelClient {

	private static final Logger log = LoggerBuilder.createLogger(PreferencesModelClient.class);
	
	
	private final ClientMessageProxy proxy;
	private final HashMap<UUID, Theme> themes;
	private final HashMap<UUID, Priority> prios;
	
	private boolean automode;
	private boolean fullscreen;
	private boolean liveTickerRunning;
	
	
	public PreferencesModelClient(ClientMessageProxy proxy) {

		this.proxy = proxy;
		this.themes = new HashMap<>();
		this.prios = new HashMap<>();
	}
	
	public void themeAdded(Theme theme) {
		themes.put(theme.getId(), theme);
		log.debug("Theme added id: " + theme.getId());
	}
	
	public void themeRemoved(UUID theme) {
		themes.remove(theme);
		log.debug("Theme removed, id: " + theme);
	}

	public void prioAdded(Priority prio) {
		prios.put(prio.getId(), prio);
		log.debug("Priority added id: " + prio.getId());
	}
	
	public void prioRemoved(UUID prio) {
		prios.remove(prio);
		log.debug("Priority removed, id: " + prio);
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
	
	public void toggleAutomode() {
		automode = !automode;
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
	
}
