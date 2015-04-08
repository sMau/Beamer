package de.netprojectev.desktop.preferences;

import javax.swing.AbstractListModel;
import javax.swing.SwingUtilities;

import de.netprojectev.client.datamodel.PreferencesModelClient.ThemeListChangedListener;
import de.netprojectev.client.datamodel.PreferencesModelClientDesktop;
import de.netprojectev.common.datastructures.Theme;
import de.netprojectev.common.exceptions.ThemeDoesNotExistException;

public class ThemeListModel extends AbstractListModel<Theme> {

	/**
	 *
	 */
	private static final long serialVersionUID = -7505622765694721418L;
	private final PreferencesModelClientDesktop prefs;

	public ThemeListModel(PreferencesModelClientDesktop prefs) {
		super();
		this.prefs = prefs;
		this.prefs.setThemeListChangeListener(new ThemeListChangedListener() {

			@Override
			public void update() {
				updateListData();

			}
		});
	}

	@Override
	public int getSize() {
		return prefs.themeCount();
	}

	@Override
	public Theme getElementAt(int index) {
		try {
			return prefs.getThemeAt(index);
		} catch (ThemeDoesNotExistException e) {
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
