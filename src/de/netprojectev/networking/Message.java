package de.netprojectev.networking;

import java.io.Serializable;
import java.util.UUID;

public abstract class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5081391059509425751L;
	
	private final Enum<OpCode> opCode;
	private final UUID id;
	
	protected Message(Enum<OpCode> opCode, UUID id) {
		this.id = id;
		this.opCode = opCode;
	}

	public Enum<OpCode> getOpCode() {
		return opCode;
	}

	public UUID getId() {
		return id;
	}
	
}