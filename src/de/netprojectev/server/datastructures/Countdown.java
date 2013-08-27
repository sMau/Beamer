package de.netprojectev.server.datastructures;

import java.util.Date;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.misc.Misc;

public class Countdown extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4353683190496476107L;
	private final long initDurationInSeconds;
	private long durationInSeconds;
	
	private String timeString;
	
	public Countdown(String name, int durationInMinutes) {
		super(name, new Priority("Countdown", durationInMinutes));
		this.initDurationInSeconds = durationInMinutes * 60;
		this.durationInSeconds = initDurationInSeconds;
		this.timeString = Misc.convertFromSecondsToTimeString((int) durationInSeconds, true);
	}
	
	public Countdown(String name, Date finishDate) {
		super(name, new Priority("Countdown", (int) (finishDate.getTime() - System.currentTimeMillis() / 1000 / 60)));
		this.initDurationInSeconds = (finishDate.getTime() - System.currentTimeMillis() / 1000);
	}

	public long getInitDurationInSeconds() {
		return initDurationInSeconds;
	}
	
	public void decreaseOneSecond() {
		durationInSeconds--;
		timeString = Misc.convertFromSecondsToTimeString((int) durationInSeconds, true);
	}

	public String getTimeString() {
		return timeString;
	}

	public long getDurationInSeconds() {
		return durationInSeconds;
	}
	
}
