package de.netprojectev.server.networking;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.Misc;
import de.netprojectev.networking.Message;

public class MessageHandlerServer extends SimpleChannelHandler {
	
	private static final Logger LOG = Misc.getLoggerAll(MessageHandlerServer.class.getName());
	
	private final MessageProxyServer proxy;
	
	public MessageHandlerServer(MessageProxyServer proxy) {
		super();
		this.proxy = proxy;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Message received = (Message) e.getMessage();
		proxy.receiveMessage(received);
		LOG.log(Level.INFO, "Message received " + received);
		//super.messageReceived(ctx, e);
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
		//TODO
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		
		// TODO Auto-generated method stub
		
		LOG.log(Level.WARNING, "Exception caught ", e.getCause());
		e.getChannel().close();
	}
}
