package de.netprojectev.netty.examples;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class SimpleExampleServerHandler extends SimpleChannelHandler {

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		UnixTime time = new UnixTime((int) (System.currentTimeMillis() / 1000));
		ChannelFuture f = e.getChannel().write(time);
		f.addListener(ChannelFutureListener.CLOSE);

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Channel ch = e.getChannel();
		ch.write(e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		SimpleExampleServer.allChannels.add(e.getChannel());

	}
}
