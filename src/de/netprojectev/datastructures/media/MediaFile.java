package de.netprojectev.datastructures.media;

import java.io.Serializable;
import java.util.UUID;


public abstract class MediaFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6210917731163785789L;
	private final UUID id;
	protected String name;
	protected Priority priority;
	protected boolean current;
	protected int showCount;
	
	protected MediaFile(String name, Priority priority, UUID id) {
		this.id = id;
		this.priority = priority;
		this.name = name;
	}
	
	protected MediaFile(String name, UUID id) {
		this.id = id;
		this.name = name;
		this.priority = new Priority("default", 1); //TODO add real default priority via prefs and so on
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null || this == null) {
	    	return false;
	    } else if(other == this){
	    	return true;
	    } else if(other instanceof MediaFile) {
	    	return ((MediaFile) other).getId().equals(this.getId());
	    } else {
	    	return false;
	    }
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public boolean isCurrent() {
		return current;
	}

	public MediaFile setCurrent(boolean current) {
		this.current = current;
		return this;
	}

	public int getShowCount() {
		return showCount;
	}

	public MediaFile increaseShowCount() {
		
		showCount++;
		
		return this;
	}
	

}
