package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import de.netprojectev.datastructures.Priority;
import de.netprojectev.utils.LoggerBuilder;

import java.util.logging.Level;

public class PriorityByteEncoder extends MessageToByteEncoder<Priority> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(PriorityByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Priority msg, ByteBuf out) throws Exception {

		ctx.write(msg.getId());
		ctx.write(msg.getMinutesToShow());
		ctx.write(msg.getName());
		ctx.writeAndFlush(msg.isDefaultPriority());

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
