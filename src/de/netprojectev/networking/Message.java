package de.netprojectev.networking;

import java.io.Serializable;

public final class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5081391059509425751L;
	
	private final Enum<OpCode> opCode;
	private final Serializable data;
	
	public Message(Enum<OpCode> command) {
		this.opCode = command;
		this.data = null;
	}
	
	public Message(Enum<OpCode> command, Serializable data) {
		this.opCode = command;
		this.data = data;
	}
	
	public Enum<OpCode> getOpCode() {
		return opCode;
	}
	public Serializable getData() {
		return data;
	}
}