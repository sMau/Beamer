package de.netprojectev.networking;

import java.io.Serializable;

/**
 * Class to encapsulate messages transfered via network.
 */
public class Message {
	
	private final OpCode opCode;
	private Serializable[] data;
	
	public Message(OpCode opCode) {
		this.opCode = opCode;
	}
	
	public Message(OpCode opCode, Serializable... data) {
		this.opCode = opCode;
		this.data = data;
	}

	public OpCode getOpCode() {
		return opCode;
	}

	public Serializable[] getData() {
		return data;
	}
	
	public void setData(Serializable[] data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "Message " + "; OpCode: " + opCode + "; Data: " + data;
	}

	
	
}