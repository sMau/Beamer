package de.netprojectev.server.datastructures;

import java.io.Serializable;
import java.util.UUID;

public class ServerTickerElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7790234554937908704L;
	private UUID id;
	private String text;
	private boolean show;
	
	public ServerTickerElement(String text) {
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
	    } else if(other instanceof ServerTickerElement) {
	    	return ((ServerTickerElement) other).getId().equals(this.getId());
	    } else {
	    	return false;
	    }
	}
}
