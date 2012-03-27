package de.netprojectev.Misc;

import java.util.LinkedList;

import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;

public class PreferencesHandler {
	
	private static PreferencesHandler instance;
	
	private LinkedList<Priority> listOfPriorities;
	private LinkedList<Theme> listOfThemes;
	
	private Boolean loadOnStart;
	private Boolean saveOnExit;
	
	private int tickerSpeed;
	
	private PreferencesHandler() {
		
		this.listOfPriorities = new LinkedList<Priority>();
		this.listOfThemes = new LinkedList<Theme>();
		
		this.loadOnStart = false;
		this.saveOnExit = false;
		
		this.tickerSpeed = Constants.DEFAULT_TICKER_SPEED;
		
	}
	
	public static PreferencesHandler getInstance() {
		
		if(instance == null) {
			instance = new PreferencesHandler();
		}
		return instance;
	}
	
	public void addPriority(Priority prio) {
		if(!listOfPriorities.contains(prio)) {
			listOfPriorities.add(prio);
		}
		
	}
	
	public void addTheme(Theme theme) {
		if(!listOfThemes.contains(theme)) {
			listOfThemes.add(theme);
		}
	}
	
	public void removePriorities(Priority[] prios) {
		for (int i = 0; i < prios.length; i++) {
			listOfPriorities.remove(prios[i]);
		}
	}
	
	public void removeThemes(Theme[] themes) {
		for (int i = 0; i < themes.length; i++) {
			listOfThemes.remove(themes[i]);
		}
	}
	

	public LinkedList<Priority> getListOfPriorities() {
		return listOfPriorities;
	}

	public void setListOfPriorities(LinkedList<Priority> listOfPriorities) {
		this.listOfPriorities = listOfPriorities;
	}

	public LinkedList<Theme> getListOfThemes() {
		return listOfThemes;
	}

	public void setListOfThemes(LinkedList<Theme> listOfThemes) {
		this.listOfThemes = listOfThemes;
	}

	public Boolean getLoadOnStart() {
		return loadOnStart;
	}

	public void setLoadOnStart(Boolean loadOnStart) {
		this.loadOnStart = loadOnStart;
	}

	public Boolean getSaveOnExit() {
		return saveOnExit;
	}

	public void setSaveOnExit(Boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}

	public int getTickerSpeed() {
		return tickerSpeed;
	}

	public void setTickerSpeed(int tickerSpeed) {
		this.tickerSpeed = tickerSpeed;
	}
	

}
