package de.netprojectev.netty.examples;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.netty.testing2.CommandMessage;

public class TimeEncoder extends SimpleChannelHandler {
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		CommandMessage msg = (CommandMessage) e.getMessage();
		
		System.out.println(msg.getCommand());
		
	}
	
}
