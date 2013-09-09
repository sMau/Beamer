package de.netprojectev.client.gui.models;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.netprojectev.client.gui.main.MainClientGUIWindow;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.PreferencesModelClient.PriorityListChangedListener;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.exceptions.PriorityDoesNotExistException;

public class PriorityComboBoxModel extends DefaultComboBoxModel<Priority> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4159572650794977280L;

	private final PreferencesModelClient prefs;
	
	public PriorityComboBoxModel(final PreferencesModelClient prefs) throws PriorityDoesNotExistException {
		super(prefs.prioritiesAsArray());
		this.prefs = prefs;
		this.prefs.addPriorityChangedListener(new PriorityListChangedListener() {
			
			@Override
			public void update() {
				try {
					updateListData();
				} catch (PriorityDoesNotExistException e) {
					prefs.getProxy().errorRequestFullSync(e);
				}
			}
		});
        
	}
	
	private void updateListData() throws PriorityDoesNotExistException {
		removeAllElements();
		Priority[] allPrios = prefs.prioritiesAsArray();
		for(int i = 0; i < allPrios.length; i++) {
			addElement(allPrios[i]);
		}
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				fireContentsChanged(this, 0, getSize());
			}
		});

	}
	
}
