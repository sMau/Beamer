package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class IntegerByteCodec extends ByteToMessageCodec<Integer> {

	private static final Logger log = LoggerBuilder.createLogger(IntegerByteCodec.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.attr(HandlerState.INTEGER_CODEC_STATE).set(HandlerState.READ_OPCODE);
		super.channelActive(ctx);
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out)
			throws Exception {
		out.writeInt(msg);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if(ctx.attr(HandlerState.INTEGER_CODEC_STATE).compareAndSet(HandlerState.READ_DATA_LENGTH, HandlerState.READ_DATA)) {
			out.add(in.readInt());
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close();
	}

}