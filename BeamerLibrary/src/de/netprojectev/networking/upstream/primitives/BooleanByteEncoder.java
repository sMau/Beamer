package de.netprojectev.networking.upstream.primitives;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import de.netprojectev.utils.LoggerBuilder;

public class BooleanByteEncoder extends MessageToByteEncoder<Boolean> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(BooleanByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Boolean msg, ByteBuf out) throws Exception {
		out.writeBoolean(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warning("Exception caught in channel handler " + getClass() + "\n" + cause.toString());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
