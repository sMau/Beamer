package de.netprojectev.client.datastructures;

import java.io.IOException;
import java.util.UUID;

import de.netprojectev.common.datastructures.MediaFile;
import de.netprojectev.server.datastructures.MediaFileServer;

public class MediaFileClient extends MediaFile {

	public static MediaFileClient reconstruct(UUID id, String name, byte[] preview, UUID priorityID,
			int showCount, MediaType type, boolean current) {
		MediaFileClient res = new MediaFileClient(name, id, priorityID, type, preview);
		res.setCurrent(current);
		res.showCount = showCount;

		return res;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 2896809877450716652L;
	// XXX safe the previews scaled to disk to save RAM
	private final MediaType type;

	private byte[] preview;

	public MediaFileClient(MediaFileServer file) throws IOException {
		this(file.getName(), file.getId(), file.getPriorityID(), file.determineMediaType(), file.determinePreview());
	}

	private MediaFileClient(String name, UUID id, UUID priorityID, MediaType type, byte[] preview) {
		super(name, id, priorityID);
		this.type = type;
		this.preview = preview;

	}

	public MediaFileClient copy() {
		return new MediaFileClient(this.name, getId(), this.priorityID, this.type, this.preview);
	}

	public byte[] getPreview() {
		return this.preview;
	}

	public MediaType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		String res = "";

		res += "Name: " + this.name + " ";
		res += "UUID: " + getId() + " ";
		res += "Type: " + this.type + " ";
		res += "Priority: " + this.priorityID + " ";

		return res;
	}

}
