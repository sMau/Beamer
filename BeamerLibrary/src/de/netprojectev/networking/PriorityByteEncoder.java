package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.datastructures.media.Priority;

public class PriorityByteEncoder extends MessageToByteEncoder<Priority> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Priority msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub

	}

}
