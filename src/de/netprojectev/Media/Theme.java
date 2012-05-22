package de.netprojectev.Media;

import java.io.File;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * 
 * Datastructure to manage the a theme. Its to be hold by a themeslide, mostly to specifiy the background image.
 * @author samu
 */
public class Theme implements Serializable {

	private static final long serialVersionUID = 5562694101580970506L;
	private String name;
	private File backgroundImage;

	/**
	 * 
	 * @param name name as identifier for the theme
	 * @param bgImg background image from the hard disk
	 */
	public Theme(String name, File bgImg) {
		this.name  = name;
		this.backgroundImage = bgImg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(File backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

}
