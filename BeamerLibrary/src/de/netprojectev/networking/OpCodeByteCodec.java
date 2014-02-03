package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;



public class OpCodeByteCodec extends ByteToMessageCodec<OpCode> {
	
	private static final Logger log = LoggerBuilder.createLogger(OpCodeByteCodec.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.attr(HandlerState.OPCODE_CODEC_STATE).set(HandlerState.READ_OPCODE);
		super.channelActive(ctx);
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, OpCode msg, ByteBuf out)
			throws Exception {
		out.writeByte(msg.ordinal());
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		if(ctx.attr(HandlerState.OPCODE_CODEC_STATE).compareAndSet(HandlerState.READ_OPCODE, HandlerState.READ_DATA_LENGTH)) {
			ctx.attr(HandlerState.INTEGER_CODEC_STATE).set(HandlerState.READ_DATA_LENGTH);
			out.add(OpCode.values()[(int) in.readByte()]);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close();
	}
}
