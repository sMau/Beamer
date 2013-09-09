package de.netprojectev.server.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class VlcPlayBackUtility {

	private final File toPlay;

	public VlcPlayBackUtility(File toPlay) {
		this.toPlay = toPlay;
	}

	public Process startPlay() throws Exception {

		//TODO quit video if nexat is clicked before video playing is finished
		final ArrayList<String> vlcStartCommand = new ArrayList<String>();
		vlcStartCommand.add("vlc");
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

}
