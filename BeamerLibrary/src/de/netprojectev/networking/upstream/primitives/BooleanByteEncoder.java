package de.netprojectev.networking.upstream.primitives;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class BooleanByteEncoder extends MessageToByteEncoder<Boolean> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Boolean msg, ByteBuf out) throws Exception {
		out.writeLong(1);
		out.writeBoolean(msg);
	}

}
