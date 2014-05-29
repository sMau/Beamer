package de.netprojectev.client.datastructures;

public enum MediaType {

	Image("Image"), Themeslide("Themeslide"), Countdown("Countdown"), Video("Video"), Unknown("unknown");

	private final String text;

	private MediaType(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
