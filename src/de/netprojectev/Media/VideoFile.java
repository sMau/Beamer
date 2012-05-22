package de.netprojectev.Media;

import java.util.Date;

import de.netprojectev.Misc.Constants;

/**
 * Datastructure to deal with video files from hard disk.
 * 
 * *** incomplete and used at the moment ***
 *
 */
public class VideoFile extends MediaFile {

	private static final long serialVersionUID = 2281243056699366253L;
	private String path;
	private int length;

	/**
	 * 
	 * @param name name in the manager
	 * @param path path to the video on the hard disk
	 */
	public VideoFile(String name, String path) {
		
		super(name, Constants.DEFAULT_PRIORITY); //TODO Implement a Default Video Priority with minutesToShow = video.length
		this.path = path;
		this.length = generateVideoLength();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("Show file: " + path + "   " + new Date());
		
	}
	
	@Override
	public String generateInfoString() {
		
		//TODO implementing differnt info, cause of video length usw.
		
		
		return super.generateInfoString();
	}
	
	private int generateVideoLength() {
		
		//TODO Video Länge aus Datei auslesen.
		
		return 5000;
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
