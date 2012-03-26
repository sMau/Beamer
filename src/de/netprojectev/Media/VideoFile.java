package de.netprojectev.Media;

import javax.swing.ImageIcon;

import de.netprojectev.Misc.Constants;

/**
 * 
 * Konkrete Implementierung der Datenstruktur MediaFile.
 * Video Datei.
 * Video File hält auch eine Priorität, um Polymorph auf MediaFile arbeiten zu können.
 * Sie wird als "Dummyfile" implementiert und enthält die Video Länge als minutesToShow.
 *
 */
public class VideoFile extends MediaFile {

	private static final long serialVersionUID = 2281243056699366253L;
	private String path;
	private int length;

	public VideoFile(String name, String path) {
		
		super(name, Constants.DEFAULT_PRIORITY); //TODO Implement a Default Video Priority with minutesToShow = video.length
		this.path = path;
		this.length = generateVideoLength();
	}

	@Override
	public ImageIcon generatePreview() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("Show file: " + path);
		
	}
	
	private int generateVideoLength() {
		
		//TODO Video Länge aus Datei auslesen.
		
		return 5000;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
