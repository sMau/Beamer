package de.netprojectev.server.datastructures;

import java.io.File;
import java.util.UUID;

public class VideoFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2033435655791192980L;
	private File videoFile;

	public VideoFile(String name, File videoFile) {
		super(name, UUID.randomUUID()); // new Priority("Video", 0)
		throw new UnsupportedOperationException("Video is not working at the moment.");
		/*
		 * this.videoFile = videoFile;
		 */
	}

	public File getVideoFile() {
		return this.videoFile;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}

}
