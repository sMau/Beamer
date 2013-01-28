package de.netprojectev.server.datastructures.media;

import java.io.Serializable;
import java.util.UUID;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.datastructures.media.Priority;

/**
 * abstract datastructure to deal, to deal with different media in an extension of this class
 * 
 * @author samu
 */
public abstract class ServerMediaFile extends MediaFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1218179453523031285L;
	
	public ServerMediaFile(String name, Priority priority) {
		super(name, priority, UUID.randomUUID());
	}

}
