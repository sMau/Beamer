package de.netprojectev.GUI.Preferences;

import javax.swing.AbstractListModel;

import de.netprojectev.Preferences.PreferencesHandler;



public class ThemeListModel extends AbstractListModel<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7235749567262695917L;

	private PreferencesHandler preferencesHandler;
	
	public ThemeListModel() {
		super();
		this.preferencesHandler = PreferencesHandler.getInstance();
		
	}
	
	
	@Override
	public int getSize() {
		return preferencesHandler.getListOfThemes().size();
	}

	@Override
	public Object getElementAt(int index) {
		return preferencesHandler.getListOfThemes().get(index).getName();
	}

}
