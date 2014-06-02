package de.netprojectev.networking;

import java.util.ArrayList;

/**
 * Class to encapsulate messages transfered via network in the control flow.
 * Actual network messages are converted to plain byte streams.
 */
public class Message {

	private final OpCode opCode;
	private ArrayList<Object> data = new ArrayList<Object>();

	public Message(OpCode opCode) {
		this.opCode = opCode;
	}

	public Message(OpCode opCode, ArrayList<Object> data) {
		this.opCode = opCode;
		for (Object d : data) {
			this.data.add(d);
		}
	}
	
	public Message(OpCode opCode, Object... data) {
		this.opCode = opCode;
		for (Object d : data) {
			this.data.add(d);
		}
	}

	public ArrayList<Object> getData() {
		return this.data;
	}

	public OpCode getOpCode() {
		return this.opCode;
	}

	@Override
	public String toString() {
		return "Message " + "; OpCode: " + this.opCode + "; Data: " + this.data;
	}

}