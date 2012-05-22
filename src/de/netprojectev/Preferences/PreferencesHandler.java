package de.netprojectev.Preferences;

import java.util.LinkedList;

import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.GUI.Preferences.PreferencesFrame;
import de.netprojectev.GUI.Preferences.PriorityListModel;
import de.netprojectev.GUI.Preferences.ThemeListModel;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.Misc.Constants;

/**
 * Datastructure to hold preferences, priorities and themes.
 * @author samu
 *
 */
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
	
	/**
	 * 
	 * @param prio
	 */
	public void addPriority(Priority prio) {
		if(!listOfPriorities.contains(prio)) {
			listOfPriorities.add(prio);
			refreshPrioListModel();
		}
		
	}
	
	public void addTheme(Theme theme) {
		if(!listOfThemes.contains(theme)) {
			listOfThemes.add(theme);
			refreshThemeListModel();
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
		refreshThemeListModel();
	}
	
	public void refreshPrioListModel() {
		if(preferencesFrame != null) {
			((PriorityListModel) preferencesFrame.getjListPrio().getModel()).updateList();
		}
	}
	
	public void refreshThemeListModel() {
		if(preferencesFrame != null) {
			((ThemeListModel) preferencesFrame.getjList2().getModel()).updateList();
		}
	}
	
	public Boolean isPriorityUsed(Priority prio) {
		
		//UNUSED
		
		return false;
		
	}
	
	public Priority searchForPriority(String name) {
		
		Priority res = null;
		if(name != null && !name.equals("")) {
			for(int i = 0; i < listOfPriorities.size(); i++) {
				if(listOfPriorities.get(i).getName().equals(name)) {
					res = listOfPriorities.get(i);
				}
			}
		}
		
		return res;
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
