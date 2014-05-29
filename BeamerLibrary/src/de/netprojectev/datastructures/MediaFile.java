package de.netprojectev.datastructures;

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
	protected transient boolean current;
	protected transient int showCount;
	
	protected MediaFile(String name, Priority priority, UUID id) {
		this.id = id;
		this.priority = priority;
		this.name = name;
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
	
	public void resetShowCount() {
		showCount = 0;
	}

	public MediaFile increaseShowCount() {
		
		showCount++;
		
		return this;
	}
	

}
