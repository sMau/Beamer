package de.netprojectev.networking;

import java.io.Serializable;

public final class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5081391059509425751L;
	
	private final byte command;
	private final Serializable data;
	
	public Message(byte command) {
		this.command = command;
		this.data = null;
	}
	
	public Message(byte command, Serializable data) {
		this.command = command;
		this.data = data;
	}
	
	public byte getCommand() {
		return command;
	}
	public Serializable getData() {
		return data;
	}
}
