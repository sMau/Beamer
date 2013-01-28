package de.netprojectev.client.media;

import java.util.UUID;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.datastructures.media.Priority;

public class ClientMediaFile extends MediaFile {

	private Preview preview;
	
	protected ClientMediaFile(String name, Priority priority, UUID id) {
		super(name, priority, id);

	}

	public Preview getPreview() {
		return preview;
	}

	public void setPreview(Preview preview) {
		this.preview = preview;
	}

}
