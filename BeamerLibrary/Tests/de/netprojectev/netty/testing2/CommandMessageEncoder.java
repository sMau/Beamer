package de.netprojectev.netty.testing2;

import io.netty.channel.ChannelHandlerContext;

public class CommandMessageEncoder extends SimpleChannelHandler {
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
		CommandMessage ack = (CommandMessage) e.getMessage();
		System.out.println("client received: " + ack.getCommand());
		super.messageReceived(ctx, e);
	}
	

}
