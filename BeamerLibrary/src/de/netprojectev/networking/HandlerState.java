package de.netprojectev.networking;

import io.netty.util.AttributeKey;

public enum HandlerState {
	
	READ_OPCODE,
	READ_DATA_LENGTH,
	READ_DATA;
	
	public static final AttributeKey<HandlerState> OPCODE_CODEC_STATE = AttributeKey.valueOf("opcode.state");
	public static final AttributeKey<HandlerState> INTEGER_CODEC_STATE = AttributeKey.valueOf("integer.state");
	public static final AttributeKey<HandlerState> JOIN_CODEC_STATE = AttributeKey.valueOf("join.state");

}
