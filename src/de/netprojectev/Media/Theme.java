package de.netprojectev.Media;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * 
 * Datenstruktur zur Verwaltung eines Themas für Themenfolien.
 *
 */
public class Theme implements Serializable {

	private static final long serialVersionUID = 5562694101580970506L;
	private String name;
	private ImageIcon backgroundImage;

	public Theme(String name, ImageIcon backgroundImage) {

		this.name = name;
		this.backgroundImage = backgroundImage;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImageIcon getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(ImageIcon backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

}
