package de.netprojectev.datastructures;

import java.io.Serializable;
import java.util.UUID;

public abstract class MediaFile implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6210917731163785789L;
	protected final UUID id;
	protected String name;
	protected UUID priorityID;
	protected transient boolean current;
	protected transient int showCount;

	protected MediaFile(String name, UUID id, UUID priorityID) {
		this.id = id;
		this.priorityID = priorityID;
		this.name = name;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || this == null) {
			return false;
		} else if (other == this) {
			return true;
		} else if (other instanceof MediaFile) {
			return ((MediaFile) other).getId().equals(this.getId());
		} else {
			return false;
		}
	}

	public UUID getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public UUID getPriorityID() {
		return this.priorityID;
	}

	public int getShowCount() {
		return this.showCount;
	}

	public MediaFile increaseShowCount() {

		this.showCount++;

		return this;
	}

	public boolean isCurrent() {
		return this.current;
	}

	public void resetShowCount() {
		this.showCount = 0;
	}

	public MediaFile setCurrent(boolean current) {
		this.current = current;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPriority(UUID priorityID) {
		this.priorityID = priorityID;
	}

}
