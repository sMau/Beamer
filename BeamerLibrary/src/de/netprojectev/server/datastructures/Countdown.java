package de.netprojectev.server.datastructures;

import de.netprojectev.datastructures.Priority;
import de.netprojectev.utils.HelperMethods;

public class Countdown extends ServerMediaFile {

	/**
	 * 
	 */
	private final static long serialVersionUID = -4353683190496476107L;
	private long initDurationInSeconds;
	private long durationInSeconds;

	private String timeString;
	
	public Countdown(String name, int durationInMinutes) {
		super(name, new Priority("Countdown", durationInMinutes));
		if(durationInMinutes <= 0) {
			throw new IllegalArgumentException("duration have to be bigger than 0");
		}
		this.initDurationInSeconds = durationInMinutes * 60;
		this.durationInSeconds = initDurationInSeconds;
		this.timeString = HelperMethods.convertFromSecondsToTimeString((int) durationInSeconds, true);
	}

	public long getInitDurationInSeconds() {
		return initDurationInSeconds;
	}
	
	public void decreaseOneSecond() {
		durationInSeconds--;
		timeString = HelperMethods.convertFromSecondsToTimeString((int) durationInSeconds, true);
		if(durationInSeconds < 0) {
			durationInSeconds = initDurationInSeconds;
		}
	}
	

	public String getTimeString() {
		return timeString;
	}

	public long getDurationInSeconds() {
		return durationInSeconds;
	}
	
}
