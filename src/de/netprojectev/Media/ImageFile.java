package de.netprojectev.Media;

import java.util.Date;

import javax.swing.ImageIcon;

import de.netprojectev.Misc.Constants;

/**
 * 
 * Konkrete Implementierung der Datenstruktur MediaFile.
 * Bild Datei.
 *
 */
public class ImageFile extends MediaFile {

	private static final long serialVersionUID = -6684164019970242002L;
	private String path;

	public ImageFile(String name, String path, Priority priority) {
		super(name, priority);
		this.path = path;
		this.priority = Constants.DEFAULT_PRIORITY;
	}

	@Override
	public ImageIcon generatePreview() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("Show file: " + path + "   " + new Date());
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
