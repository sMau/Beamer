package de.netprojectev.Tests;

import de.netprojectev.GUI.Main.ManagerFrame;
import de.netprojectev.GUI.Preferences.PreferencesFrame;
import de.netprojectev.Media.Priority;
import de.netprojectev.Preferences.PreferencesHandler;

public class TestPreferences {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PreferencesHandler handler = PreferencesHandler.getInstance();
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
