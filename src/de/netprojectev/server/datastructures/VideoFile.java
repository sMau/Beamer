package de.netprojectev.server.datastructures;

import java.io.File;

import de.netprojectev.datastructures.media.Priority;

public class VideoFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2033435655791192980L;
	private File videoFile;
	private int duration;
	
	public VideoFile(String name, File videoFile) {
		super(name, new Priority("Video", 0));
		this.videoFile = videoFile;
		
		//TODO est. of duration
	}

	public File getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}
	
}
