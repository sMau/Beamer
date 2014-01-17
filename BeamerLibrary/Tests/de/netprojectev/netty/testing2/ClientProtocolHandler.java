package de.netprojectev.netty.testing2;

import io.netty.channel.ChannelHandlerContext;

public class ClientProtocolHandler extends SimpleChannelHandler {
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.writeRequested(ctx, e);
	}
}
