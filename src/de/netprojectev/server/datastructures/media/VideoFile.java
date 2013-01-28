package de.netprojectev.server.datastructures.media;

import de.netprojectev.misc.Constants;

/**
 * Datastructure to deal with video files from hard disk.
 * 
 * *** incomplete and unused at the moment ***
 *
 */
public class VideoFile extends ServerMediaFile {

	/*
	 * Class not used yet, as videos arent supported at the moment
	 * 
	 */
	
	private static final long serialVersionUID = 2281243056699366253L;
	private String path;
	private int length;

	/**
	 * 
	 * @param name name in the manager
	 * @param path path to the video on the hard disk
	 */
	public VideoFile(String name, String path) {
		
		super(name, Constants.DEFAULT_PRIORITY);
		this.path = path;
		this.length = generateVideoLength();
	}
	
	@Override
	public String generateInfoString() {
		
		return super.generateInfoString();
	}
	
	private int generateVideoLength() {

		return 0;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
