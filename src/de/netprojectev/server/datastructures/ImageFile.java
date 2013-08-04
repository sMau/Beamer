package de.netprojectev.server.datastructures;

import javax.swing.ImageIcon;

import de.netprojectev.datastructures.media.Priority;

public class ImageFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972824512907132864L;
	private ImageIcon image;
	
	public ImageFile(String name, ImageIcon image, Priority priority) {
		super(name, priority);
		this.image = image;
	}

	public ImageIcon getImage() {
		return image;
	}
	
}
