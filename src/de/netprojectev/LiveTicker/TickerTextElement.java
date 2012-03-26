package de.netprojectev.LiveTicker;

import java.io.Serializable;

/**
 * 
 * Eine Datenstruktur um ein Textelement sowie den Boolean toShow zu verknüpfen.
 *
 */
public class TickerTextElement implements Serializable {
	
	private static final long serialVersionUID = 1379588953203246516L;
	private String text;
	private Boolean toShow;
	
	public TickerTextElement(String text) {
		this.text = text;
		this.toShow = true;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getToShow() {
		return toShow;
	}
	public void setToShow(Boolean toShow) {
		this.toShow = toShow;
	}
	
	
	
}
