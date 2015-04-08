package de.netprojectev.common.networking;

import java.io.Serializable;
import java.util.UUID;

public class DequeueData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5472644686787312647L;
	private final int position;
	private final UUID id;

	public DequeueData(int position, UUID id) {
		this.position = position;
		this.id = id;
	}

	public UUID getId() {
		return this.id;
	}

	public int getPosition() {
		return this.position;
	}
}
