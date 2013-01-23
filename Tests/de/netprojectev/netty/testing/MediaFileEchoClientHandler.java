package de.netprojectev.netty.testing;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.media.ImageFile;
import de.netprojectev.media.MediaFile;
import de.netprojectev.media.Priority;
import de.netprojectev.media.VideoFile;

public class MediaFileEchoClientHandler extends SimpleChannelHandler {


	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		MediaFile media = new VideoFile("huuuu", "/home/samu/");
		ChannelFuture f = e.getChannel().write(media);
		f.addListener(ChannelFutureListener.CLOSE);
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

}
