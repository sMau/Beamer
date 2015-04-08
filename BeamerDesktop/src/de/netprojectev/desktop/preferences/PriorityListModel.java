package de.netprojectev.desktop.preferences;

import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;

import de.netprojectev.client.datamodel.PreferencesModelClient.PriorityListChangedListener;
import de.netprojectev.client.datamodel.PreferencesModelClientDesktop;
import de.netprojectev.common.datastructures.Priority;
import de.netprojectev.common.exceptions.PriorityDoesNotExistException;

public class PriorityListModel extends AbstractListModel<Priority> {

	/**
	 *
	 */
	private static final long serialVersionUID = -5497538191945223356L;
	private final PreferencesModelClientDesktop prefs;

	public PriorityListModel(PreferencesModelClientDesktop prefs) {
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
			prefs.getProxy().errorRequestFullSync(e);
		}
		return null;
	}

	private void updateListData() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				fireContentsChanged(this, 0, getSize());
			}
		});

	}

}
