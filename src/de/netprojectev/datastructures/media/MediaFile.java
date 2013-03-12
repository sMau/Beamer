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
		//TODO add default priority
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

}
