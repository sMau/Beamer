package de.netprojectev.netty.examples;

import io.netty.channel.ChannelHandlerContext;

public class SimpleExampleClientHandler extends SimpleChannelHandler {

	private final ChannelBuffer buf = ChannelBuffers.dynamicBuffer();

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		UnixTime m = (UnixTime) e.getMessage();
		System.out.println(m);
		e.getChannel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

}
