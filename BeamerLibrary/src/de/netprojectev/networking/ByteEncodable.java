package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;

public interface ByteEncodable {

	public ByteBuf encode();
	public ByteEncodable decode(ByteBuf encodedBytes);
	
}
