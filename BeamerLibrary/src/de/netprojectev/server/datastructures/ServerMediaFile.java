package de.netprojectev.server.datastructures;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.datastructures.MediaFile;

public class ServerMediaFile extends MediaFile {

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
	public MediaType determineMediaType() {
		if (this instanceof Countdown) {
			return MediaType.Countdown;
		} else if (this instanceof ImageFile) {
			return MediaType.Image;
		} else if (this instanceof VideoFile) {
			return MediaType.Video;
		} else if (this instanceof Themeslide) {
			return MediaType.Themeslide;
		} else {
			return MediaType.Unknown;
		}
	}
	
	/**
	 * 
	 * @return the byte array representing the full image file, or null in case of video and countdown
	 * @throws IOException iff corresponding files cannot be read from disk
	 * @throws URISyntaxException 
	 */
	public byte[] determinePreview() throws IOException {
		//TODO previewing for Videos and Countdowns 
		if (this instanceof Countdown) {
			return getNoPreviewImage();
		} else if (this instanceof ImageFile) {
			return ((ImageFile) this).get();
		} else if (this instanceof VideoFile) {
			return getNoPreviewImage();
		} else if (this instanceof Themeslide) {
			return ((Themeslide) this).get();
		} else {
			return getNoPreviewImage();
		}
	}
	
	private byte[] getNoPreviewImage() throws IOException {
		URL url = this.getClass().getResource("/de/netprojectev/server/gfx/no_preview.png");
		try {
			return Files.readAllBytes(Paths.get(url.toURI()));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}