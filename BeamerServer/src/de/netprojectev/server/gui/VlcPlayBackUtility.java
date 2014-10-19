package de.netprojectev.server.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class VlcPlayBackUtility {

	private final File toPlay;
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public VlcPlayBackUtility(File toPlay) {
		this.toPlay = toPlay;
	}

	public Process startPlay() throws Exception {

		//TODO quit video if nexat is clicked before video playing is finished
		final ArrayList<String> vlcStartCommand = new ArrayList<String>();
		
		if (isMac()) {
			vlcStartCommand.add("/Applications/VLC.app/Contents/MacOS/VLC");
		} else if (isUnix()) {
			vlcStartCommand.add("vlc");
		}
		
		vlcStartCommand.add("-f");
		vlcStartCommand.add("--no-video-title-show");
		vlcStartCommand.add("--play-and-exit");
		vlcStartCommand.add("--video-on-top");
		vlcStartCommand.add(toPlay.getAbsolutePath());
		
		return new ProcessBuilder(vlcStartCommand).start();
	}

	private void startLogReader(final InputStream input) {
		Thread loggingReader = new Thread(new Runnable() {

			@Override
			public void run() {

				BufferedReader br = new BufferedReader(new InputStreamReader(input));
				try {
					while ((br.readLine()) != null) {

					}
				} catch (IOException ex) {
					System.out.println(ex);
				}
				try {
					br.close();
				} catch (IOException ex) {
					System.out.println(ex);
				}
			}
		});
		loggingReader.start();
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
