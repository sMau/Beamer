package de.netprojectev.server.datastructures;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.datastructures.MediaFile;

public abstract class ServerMediaFile extends MediaFile {

	/**
	 *
	 */
	private static final long serialVersionUID = -663985073993673515L;

	protected ServerMediaFile(String name, UUID priorityID) {
		super(name, UUID.randomUUID(), priorityID);
	}

	/**
	 *
	 * @return the media type of this media file
	 */
	public abstract MediaType determineMediaType();

	/**
	 *
	 * @return the byte array representing the full image file, or null in case
	 *         of video and countdown
	 * @throws IOException
	 *             iff corresponding files cannot be read from disk
	 * @throws URISyntaxException
	 */
	public abstract byte[] determinePreview() throws IOException;

	protected byte[] getNoPreviewImage() throws IOException {
		return new byte[1];
	}

}