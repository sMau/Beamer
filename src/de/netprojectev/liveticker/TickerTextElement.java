package de.netprojectev.liveticker;

import java.io.Serializable;

/**
 * Datastructure to hold a text and a toShow flag, to be able to disable a element without deleting.
 * @author samu
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
		LiveTicker.getInstance().generateCompleteTickerString();
	}
	
	
	
}
