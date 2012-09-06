package de.netprojectev.Preferences;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.GUI.Preferences.PreferencesFrame;
import de.netprojectev.GUI.Preferences.PriorityListModel;
import de.netprojectev.GUI.Preferences.ThemeListModel;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;

/**
 * Datastructure to hold preferences, priorities and themes.
 * @author samu
 *
 */
public class PreferencesHandler {

	private static PreferencesHandler instance = new PreferencesHandler();
	
	private PreferencesFrame preferencesFrame;
	private ManagerFrame managerFrame;
	
	private LinkedList<Priority> listOfPriorities;
	private LinkedList<Theme> listOfThemes;
	
	private Properties properties;
	
	private PreferencesHandler() {

		properties = new Properties(Misc.generateDefaultProps());
		this.listOfPriorities = new LinkedList<Priority>();
		addPriority(Constants.DEFAULT_PRIORITY);

		this.listOfThemes = new LinkedList<Theme>();
		
	}
	
	public static PreferencesHandler getInstance() {
		
		if(instance == null) {
			instance = new PreferencesHandler();
		}
		return instance;
	}
	
	/**
	 * This method updates the properties object, by writing all changes from the preferences frame to it.
	 * @throws IOException 
	 */
	public void updatePropertiesFromPreferencesFrame() throws IOException {

		properties.setProperty(Constants.PROP_PREVIEW_SCALE_WIDTH, "" + preferencesFrame.getPreviewWidth());
		properties.setProperty(Constants.PROP_SCREEN_NUMBER_FULLSCREEN, "" + preferencesFrame.getFullscreenNumber());
		properties.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR, preferencesFrame.getThemeslideCreatorFontColor());
		properties.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE, preferencesFrame.getThemeslideCreatorFontSize());
		properties.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE, preferencesFrame.getThemeslideCreatorFontType());
		properties.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT, "" + preferencesFrame.getThemeslideCreatorMarginLeft());
		properties.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP, "" + preferencesFrame.getThemeslideCreatorMarginTop());
		properties.setProperty(Constants.PROP_TICKER_FONTCOLOR, preferencesFrame.getTickerFontColor());
		properties.setProperty(Constants.PROP_TICKER_FONTSIZE, preferencesFrame.getTickerFontSize());
		properties.setProperty(Constants.PROP_TICKER_FONTTYPE, preferencesFrame.getTickerFontType());
		properties.setProperty(Constants.PROP_TICKER_SPEED, "" + preferencesFrame.getTickerSpeed());
		properties.setProperty(Constants.PROP_COUNTDOWN_FONTCOLOR, preferencesFrame.getCountdownFontColor());
		properties.setProperty(Constants.PROP_COUNTDOWN_FONTSIZE, preferencesFrame.getCountdownFontSize());
		properties.setProperty(Constants.PROP_COUNTDOWN_FONTTYPE, "" + preferencesFrame.getCountdownFontType());

		Misc.savePropertiesToDisk(properties);
		
	}
	
	/**
	 * Adds a priority to the list.
	 * @param prio the priority to add to handlers list
	 */
	public void addPriority(Priority prio) {
		if(!listOfPriorities.contains(prio)) {
			listOfPriorities.add(prio);
			refreshPrioListModel();
		}
		
	}
	
	/**
	 * Adds a theme to the list.
	 * @param theme the theme to add to handlers list
	 */
	public void addTheme(Theme theme) {
		if(!listOfThemes.contains(theme)) {
			listOfThemes.add(theme);
			refreshThemeListModel();
		}
	}
	
	/**
	 * removes the given priorities from handlers list
	 * prevents the user from deleting the default priority
	 * @param prios priorities to remove
	 */
	public void removePriorities(Priority[] prios) {
		for (int i = 0; i < prios.length; i++) {
			if(!prios[i].getName().equals("default")) {
				listOfPriorities.remove(prios[i]);
			}
		}
		refreshPrioListModel();
	}
	
	/**
	 * removes the given array of themes from handlers list
	 * @param themes themes to remove
	 */
	public void removeThemes(Theme[] themes) {
		for (int i = 0; i < themes.length; i++) {
			listOfThemes.remove(themes[i]);
		}
		refreshThemeListModel();
	}
	
	/**
	 * Invokes a update on the model of the JList to update the view
	 */
	public void refreshPrioListModel() {
		if(preferencesFrame != null) {
			((PriorityListModel) preferencesFrame.getjListPrio().getModel()).updateList();
		}
	}
	
	/**
	 * Invokes a update on the model of the JList to update the view
	 */
	public void refreshThemeListModel() {
		if(preferencesFrame != null) {
			((ThemeListModel) preferencesFrame.getjList2().getModel()).updateList();
		}
	}

	/**
	 * 
	 * @param name priorities name to search for
	 * @return priority matching the given name
	 */
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

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	

}
