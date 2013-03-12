package de.netprojectev.server.datastructures.media;

import java.util.UUID;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.datastructures.media.Priority;

public class ServerMediaFile extends MediaFile {


	/**
	 * 
	 */
	private static final long serialVersionUID = -663985073993673515L;

	protected ServerMediaFile(String name, Priority priority) {
		super(name, priority, UUID.randomUUID());
	}
	
	protected ServerMediaFile(String name) {
		super(name, UUID.randomUUID());
	}

}