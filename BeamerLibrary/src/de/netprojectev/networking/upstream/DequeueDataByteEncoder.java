package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.networking.DequeueData;

public class DequeueDataByteEncoder extends MessageToByteEncoder<DequeueData> {

	@Override
	protected void encode(ChannelHandlerContext ctx, DequeueData msg, ByteBuf out) throws Exception {
		ctx.write(msg.getId());
		ctx.writeAndFlush(msg.getRow());
	}

}
