package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.server.datastructures.Countdown;

public class CountdownByteEncoder extends MessageToByteEncoder<Countdown> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Countdown msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub

	}

}
