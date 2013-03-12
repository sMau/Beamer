package de.netprojectev.server.datastructures.liveticker;

import java.io.Serializable;
import java.util.UUID;

import de.netprojectev.datastructures.media.MediaFile;

public class TickerElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7790234554937908704L;
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
	
	@Override
	public boolean equals(Object other){
	    if (other == null || this == null) {
	    	return false;
	    } else if(other == this){
	    	return true;
	    } else if(other instanceof TickerElement) {
	    	return ((TickerElement) other).getId().equals(this.getId());
	    } else {
	    	return false;
	    }
	}
}
