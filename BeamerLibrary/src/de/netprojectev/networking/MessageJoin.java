package de.netprojectev.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.Serializable;

public class MessageJoin extends ChannelInboundHandlerAdapter {

	private byte state = WAITING;
	
	public static final byte WAITING = 0;
	public static final byte READ_OPCODE = 1;
	public static final byte READ_DATA_LENGTH = 2;
	
	private Message joinedMsg;
	private Serializable[] data;
	private int index = 0;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		switch (state) {
		case WAITING:
			if(msg instanceof OpCode) {
				joinedMsg = new Message((OpCode) msg);
				state = READ_OPCODE;
			}
			break;
		case READ_OPCODE:
			if(msg instanceof Integer) {
				int length = (Integer) msg;
				if(length < 1) {
					state = WAITING;
					super.channelRead(ctx, joinedMsg);
				} else {
					data = new Serializable[length];
					state = READ_DATA_LENGTH;
				}
			}
			break;
		case READ_DATA_LENGTH:
			if(msg instanceof Serializable) {
				data[index] = (Serializable) msg;
				if(index == data.length - 1) {
					state = WAITING;
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

}
