package de.netprojectev.client.gui.preferences;

import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;

import de.netprojectev.server.model.PreferencesModel;


/**
 * This is the model for the Priority List in the PreferencesFrame.
 * It connects the preferencesHandlers data to the JList.
 * @author samu
 *
 */
public class PriorityListModel extends AbstractListModel<Object> {

	private static final long serialVersionUID = 1178949757753878018L;
	
	private PreferencesModel preferencesHandler;
	
	public PriorityListModel() {
		super();
		this.preferencesHandler = PreferencesModel.getInstance();
		
	}
	

	@Override
	public int getSize() {
		return preferencesHandler.getListOfPriorities().size();
	}

	@Override
	public Object getElementAt(int index) {
		return preferencesHandler.getListOfPriorities().get(index).getName();
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