package de.netprojectev.netty.testing2;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class CommandMessageEncoder extends SimpleChannelHandler {
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
		CommandMessage ack = (CommandMessage) e.getMessage();
		System.out.println("client received: " + ack.getCommand());
		super.messageReceived(ctx, e);
	}
	

}
