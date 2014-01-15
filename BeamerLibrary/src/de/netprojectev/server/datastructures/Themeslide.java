package de.netprojectev.server.datastructures;

import java.io.IOException;
import java.util.UUID;

import de.netprojectev.datastructures.media.Priority;

public class Themeslide extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -14234502199126341L;
	private UUID themeId;
	private ImageFile imageRepresantation;
	
	public Themeslide(String name, UUID themeId, Priority priority, ImageFile imageRepresentation) {
		super(name, priority);
		this.themeId = themeId;
		this.imageRepresantation = imageRepresentation;
	}

	public byte[] get() throws IOException {
		return imageRepresantation.get();
	}

	public UUID getThemeId() {
		return themeId;
	}

	public ImageFile getImageRepresantation() {
		return imageRepresantation;
	}

}
