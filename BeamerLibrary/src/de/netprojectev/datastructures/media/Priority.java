package de.netprojectev.datastructures.media;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * Datastructure to hold the time to show connected with a identifier (uuid)
 * @author samu
 *
 */
public class Priority implements Serializable {

	private static final long serialVersionUID = 4619160913893672095L;
	private UUID id;
	private String name;
	private int minutesToShow;
	private boolean defaultPriority;

	/**
	 * 
	 * @param name name of the priority
	 * @param minutesToShow time to show the specific file holding this priority
	 */
	public Priority(String name, int minutesToShow) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.minutesToShow = minutesToShow;
		this.defaultPriority = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinutesToShow() {
		return minutesToShow;
	}

	public void setMinutesToShow(int minutesToShow) {
		this.minutesToShow = minutesToShow;
	}

	public UUID getId() {
		return id;
	}
	
	public long getTimeToShowInMilliseconds() {
		return minutesToShow*60*1000;
	}
	
	@Override
	public String toString() {
		return name + " - " + minutesToShow;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null || this == null) {
	    	return false;
	    } else if(other == this){
	    	return true;
	    } else if(other instanceof Priority) {
	    	return ((Priority) other).getId().equals(this.getId());
	    } else {
	    	return false;
	    }
	}

	public boolean isDefaultPriority() {
		return defaultPriority;
	}

	public void setDefaultPriority(boolean defaultPriority) {
		this.defaultPriority = defaultPriority;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
