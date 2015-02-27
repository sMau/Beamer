package de.netprojectev.networking.upstream.primitives;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import de.netprojectev.utils.LoggerBuilder;

import java.util.logging.Level;

public class StringByteEncoder extends MessageToByteEncoder<String> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(StringByteEncoder.class);

	/**
	 * encodes a string into an byte array. First a long is written which
	 * represents the length. Then the data bytes are written.
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		ctx.writeAndFlush(msg.getBytes());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
