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
	private ImageIcon preview;

	public ImageFile(String name, String path, Priority priority) {
		super(name, priority);
		this.path = path;
		this.priority = Constants.DEFAULT_PRIORITY;
		this.preview = generatePreview();
	}

	@Override
	public ImageIcon generatePreview() {
		System.out.println("generate Preview   " + path);
		if(path != null) {
			return new ImageIcon(path);
		} else {
			return null;
		}

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

	public ImageIcon getPreview() {
		return preview;
	}

	public void setPreview(ImageIcon preview) {
		this.preview = preview;
	}

}
