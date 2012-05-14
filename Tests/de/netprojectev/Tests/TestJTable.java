package de.netprojectev.Tests;

import de.netprojectev.GUI.Main.ManagerFrame;

public class TestJTable {
	
	/**
	 * @param args
	 * 
	 * Die Klasse dient lediglich zum starten der Oberfl�che und zum hinzuf�gen einiger weniger Testdateien.
	 * Die Oberfl�che muss mit der erzeugten Instanz manuell getestet werden.
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
