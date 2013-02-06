package de.netprojectev.client.gui.preferences;

import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;

import de.netprojectev.old.server.model.PreferencesModelOld;


/**
 * This is the model for the Theme List in the PreferencesFrame.
 * It connects the preferencesHandlers data to the JList.
 * @author samu
 *
 */
public class ThemeListModel extends AbstractListModel<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7235749567262695917L;

	private PreferencesModelOld preferencesHandler;
	
	public ThemeListModel() {
		super();
		this.preferencesHandler = PreferencesModelOld.getInstance();
		
	}
	
	
	@Override
	public int getSize() {
		return preferencesHandler.getListOfThemes().size();
	}

	@Override
	public Object getElementAt(int index) {
		return preferencesHandler.getListOfThemes().get(index).getName();
	}
	
	/**
     * Updating the list view to the current Data Changes on the Model.
     * Uses invokeLater for clean and thread-save event handling.
     */
	public void updateList() {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	fireContentsChanged(this, 0, preferencesHandler.getListOfPriorities().size());
            }
        });
	}

}
