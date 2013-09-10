package de.netprojectev.client.gui.main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.UnsupportedLookAndFeelException;

import de.netprojectev.client.ConstantsClient;

public class StarterClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.setProperty("sun.java2d.opengl", "True");

		// TODO add memory params to the vm
		// TODO check which look and feel to use

		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (ClassNotFoundException | InstantiationException |
		 * IllegalAccessException | UnsupportedLookAndFeelException e) {
		 * 
		 * e.printStackTrace();
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		// }

		File savePath = new File(ConstantsClient.SAVE_PATH);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainClientGUIWindow();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		});
	}

}
