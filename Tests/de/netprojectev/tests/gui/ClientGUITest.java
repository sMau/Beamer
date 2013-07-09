package de.netprojectev.tests.gui;

import java.io.File;

import de.netprojectev.client.Client;
import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.gui.main.MainClientGUIWindow;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientGUITest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final MediaModelClient mediaModel = new MediaModelClient(new ClientMessageProxy(new Client("", 0, null)));
		

		ClientMediaFile m1 = new ClientMediaFile(new VideoFile("Vid1", new File("/home/samu")));
		ClientMediaFile m2 = new ClientMediaFile(new VideoFile("Vid2", new File("/home/samu")));
		ClientMediaFile m3 = new ClientMediaFile(new VideoFile("Vid3", new File("/home/samu")));
		ClientMediaFile m4 = new ClientMediaFile(new VideoFile("Vid4", new File("/home/samu")));
		
		mediaModel.addMediaFile(m1);
		mediaModel.addMediaFile(m2);
		mediaModel.addMediaFile(m3);
		mediaModel.addMediaFile(m4);
		
		
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	 new MainClientGUIWindow(mediaModel, null).setVisible(true); //TODO change the null value when ticker implemented
            }
        });

	}

}
