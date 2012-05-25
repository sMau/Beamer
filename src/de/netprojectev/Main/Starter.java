package de.netprojectev.Main;

import de.netprojectev.GUI.Manager.ManagerFrame;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println(e);
        }
		new ManagerFrame().setVisible(true);

	}

}
