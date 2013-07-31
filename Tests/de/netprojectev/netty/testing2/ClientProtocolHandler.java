package de.netprojectev.netty.testing2;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ClientProtocolHandler extends SimpleChannelHandler {
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.writeRequested(ctx, e);
	}
}
