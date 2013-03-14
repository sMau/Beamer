package de.netprojectev.datastructures.media;

import java.io.Serializable;
import java.util.UUID;

/**
 * 
 * Datastructure to hold the time to show connected with a identifier (name)
 * @author samu
 *
 */
public class Priority implements Serializable {

	private static final long serialVersionUID = 4619160913893672095L;
	private final UUID id;
	private String name;
	private int minutesToShow;

	/**
	 * 
	 * @param name name of the priority
	 * @param minutesToShow time to show the specific file holding this priority
	 */
	public Priority(String name, int minutesToShow) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.minutesToShow = minutesToShow;
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
}
