package de.netprojectev.networking;

import java.util.List;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;


public class OpCodeByteCodec extends ByteToMessageCodec<OpCode> {
	private static final Logger log = LoggerBuilder.createLogger(OpCodeByteCodec.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, OpCode msg, ByteBuf out)
			throws Exception {
		out.writeByte(msg.ordinal());
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		out.add(OpCode.values()[(int) in.readByte()]);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close();
	}
}
