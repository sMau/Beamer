package de.netprojectev.networking;



/**
 * Class to encapsulate messages transfered via network in the control flow.
 * Actual network messages are converted to plain byte streams.
 */
public class Message {
	
	private final OpCode opCode;
	private Object[] data;
	
	public Message(OpCode opCode) {
		this.opCode = opCode;
	}
	
	public Message(OpCode opCode, Object... data) {
		this.opCode = opCode;
		this.data = data;
	}

	public OpCode getOpCode() {
		return opCode;
	}

	public Object[] getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Message " + "; OpCode: " + opCode + "; Data: " + data;
	}

	
	
}