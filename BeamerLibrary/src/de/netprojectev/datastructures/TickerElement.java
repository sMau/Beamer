package de.netprojectev.datastructures;

import java.io.Serializable;
import java.util.UUID;

public class TickerElement implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7790234554937908704L;
	private final UUID id;
	private String text;
	private boolean show;
	
	public TickerElement(String text) {
		this.id = UUID.randomUUID();
		this.text = text;
		this.show = true;
	}
	
	public TickerElement(String text, UUID id) {
		this.id = id;
		this.text = text;
		this.show = true;
	}

	public boolean isShow() {
		return show;
	}

	public TickerElement setShow(boolean show) {
		this.show = show;
		return this;
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

	public void setText(String text) {
		this.text = text;
	}
}
