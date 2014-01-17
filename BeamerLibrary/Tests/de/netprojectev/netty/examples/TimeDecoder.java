package de.netprojectev.netty.examples;

import io.netty.channel.ChannelHandlerContext;

public class TimeDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() < 4) {
			return null;
		}
		return new UnixTime(buffer.readInt());
	}

}
