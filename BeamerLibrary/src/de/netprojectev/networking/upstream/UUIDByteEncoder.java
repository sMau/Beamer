package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.UUID;
import java.util.logging.Level;

import de.netprojectev.utils.LoggerBuilder;

public class UUIDByteEncoder extends MessageToByteEncoder<UUID> {
	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(UUIDByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, UUID msg, ByteBuf out) throws Exception {
		out.writeLong(msg.getLeastSignificantBits());
		out.writeLong(msg.getMostSignificantBits());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
