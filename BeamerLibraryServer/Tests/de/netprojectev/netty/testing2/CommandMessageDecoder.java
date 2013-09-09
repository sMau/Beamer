package de.netprojectev.netty.testing2;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class CommandMessageDecoder extends SimpleChannelHandler {
	
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		CommandMessage msg = (CommandMessage) e.getMessage();
		
		System.out.println("server received: " + msg.getCommand());
		e.getChannel().write(new CommandMessage((byte) 0));
	}
}
