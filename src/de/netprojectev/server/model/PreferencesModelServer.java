package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import javax.swing.ImageIcon;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;

public class PreferencesModelServer {
	
	private static Properties props = new Properties();
	private static HashMap<UUID, Priority> prios = new HashMap<>();
	private static HashMap<UUID, Theme> themes = new HashMap<>();
	
	private PreferencesModelServer() {
		
	}
	
	public static String getPropertyByKey(String key) {
		// TODO
		return null;
	}
	
	public static void setProperty(String key, String value) {
		// TODO
		
	}
	
	public static Priority addPriority(String name, int duration) {
		// TODO
		return null;
	}
	
	public static Theme addTheme(String name, ImageIcon background) {
		// TODO
		return null;
	}
	
	public static void removePriority(UUID id) {
		// TODO
	}
	
	public static void removeTheme(UUID id) {
		// TODO
	}
	
	public static Priority getPriorityById(UUID id) {
		// TODO
		return null;
	}
	
	public static Priority getThemeById(UUID id) {
		// TODO
		return null;
	}

}
