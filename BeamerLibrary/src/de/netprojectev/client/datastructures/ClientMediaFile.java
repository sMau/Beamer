package de.netprojectev.client.datastructures;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import de.netprojectev.datastructures.MediaFile;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientMediaFile extends MediaFile {

	public interface ClientMediaFilePreview {

	}

	// TODO safe the previews scaled to disk to save RAM
	private final MediaType type;
	private byte[] preview;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2896809877450716652L;

	public static ClientMediaFile reconstruct(UUID id, String name, byte[] preview, UUID priorityID,
			int showCount, MediaType type, boolean current) {
		ClientMediaFile res = new ClientMediaFile(name, id, priorityID, type);
		res.setPreview(preview);
		res.setCurrent(current);
		res.showCount = showCount;
		return res;
	}

	private ClientMediaFile(ClientMediaFile clientMediaFile) {
		super(clientMediaFile.getName(), clientMediaFile.getPriorityID(), clientMediaFile.getId());
		this.type = clientMediaFile.type;
	}

	public ClientMediaFile(ServerMediaFile serverMediaFile) throws FileNotFoundException, IOException {
		super(serverMediaFile.getName(), serverMediaFile.getPriorityID(), serverMediaFile.getId());
		// TODO make preview possible for videos and countdown

		this.showCount = serverMediaFile.getShowCount();
		this.current = serverMediaFile.isCurrent();

		int widthToScaleTo = ConstantsServer.DEFAULT_PREVIEW_SCAL_WIDTH;

		if (serverMediaFile instanceof Countdown) {
			this.type = MediaType.Countdown;
			this.preview = null;
		} else if (serverMediaFile instanceof ImageFile) {
			this.type = MediaType.Image;
			this.preview = ((ImageFile) serverMediaFile).get();
		} else if (serverMediaFile instanceof VideoFile) {
			this.type = MediaType.Video;
			this.preview = null;
		} else if (serverMediaFile instanceof Themeslide) {
			this.type = MediaType.Themeslide;
			this.preview = ((Themeslide) serverMediaFile).get();

		} else {
			this.type = MediaType.Unknown;
			this.preview = null;
		}

	}

	private ClientMediaFile(String name, UUID id, UUID priorityID, MediaType type) {
		super(name, id, priorityID);
		this.type = type;
	}

	public ClientMediaFile copy() {
		return new ClientMediaFile(this);
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

}
