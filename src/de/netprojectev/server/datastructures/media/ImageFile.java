package de.netprojectev.server.datastructures.media;

import javax.swing.ImageIcon;

public class ImageFile extends ServerMediaFile {

	private ImageIcon image;
	
	public ImageFile(String name, ImageIcon image) {
		super(name);
		this.image = image;
	}

	public ImageIcon getImage() {
		return image;
	}
	
}
