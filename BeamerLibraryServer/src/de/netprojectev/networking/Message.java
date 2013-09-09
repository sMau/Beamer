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
	private Serializable[] data;
	
	public Message(OpCode opCode) {
		this.id = UUID.randomUUID();
		this.opCode = opCode;
	}
	
	public Message(OpCode opCode, Serializable... data) {
		this.id = UUID.randomUUID();
		this.opCode = opCode;
		this.data = data;
	}

	public OpCode getOpCode() {
		return opCode;
	}

	public UUID getId() {
		return id;
	}

	public Serializable[] getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return "Message: " + id + "; OpCode: " + opCode + "; Data: " + data;
	}
	
}