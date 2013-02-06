package de.netprojectev.old.server.datastructures.liveticker;

import java.io.Serializable;

/**
 * Datastructure to hold a text and a toShow flag, to be able to disable a element without deleting.
 * @author samu
 *
 */
public class TickerTextElement implements Serializable {
	
	private static final long serialVersionUID = 1379588953203246516L;
	private String text;
	private boolean toShow;
	
	
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
	public void setToShow(boolean toShow) {
		this.toShow = toShow;
		
		// TODO changing to show does not have a direct affect, corrupted after refactoring
		// will only change after manual refresh
		
		//LiveTicker.getInstance().generateCompleteTickerString();
	}
	
	
	
}
