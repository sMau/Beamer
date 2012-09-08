package de.netprojectev.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;

public class Starter {

	private static Logger log;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		

		/*
		 * using Nimbus as LaF
		 */
		
		onFirstStart();
		
		try {
			log.log(Level.INFO, "Starting up...");
	        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (ClassNotFoundException ex) {
	       log.log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (InstantiationException ex) {
	    	log.log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (IllegalAccessException ex) {
	    	log.log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    	log.log(java.util.logging.Level.SEVERE, null, ex);
	    }
		
		

		SwingUtilities.invokeLater(new Runnable() {

	        public void run() {
	            new ManagerFrame().setVisible(true);
	        }
	    });

	}

	/**
	 * checks if this is the first time the program is started on this machine,
	 * and creates the necessary file structure in the home folder of the user
	 * if it does not exist already
	 * It also generates a standard properties file, containing the defaults.
	 *
	 */
	private static void onFirstStart() {
		
		
		File toTestMain = new File(Constants.SAVE_PATH);
		File toTestCache = new File(Constants.SAVE_PATH + Constants.FOLDER_THEMESLIDE_CACHE);
		File properties = new File(Constants.SAVE_PATH + Constants.FILENAME_PROPERTIES);
		if(!toTestMain.isDirectory()) {
			toTestMain.mkdirs();
		}
		if(!toTestCache.isDirectory()) {
			toTestCache.mkdirs();
		}
		
		log = Misc.getLoggerAll(Starter.class.getName());
		log.log(Level.INFO, "first time starting program, setting up files and directories");
		if(!properties.exists()) {
			try {
				Misc.savePropertiesToDisk(Misc.generateDefaultProps());
			} catch (IOException e) {
				log.log(Level.SEVERE, null, e);
			}
		}
		
	}

}
