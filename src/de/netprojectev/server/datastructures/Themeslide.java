package de.netprojectev.server.datastructures;

import java.util.UUID;

import de.netprojectev.datastructures.media.Priority;

public class Themeslide extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -14234502199126341L;
	private UUID themeId;
	private ImageFile imageRepresantation;
	
	public Themeslide(String name, UUID themeId, Priority priority) {
		super(name, priority);
		this.themeId = themeId;
	}

	public ImageFile getImageRepresantation() {
		return imageRepresantation;
	}

	public UUID getThemeId() {
		return themeId;
	}

	public void setImageRepresantation(ImageFile imageRepresantation) {
		this.imageRepresantation = imageRepresantation;
	}
	
}
