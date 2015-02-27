package de.netprojectev.networking.upstream.primitives;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import de.netprojectev.networking.OpCode;
import de.netprojectev.utils.LoggerBuilder;

import java.util.logging.Level;

public class OpCodeByteEncoder extends MessageToByteEncoder<OpCode> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(OpCodeByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, OpCode msg, ByteBuf out)
			throws Exception {
		out.writeByte(msg.ordinal());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
