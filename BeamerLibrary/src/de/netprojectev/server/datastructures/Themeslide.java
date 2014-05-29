package de.netprojectev.server.datastructures;

import java.io.IOException;
import java.util.UUID;

public class Themeslide extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -14234502199126341L;
	private UUID themeId;
	private ImageFile imageRepresantation;

	public Themeslide(String name, UUID themeId, UUID priorityID, ImageFile imageRepresentation) {
		super(name, priorityID);
		this.themeId = themeId;
		this.imageRepresantation = imageRepresentation;
	}

	public byte[] get() throws IOException {
		return this.imageRepresantation.get();
	}

	public ImageFile getImageRepresantation() {
		return this.imageRepresantation;
	}

	public UUID getThemeId() {
		return this.themeId;
	}

}
