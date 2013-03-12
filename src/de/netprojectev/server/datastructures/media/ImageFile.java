package de.netprojectev.server.datastructures.media;

import javax.swing.ImageIcon;

public class ImageFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972824512907132864L;
	private ImageIcon image;
	
	public ImageFile(String name, ImageIcon image) {
		super(name);
		this.image = image;
	}

	public ImageIcon getImage() {
		return image;
	}
	
}
