package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.datastructures.Priority;

public class PriorityByteEncoder extends MessageToByteEncoder<Priority> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Priority msg, ByteBuf out) throws Exception {

		out.writeInt(4); // object count
		ctx.write(msg.getId());
		ctx.write(msg.getMinutesToShow());
		ctx.write(msg.getName());
		ctx.writeAndFlush(msg.isDefaultPriority());

	}

}
