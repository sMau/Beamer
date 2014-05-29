package de.netprojectev.server.datastructures;

import java.util.UUID;

import de.netprojectev.datastructures.MediaFile;

public class ServerMediaFile extends MediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -663985073993673515L;

	protected ServerMediaFile(String name, UUID priorityID) {
		super(name, priorityID, UUID.randomUUID());
		// TODO fix the video shit
	}
}