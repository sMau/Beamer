package de.netprojectev.networking;


public class Message<T> {

	/**
	 * Class to encapsulate messages transfered via network.
	 */
	
	private final OpCode opCode;
	private T[] data;
	
	public Message(OpCode opCode) {
		this.opCode = opCode;
	}
	
	public Message(OpCode opCode, T... data) {
		this.opCode = opCode;
		this.data = data;
	}

	public OpCode getOpCode() {
		return opCode;
	}

	public T[] getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return "OpCode: " + opCode + "; Data: " + data;
	}
	
}