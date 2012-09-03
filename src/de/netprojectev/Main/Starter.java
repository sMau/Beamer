package de.netprojectev.Main;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * this here is using native LaF. 
		 */
		/*try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println(e);
        }
		new ManagerFrame().setVisible(true);*/
		
		
		/*
		 * 
		 * Using seaglass LaF (seems to be a little buggy, sadly, as its very nice LaF)
		 * http://code.google.com/p/seaglass/
		 * 
		 */
		
		
		/*
		try {
			javax.swing.UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		*/
		/*
		 * 
		 * using Nibums as LaF
		 * 
		 */
		
		try {
	        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (ClassNotFoundException ex) {
	        java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (InstantiationException ex) {
	        java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (IllegalAccessException ex) {
	        java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	        java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	    }
		
		onFirstStart();

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
		if(!properties.exists()) {
			try {
				Misc.savePropertiesToDisk(Misc.generateDefaultProps());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
