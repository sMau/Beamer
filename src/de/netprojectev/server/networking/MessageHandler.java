package de.netprojectev.server.networking;

import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.Misc;

public class MessageHandler extends SimpleChannelHandler {
	
	private static final Logger LOG = Misc.getLoggerAll(MessageHandler.class.getName());
	
	private final MessageProxy proxy;
	
	public MessageHandler(MessageProxy proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(ctx, e);
	}
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.writeRequested(ctx, e);
	}
	

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		
		// TODO Auto-generated method stub
		e.getChannel().close();
	}
}
