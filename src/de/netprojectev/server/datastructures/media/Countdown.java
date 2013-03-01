package de.netprojectev.server.datastructures.media;

public class Countdown extends ServerMediaFile {

	private int duration;
	
	public Countdown(String name, int duration) {
		super(name);
		this.duration = duration;
	}
}
