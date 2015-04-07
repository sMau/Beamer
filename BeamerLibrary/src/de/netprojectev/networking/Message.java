package de.netprojectev.networking;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to encapsulate messages transfered via network in the control flow.
 * Actually network messages are converted to plain byte streams.
 */
public class Message implements Serializable {

	private final OpCode opCode;
	private ArrayList<Serializable> data = new ArrayList<Serializable>();

	public Message(OpCode opCode) {
		this.opCode = opCode;
	}

	public Message(OpCode opCode, ArrayList<Serializable> data) {
		this.opCode = opCode;
		for (Serializable d : data) {
			this.data.add(d);
		}
	}

	public Message(OpCode opCode, Serializable... data) {
		this.opCode = opCode;
		for (Serializable d : data) {
			this.data.add(d);
		}
	}

	public ArrayList<Serializable> getData() {
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