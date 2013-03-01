package de.netprojectev.server.datastructures.liveticker;

import java.util.UUID;

public class TickerElement {
	
	private UUID id;
	private String text;
	private boolean show;
	
	public TickerElement(String text) {
		id = UUID.randomUUID();
		this.text = text;
		this.show = true;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public UUID getId() {
		return id;
	}

	public String getText() {
		return text;
	}
}
