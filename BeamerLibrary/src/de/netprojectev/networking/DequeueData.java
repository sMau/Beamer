package de.netprojectev.networking;

import java.io.Serializable;
import java.util.UUID;

public class DequeueData implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 5472644686787312647L;
	private final int row;
	private final UUID id;

	public DequeueData(int row, UUID id) {
		this.row = row;
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public UUID getId() {
		return id;
	}
}
