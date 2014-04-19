package de.netprojectev.networking.upstream;

import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class UUIDByteEncoder extends MessageToByteEncoder<UUID> {
	private static final Logger log = LoggerBuilder.createLogger(UUIDByteEncoder.class);
	private static final long uuidLength = 128;
	
	@Override
	protected void encode(ChannelHandlerContext ctx, UUID msg, ByteBuf out) throws Exception {
		out.writeLong(uuidLength);
		out.writeLong(msg.getLeastSignificantBits());
		out.writeLong(msg.getMostSignificantBits());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close(); //XXX
	}
}
