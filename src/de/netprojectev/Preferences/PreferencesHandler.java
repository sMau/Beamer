package de.netprojectev.Preferences;

import java.util.LinkedList;

import de.netprojectev.GUI.Main.ManagerFrame;
import de.netprojectev.GUI.Preferences.PreferencesFrame;
import de.netprojectev.GUI.Preferences.PriorityListModel;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.Misc.Constants;

public class PreferencesHandler {
	
	private static PreferencesHandler instance;
	
	private PreferencesFrame preferencesFrame;
	private ManagerFrame managerFrame;
	
	private LinkedList<Priority> listOfPriorities;
	private LinkedList<Theme> listOfThemes;
	
	private Boolean loadOnStart;
	private Boolean saveOnExit;
	
	private int tickerSpeed;
	
	private PreferencesHandler() {
		
		this.listOfPriorities = new LinkedList<Priority>();
		addPriority(Constants.DEFAULT_PRIORITY);
		
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
			refreshPrioListModel();
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
		refreshPrioListModel();
	}
	
	public void removeThemes(Theme[] themes) {
		for (int i = 0; i < themes.length; i++) {
			listOfThemes.remove(themes[i]);
		}
	}
	
	public void refreshPrioListModel() {
		if(preferencesFrame != null) {
			((PriorityListModel) preferencesFrame.getjListPrio().getModel()).updateList();
		}
	}
	
	public Boolean isPriorityUsed(Priority prio) {
		
		//UNUSED
		
		return false;
		
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

	public PreferencesFrame getPreferencesFrame() {
		return preferencesFrame;
	}

	public void setPreferencesFrame(PreferencesFrame preferencesFrame) {
		this.preferencesFrame = preferencesFrame;
	}

	public ManagerFrame getManagerFrame() {
		return managerFrame;
	}

	public void setManagerFrame(ManagerFrame managerFrame) {
		this.managerFrame = managerFrame;
	}
	

}
