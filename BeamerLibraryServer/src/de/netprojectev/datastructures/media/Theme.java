package de.netprojectev.datastructures.media;

import java.io.Serializable;
import java.util.UUID;

import javax.swing.ImageIcon;

/**
 * 
 * Datastructure to manage the a theme. Its to be hold by a themeslide, mostly to specifiy the background image.
 * @author samu
 */
public class Theme implements Serializable {

	private static final long serialVersionUID = 5562694101580970506L;
	private final UUID id;
	private String name;
	private ImageIcon backgroundImage;

	/**
	 * 
	 * @param name name as identifier for the theme
	 * @param bgImg background image from the hard disk
	 */
	public Theme(String name, ImageIcon bgImg) {
		this.id = UUID.randomUUID();
		this.name  = name;
		this.backgroundImage = bgImg;
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

	public UUID getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null || this == null) {
	    	return false;
	    } else if(other == this){
	    	return true;
	    } else if(other instanceof Theme) {
	    	return ((Theme) other).getId().equals(this.getId());
	    } else {
	    	return false;
	    }
	}
}