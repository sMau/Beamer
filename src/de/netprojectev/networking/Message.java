package de.netprojectev.networking;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {

	/**
	 * Class to encapsulate messages transfered via network.
	 */
	private static final long serialVersionUID = 5081391059509425751L;
	
	private final OpCode opCode;
	private final UUID id;
	private Serializable data;
	
	public Message(OpCode opCode, UUID id) {
		this.id = id;
		this.opCode = opCode;
	}
	
	public Message(OpCode opCode, UUID id, Serializable data) {
		this.id = id;
		this.opCode = opCode;
		this.data = data;
	}

	public OpCode getOpCode() {
		return opCode;
	}

	public UUID getId() {
		return id;
	}

	public Object getData() {
		return data;
	}
	
}