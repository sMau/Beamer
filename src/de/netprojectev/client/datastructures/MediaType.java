package de.netprojectev.client.datastructures;

public enum MediaType {

	Video("Video"), Image("Image"), Themeslide("Themeslide"), Countdown("Countdown"), Unknown("unknown");

	private final String text;

	private MediaType(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
