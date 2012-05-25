package de.netprojectev.Tests;

import de.netprojectev.GUI.Manager.ManagerFrame;

public class TestJTable {
	
	/**
	 * @param args
	 * 
	 * Test class to start main frame
	 * 
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
