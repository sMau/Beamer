package de.netprojectev.networking;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;


public class OpCodeByteCodec extends ByteToMessageCodec<OpCode> {

	@Override
	protected void encode(ChannelHandlerContext ctx, OpCode msg, ByteBuf out)
			throws Exception {
		out.writeByte(msg.ordinal());
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		out.add(OpCode.values()[(int) in.readByte()]);
	}

}
