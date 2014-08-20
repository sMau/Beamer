package de.netprojectev.server.datastructures;

import java.util.UUID;

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
		super(name, UUID.randomUUID());

		if (durationInMinutes <= 0) {
			throw new IllegalArgumentException(
					"duration have to be bigger than 0");
		}
		this.initDurationInSeconds = durationInMinutes * 60;
		this.durationInSeconds = initDurationInSeconds;
		this.timeString = HelperMethods.convertFromSecondsToTimeString(
				(int) durationInSeconds, true);

	}

	public void decreaseOneSecond() {
		this.durationInSeconds--;
		this.timeString = HelperMethods.convertFromSecondsToTimeString(
				(int) this.durationInSeconds, true);
		if (this.durationInSeconds < 0) {
			this.durationInSeconds = this.initDurationInSeconds;
		}
	}

	public long getDurationInSeconds() {
		return this.durationInSeconds;
	}

	public long getInitDurationInSeconds() {
		return this.initDurationInSeconds;
	}

	public String getTimeString() {
		return this.timeString;
	}

}
