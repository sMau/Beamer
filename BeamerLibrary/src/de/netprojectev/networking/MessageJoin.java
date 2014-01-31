package de.netprojectev.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageJoin extends ChannelInboundHandlerAdapter {

	private byte state = WAITING;
	
	public static final byte WAITING = 0;
	public static final byte READ_OPCODE = 1;
	public static final byte READ_DATA_LENGTH = 2;
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		switch (state) {
		case WAITING:
			
			break;
		case READ_OPCODE:
			
			break;
		case READ_DATA_LENGTH:
			
			break;
		default:
			
			break;
		}
		
		
		super.channelRead(ctx, msg);
	}
	
	
	
	
}
