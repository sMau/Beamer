package de.netprojectev.server.datastructures;

public class Countdown extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4353683190496476107L;
	private int duration;
	
	public Countdown(String name, int duration) {
		super(name, null);
		this.duration = duration;
	}
}
