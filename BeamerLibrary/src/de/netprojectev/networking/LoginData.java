package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;

public class LoginData implements ByteEncodable {

	private final String alias;
	private final String key;
	
	public LoginData(String alias, String key) {
		this.alias = alias;
		this.key = key;
	}

	public String getAlias() {
		return alias;
	}

	public String getKey() {
		return key;
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
