package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.server.datastructures.media.Theme;
import de.netprojectev.server.exceptions.PriorityDoesNotExistException;
import de.netprojectev.server.exceptions.PropertyDoesNotExistException;
import de.netprojectev.server.exceptions.ThemeDoesNotExistException;
import de.netprojectev.server.networking.MessageProxyServer;

public class PreferencesModelServer {
	
	private static Properties props = new Properties();
	private final MessageProxyServer proxy;
	private HashMap<UUID, Priority> prios;
	private HashMap<UUID, Theme> themes;
	
	public PreferencesModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		prios = new HashMap<>();
		themes = new HashMap<>();
	}
	
	public static String getPropertyByKey(String key) throws PropertyDoesNotExistException {
		if(props.getProperty(key) == null) {
			throw new PropertyDoesNotExistException("Property does not exist. Key: " + key);
		} else {
			return props.getProperty(key);
		}
	}
	
	public static void setProperty(String key, String value) {
		props.setProperty(key, value);
	}
	
	public UUID addPriority(Priority priority) {
		prios.put(priority.getId(), priority);
		return priority.getId();
	}
	
	public UUID addTheme(Theme theme) {
		themes.put(theme.getId(), theme);
		return theme.getId();
	}
	
	public void removePriority(UUID id) {
		prios.remove(id);
	}
	
	public void removeTheme(UUID id) {
		themes.remove(id);
	}
	
	public Priority getPriorityById(UUID id) throws PriorityDoesNotExistException {
		if(prios.get(id) == null) {
			throw new PriorityDoesNotExistException("Priority does not exist. ID: " + id);
		} else {
			return prios.get(id);
		}
	}
	
	public Theme getThemeById(UUID id) throws ThemeDoesNotExistException{
		if(themes.get(id) == null) {
			throw new ThemeDoesNotExistException("Theme does not exist. ID: " + id);
		} else {
			return themes.get(id);
		}
		
	}

}
