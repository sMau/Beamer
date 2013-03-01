package de.netprojectev.server.datastructures.media;

import java.io.File;

public class VideoFile extends ServerMediaFile {

	private File videoFile;
	private int duration;
	
	public VideoFile(String name, File videoFile) {
		super(name);
		this.videoFile = videoFile;
		
		//TODO est. of duration
	}
	
}
