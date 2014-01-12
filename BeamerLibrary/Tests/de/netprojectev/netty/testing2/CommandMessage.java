package de.netprojectev.netty.testing2;

import java.io.Serializable;

public class CommandMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9156290095703625232L;
	private byte command;
	private Serializable data;
	
	public CommandMessage(byte command) {
		this.command = command;
	}
	
	public byte getCommand() {
		return command;
	}

	public Serializable getData() {
		return data;
	}
}
