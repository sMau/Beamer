package de.netprojectev.networking;

import java.util.ArrayList;



/**
 * Class to encapsulate messages transfered via network in the control flow.
 * Actual network messages are converted to plain byte streams.
 */
public class Message {
	
	private final OpCode opCode;
	private ArrayList<Object> data;
	
	public Message(OpCode opCode) {
		this.opCode = opCode;
	}
	
	public Message(OpCode opCode, Object... data) {
		this.opCode = opCode;
		this.data = new ArrayList<Object>();
		for(Object d : data) {
			this.data.add(d);
		}
	}

	public OpCode getOpCode() {
		return opCode;
	}

	public ArrayList<Object> getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Message " + "; OpCode: " + opCode + "; Data: " + data;
	}

	
	
}