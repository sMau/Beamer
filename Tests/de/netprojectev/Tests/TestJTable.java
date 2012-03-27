package de.netprojectev.Tests;

import de.netprojectev.GUI.ManagerFrame;
import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Misc.Constants;

public class TestJTable {

	/**
	 * @param args
	 * 
	 * Die Klasse dient lediglich zum starten der Oberfl�che und zum hinzuf�gen einiger weniger Testdateien.
	 * Die Oberfl�che muss mit der erzeugten Instanz manuell getestet werden.
	 * 
	 */
	public static void main(String[] args) {
		
		ManagerFrame frame;
		
		MediaFile testFile0 = new ImageFile("Werbung", "/home/samu/Bilder1", Constants.DEFAULT_PRIORITY);
		MediaFile testFile1 = new ImageFile("Hallo", "/home/samu/Bilder2", Constants.DEFAULT_PRIORITY);
		MediaFile testFile2 = new ImageFile("Webung1", "/home/samu/Bilder3", Constants.DEFAULT_PRIORITY);
		MediaFile[] testFiles = new MediaFile[3];
		testFiles[0] = testFile0;
		testFiles[1] = testFile1;
		testFiles[2] = testFile2;
		MediaHandler.getInstance().add(testFiles);
		
		try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println(e);
        }

        /*
         * Create and display the form
         */
        
		frame = new ManagerFrame();
		frame.setVisible(true);

	}

}
