package de.netprojectev.old.datastructures.media;

import java.util.Date;

/**
 * 
 * Datastructure to manage current status of a media file.
 */
public class Status {
	
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
