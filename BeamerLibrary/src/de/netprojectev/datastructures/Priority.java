package de.netprojectev.datastructures;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * Datastructure to hold the time to show connected with a identifier (uuid)
 * 
 * @author samu
 * 
 */
public class Priority implements Serializable {

	private static final long serialVersionUID = 4619160913893672095L;
	private final UUID id;
	private String name;
	private int minutesToShow;
	private boolean defaultPriority;

	/**
	 * 
	 * @param name
	 *            name of the priority
	 * @param minutesToShow
	 *            time to show the specific file holding this priority
	 */
	public Priority(String name, int minutesToShow) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.minutesToShow = minutesToShow;
		this.defaultPriority = false;
	}
	
	public static Priority reconstruct(String name, int minutesToShow, UUID id) {
		return new Priority(name, minutesToShow, id);
	}

	private Priority(String name, int minutesToShow, UUID id) {
		this.id = id;
		this.name = name;
		this.minutesToShow = minutesToShow;
		this.defaultPriority = false;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || this == null) {
			return false;
		} else if (other == this) {
			return true;
		} else if (other instanceof Priority) {
			return ((Priority) other).getId().equals(this.getId());
		} else {
			return false;
		}
	}

	public UUID getId() {
		return this.id;
	}

	public int getMinutesToShow() {
		return this.minutesToShow;
	}

	public String getName() {
		return this.name;
	}

	public long getTimeToShowInMilliseconds() {
		return this.minutesToShow * 60 * 1000;
	}

	public boolean isDefaultPriority() {
		return this.defaultPriority;
	}

	public void setDefaultPriority(boolean defaultPriority) {
		this.defaultPriority = defaultPriority;
	}

	public void setMinutesToShow(int minutesToShow) {
		this.minutesToShow = minutesToShow;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name + " - " + this.minutesToShow;
	}

}
