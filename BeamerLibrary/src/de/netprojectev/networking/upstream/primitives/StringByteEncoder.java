package de.netprojectev.networking.upstream.primitives;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class StringByteEncoder extends MessageToByteEncoder<String> {

	/**
	 * encodes a string into an byte array. First a long is written which
	 * represents the length. Then the data bytes are written.
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		ctx.writeAndFlush(msg.getBytes());
	}

}
