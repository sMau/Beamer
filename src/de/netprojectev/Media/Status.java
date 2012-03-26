package de.netprojectev.Media;

import java.util.Date;

/**
 * 
 * Datenstruktur zur Verwaltung des aktuellen Status einer Medien Datei.
 *
 */
public class Status {

	private Boolean isCurrent;
	private Boolean wasShowed;
	private Date showAt;

	public Status() {
		this.isCurrent = false;
		this.wasShowed = false;

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
