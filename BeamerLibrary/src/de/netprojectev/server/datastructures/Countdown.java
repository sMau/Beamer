package de.netprojectev.server.datastructures;

import java.io.IOException;
import java.util.UUID;

import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.utils.HelperMethods;

public class Countdown extends ServerMediaFile {

	/**
	 *
	 */
	public static final UUID COUNTDOWN_PRIORITY = new UUID(1337, 1337);

	private final static long serialVersionUID = -4353683190496476107L;
	private long initDurationInSeconds;
	private long durationInSeconds;

	private String timeString;

	public Countdown(String name, int durationInMinutes) {
		super(name, COUNTDOWN_PRIORITY);

		if (durationInMinutes <= 0) {
			throw new IllegalArgumentException(
					"duration have to be bigger than 0");
		}
		this.initDurationInSeconds = durationInMinutes * 60;
		this.durationInSeconds = this.initDurationInSeconds;
		this.timeString = HelperMethods.convertFromSecondsToTimeString(
				(int) this.durationInSeconds, true);

	}

	public void decreaseOneSecond() {
		this.durationInSeconds--;
		this.timeString = HelperMethods.convertFromSecondsToTimeString(
				(int) this.durationInSeconds, true);
		if (this.durationInSeconds < 0) {
			this.durationInSeconds = this.initDurationInSeconds;
		}
	}

	@Override
	public MediaType determineMediaType() {
		return MediaType.Countdown;
	}

	@Override
	public byte[] determinePreview() throws IOException {
		return super.getNoPreviewImage();
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
