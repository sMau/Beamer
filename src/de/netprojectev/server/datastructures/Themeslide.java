package de.netprojectev.server.datastructures;

import java.util.UUID;

import de.netprojectev.datastructures.media.Priority;

public class Themeslide extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -14234502199126341L;
	private ImageFile imageRepresantation;
	private UUID themeId;
	
	public Themeslide(String name, ImageFile image, UUID themeId, Priority priority) {
		super(name, priority);
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
