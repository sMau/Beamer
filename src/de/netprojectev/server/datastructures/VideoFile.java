package de.netprojectev.server.datastructures;

import java.io.File;

public class VideoFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2033435655791192980L;
	private File videoFile;
	private int duration;
	
	public VideoFile(String name, File videoFile) {
		super(name);
		this.videoFile = videoFile;
		
		//TODO est. of duration
	}
	
}
