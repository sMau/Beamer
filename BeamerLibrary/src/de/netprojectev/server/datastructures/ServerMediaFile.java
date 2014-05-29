package de.netprojectev.server.datastructures;

import java.util.UUID;

import de.netprojectev.datastructures.MediaFile;
import de.netprojectev.datastructures.Priority;

public class ServerMediaFile extends MediaFile {


	/**
	 * 
	 */
	private static final long serialVersionUID = -663985073993673515L;

	protected ServerMediaFile(String name, Priority priority) {
		super(name, priority, UUID.randomUUID());
	}

}