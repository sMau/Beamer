package de.netprojectev.client.gui.preferences;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.netprojectev.client.gui.main.MainClientGUIWindow;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.PreferencesModelClient.PriorityListChangedListener;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.exceptions.PriorityDoesNotExistException;

public class PriorityListModel extends AbstractListModel<Priority> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5497538191945223356L;
	private final PreferencesModelClient prefs;
	
	public PriorityListModel(PreferencesModelClient prefs) {
		super();
		this.prefs = prefs;
		this.prefs.addPriorityChangedListener(new PriorityListChangedListener() {
			
			@Override
			public void update() {
				updateListData();
			}
		});
        
       
	}
	
	@Override
	public int getSize() {		
		return prefs.priorityCount();
	}
	
	@Override
	public Priority getElementAt(int index) {
		try {
			return prefs.getPriorityAt(index);
		} catch (PriorityDoesNotExistException e) {
			ClientMessageProxy.errorRequestFullSync(prefs.getProxy(), e);
			MainClientGUIWindow.errorRequestingFullsyncDialog(new JFrame());
		}
		return null;
	}

	private void updateListData() {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				fireContentsChanged(this, 0, getSize());
			}
		});

	}


}
