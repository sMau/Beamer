package de.netprojectev.networking;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class IntegerByteCodec extends ByteToMessageCodec<Integer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out)
			throws Exception {
		out.writeInt(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		out.add(in.readInt());
	}

}
