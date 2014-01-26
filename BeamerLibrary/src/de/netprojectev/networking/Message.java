package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class Message implements ByteEncodable {

	/**
	 * Class to encapsulate messages transfered via network.
	 */
	
	private final OpCode opCode;
	private final UUID id;
	private ByteEncodable[] data;
	
	public Message(OpCode opCode) {
		this.id = UUID.randomUUID();
		this.opCode = opCode;
	}
	
	public Message(OpCode opCode, ByteEncodable... data) {
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

	public ByteEncodable[] getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return "Message: " + id + "; OpCode: " + opCode + "; Data: " + data;
	}

	@Override
	public ByteBuf encode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteEncodable decode(ByteBuf encodedBytes) {
		// TODO Auto-generated method stub
		return null;
	}
	
}