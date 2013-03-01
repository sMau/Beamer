package de.netprojectev.server.datastructures.media;

import java.util.UUID;

public class Themeslide extends ServerMediaFile {

	private ImageFile imageRepresantation;
	private UUID themeId;
	
	public Themeslide(String name, ImageFile image, UUID themeId) {
		super(name);
		this.imageRepresantation = image;
		this.themeId = themeId;
	}

	public ImageFile getImageRepresantation() {
		return imageRepresantation;
	}

	public UUID getThemeId() {
		return themeId;
	}
	
}
