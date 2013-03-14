package de.netprojectev.client.media;

import java.util.UUID;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.datastructures.media.Priority;

public class ClientMediaFile extends MediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2896809877450716652L;

	protected ClientMediaFile(String name, Priority priority, UUID id) {
		super(name, priority, id);

	}

}
