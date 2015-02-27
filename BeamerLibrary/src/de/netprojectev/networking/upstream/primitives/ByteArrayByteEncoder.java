package de.netprojectev.networking.upstream.primitives;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import de.netprojectev.utils.LoggerBuilder;

import java.util.logging.Level;

public class ByteArrayByteEncoder extends MessageToByteEncoder<byte[]> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(ByteArrayByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
		out.writeInt(msg.length);
		out.writeBytes(msg);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
