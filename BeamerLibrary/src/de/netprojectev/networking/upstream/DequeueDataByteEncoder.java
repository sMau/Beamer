package de.netprojectev.networking.upstream;

import de.netprojectev.networking.DequeueData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DequeueDataByteEncoder extends MessageToByteEncoder<DequeueData> {

	@Override
	protected void encode(ChannelHandlerContext ctx, DequeueData msg, ByteBuf out) throws Exception {
		out.writeInt(2);
		ctx.write(msg.getId());
		ctx.writeAndFlush(msg.getRow());
	}

}
