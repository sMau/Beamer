package de.netprojectev.tests.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestVLCPlaying {

	public static void main(String[] args) throws IOException {

		final ArrayList<String> vlcStartCommand = new ArrayList<String>();
		vlcStartCommand.add("vlc");
		vlcStartCommand.add("-f");
		vlcStartCommand.add("--no-video-title-show");
		vlcStartCommand.add("--play-and-exit");
		vlcStartCommand.add("/media/truecrypt1/JDown/tvs-poi-dd51-ded-dl-7p-ithd-avc/Person.of.Interest.S02E09.Liebesgruesse.aus.Estland.German.DD51.Dubbed.DL.720p.iTunesHD.AVC-TVS");

		try {
			final Process vlc = new ProcessBuilder(vlcStartCommand).start();

			/*
			 * startLogReader(vlc.getInputStream());
			 * startLogReader(vlc.getErrorStream());
			 */

		} catch (Exception e) {
			System.out.println("catched!!!");
			e.printStackTrace();
		}

	}

	private static void startLogReader(final InputStream input) {
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
