package de.netprojectev.tests;

import de.netprojectev.client.gui.manager.ManagerFrame;
import de.netprojectev.client.gui.preferences.PreferencesFrame;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.server.model.PreferencesModel;

public class TestPreferences {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PreferencesModel handler = PreferencesModel.getInstance();
		handler.addPriority(new Priority("test1", 1));
		handler.addPriority(new Priority("test2", 5));
		handler.addPriority(new Priority("test3", 10));
		handler.addPriority(new Priority("test4", 15));
		handler.addPriority(new Priority("test5", 20));
		
		try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println(e);
        }
		
		
		new PreferencesFrame(new ManagerFrame()).setVisible(true);

	}

}
