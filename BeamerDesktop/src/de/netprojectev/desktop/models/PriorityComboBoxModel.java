package de.netprojectev.desktop.models;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;

import de.netprojectev.client.datamodel.PreferencesModelClient.PriorityListChangedListener;
import de.netprojectev.client.datamodel.PreferencesModelClientDesktop;
import de.netprojectev.common.datastructures.Priority;
import de.netprojectev.common.exceptions.PriorityDoesNotExistException;

public class PriorityComboBoxModel extends DefaultComboBoxModel<Priority> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4159572650794977280L;

	private final PreferencesModelClientDesktop prefs;

	public PriorityComboBoxModel(final PreferencesModelClientDesktop prefs) throws PriorityDoesNotExistException {
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
		for (int i = 0; i < allPrios.length; i++) {
			addElement(allPrios[i]);
		}
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				fireContentsChanged(this, 0, getSize());
			}
		});

	}

}
