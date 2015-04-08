package de.netprojectev.desktop.main;

import java.io.File;
import java.util.logging.Level;

import javax.swing.UnsupportedLookAndFeelException;

import de.netprojectev.client.ConstantsClient;
import de.netprojectev.common.utils.LoggerBuilder;

public class StarterDesktop {

	private static String OS = System.getProperty("os.name").toLowerCase();
	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(StarterDesktop.class);

	/**
	 * @param args
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		if (isWindows()) {
			System.setProperty("sun.java2d.opengl", "False");
			System.setProperty("sun.java2d.d3d", "True");

			javax.swing.UIManager.setLookAndFeel(
					javax.swing.UIManager.getSystemLookAndFeelClassName());

		} else if (isMac()) {
			System.setProperty("sun.java2d.opengl", "True");

			javax.swing.UIManager.setLookAndFeel(
					javax.swing.UIManager.getSystemLookAndFeelClassName());

		} else if (isUnix()) {
			System.setProperty("sun.java2d.opengl", "True");

			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

		} else if (isSolaris()) {
			System.setProperty("sun.java2d.opengl", "True");

			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}

		}

		File savePath = new File(ConstantsClient.SAVE_PATH);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					new MainFrameDesktop();
				} catch (Exception e) {
					log.log(Level.SEVERE, "Error during GUI init.", e);
					System.exit(0);
				} 
			}
		});
	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

}
