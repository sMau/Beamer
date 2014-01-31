package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class OpCodeEncoder extends MessageToByteEncoder<OpCode> {

	@Override
	protected void encode(ChannelHandlerContext ctx, OpCode msg, ByteBuf out)
			throws Exception {
		out.writeByte(msg.ordinal());
	}

}
