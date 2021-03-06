package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import de.netprojectev.networking.DequeueData;
import de.netprojectev.utils.LoggerBuilder;

import java.util.logging.Level;

public class DequeueDataByteEncoder extends MessageToByteEncoder<DequeueData> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(DequeueDataByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, DequeueData msg, ByteBuf out) throws Exception {
		ctx.write(msg.getId());
		ctx.writeAndFlush(msg.getRow());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
