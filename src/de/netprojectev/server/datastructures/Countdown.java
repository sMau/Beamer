package de.netprojectev.server.datastructures;

import java.text.DateFormat;
import java.util.Date;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.misc.Misc;

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
			throw new IllegalArgumentException("duration have to be bigge than 0");
		}
		this.initDurationInSeconds = durationInMinutes * 60;
		this.durationInSeconds = initDurationInSeconds;
		this.timeString = Misc.convertFromSecondsToTimeString((int) durationInSeconds, true);
	}

	public long getInitDurationInSeconds() {
		return initDurationInSeconds;
	}
	
	public void decreaseOneSecond() {
		durationInSeconds--;
		timeString = Misc.convertFromSecondsToTimeString((int) durationInSeconds, true);
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
