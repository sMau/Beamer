package de.netprojectev.GUI;

import javax.swing.AbstractListModel;

import de.netprojectev.Misc.PreferencesHandler;

public class PriorityListModel extends AbstractListModel<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1178949757753878018L;
	
	private PreferencesHandler preferencesHandler;
	
	public PriorityListModel() {
		super();
		this.preferencesHandler = PreferencesHandler.getInstance();
		
	}
	
	

	@Override
	public int getSize() {
		return preferencesHandler.getListOfPriorities().size();
	}

	@Override
	public Object getElementAt(int index) {
		return preferencesHandler.getListOfPriorities().get(index).getName();
	}

}
