package de.netprojectev.networking.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.networking.Message;

public class ClientMessageHandler extends SimpleChannelHandler {

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
		Message msg = (Message) e.getMessage();
		System.out.println(msg.getOpCode());
		super.messageReceived(ctx, e);
	}
	
	
}
