package de.netprojectev.netty.testing;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.old.datastructures.media.Status;
import de.netprojectev.old.server.datastructures.media.ServerMediaFile;

public class MediaFileEchoServerHandler extends SimpleChannelHandler {
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		ServerMediaFile media = (ServerMediaFile) e.getMessage();
		e.getChannel().close();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

}
