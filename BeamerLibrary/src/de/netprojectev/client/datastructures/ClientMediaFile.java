package de.netprojectev.client.datastructures;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import de.netprojectev.datastructures.MediaFile;
import de.netprojectev.server.datastructures.ServerMediaFile;

public class ClientMediaFile extends MediaFile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2896809877450716652L;
	//XXX safe the previews scaled to disk to save RAM
	private final MediaType type;
	private byte[] preview;

	
	public ClientMediaFile(ServerMediaFile file) throws IOException {
		this(file.getName(), file.getId(), file.getPriorityID(), file.determineMediaType(), file.determinePreview());
	}
	
	public static ClientMediaFile reconstruct(UUID id, String name, byte[] preview, UUID priorityID,
			int showCount, MediaType type, boolean current) {
		ClientMediaFile res = new ClientMediaFile(name, id, priorityID, type, preview);
		res.setCurrent(current);
		res.showCount = showCount;
		
		return res;
	}

	private ClientMediaFile(String name, UUID id, UUID priorityID, MediaType type, byte[] preview) {
		super(name, id, priorityID);
		this.type = type;
		this.preview = preview;
	}

	public byte[] getPreview() {
		return this.preview;
	}

	public MediaType getType() {
		return this.type;
	}

	public void setPreview(byte[] preview) {
		this.preview = preview;
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

	public ClientMediaFile copy() {
		return new ClientMediaFile(name, getId(), priorityID, type, preview);
	}

}
