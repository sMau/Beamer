package de.netprojectev.Media;

import java.util.Date;

/**
 * 
 * Datenstruktur zur Verwaltung des aktuellen Status einer Medien Datei.
 *
 */
public class Status {

	//TODO add a reset played states, invokable via gui
	//TODO set wasShowed after a given time, settable via prefs
	
	private Boolean isCurrent;
	private Boolean wasShowed;
	private Date showAt;

	
	public Status() {
		this.isCurrent = false;
		this.wasShowed = false;
		this.showAt = null;

	}

	public Boolean getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public Boolean getWasShowed() {
		return wasShowed;
	}

	public void setWasShowed(Boolean wasShowed) {
		this.wasShowed = wasShowed;
	}

	public Date getShowAt() {
		return showAt;
	}

	public void setShowAt(Date showAt) {
		this.showAt = showAt;
	}

}
