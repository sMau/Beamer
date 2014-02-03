package de.netprojectev.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.Serializable;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class MessageJoin extends ChannelInboundHandlerAdapter {
	
	private static final Logger log = LoggerBuilder.createLogger(MessageJoin.class);
	
	private Message joinedMsg;
	private Serializable[] data;
	private int index = 0;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.attr(HandlerState.JOIN_CODEC_STATE).set(HandlerState.READ_OPCODE);
		super.channelActive(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		switch (ctx.attr(HandlerState.JOIN_CODEC_STATE).get()) {
		case READ_OPCODE:
			if(msg instanceof OpCode) {
				joinedMsg = new Message((OpCode) msg);
				ctx.attr(HandlerState.JOIN_CODEC_STATE).set(HandlerState.READ_DATA_LENGTH);
			}
			break;
		case READ_DATA_LENGTH:
			if(msg instanceof Integer) {
				int length = (Integer) msg;
				if(length < 1) {
					ctx.attr(HandlerState.JOIN_CODEC_STATE).set(HandlerState.READ_OPCODE);
					ctx.attr(HandlerState.OPCODE_CODEC_STATE).set(HandlerState.READ_OPCODE);
					ctx.attr(HandlerState.INTEGER_CODEC_STATE).set(HandlerState.READ_OPCODE);
					super.channelRead(ctx, joinedMsg);
				} else {
					data = new Serializable[length];
					ctx.attr(HandlerState.JOIN_CODEC_STATE).set(HandlerState.READ_DATA);
				}
			}
			break;
		case READ_DATA:
			if(msg instanceof Serializable) {
				data[index] = (Serializable) msg;
				if(index == data.length - 1) {
					ctx.attr(HandlerState.JOIN_CODEC_STATE).set(HandlerState.READ_OPCODE);
					ctx.attr(HandlerState.OPCODE_CODEC_STATE).set(HandlerState.READ_OPCODE);
					ctx.attr(HandlerState.INTEGER_CODEC_STATE).set(HandlerState.READ_OPCODE);
					index = 0;
					joinedMsg.setData(data.clone());
					super.channelRead(ctx, joinedMsg);
				} else {
					index++;
				}
			}
			break;
		default:
			super.channelRead(ctx, msg);
			break;
		}
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close();
	}

}
