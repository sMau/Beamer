package de.netprojectev.client.networking;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.Misc;
import de.netprojectev.networking.Message;

public class ClientMessageHandler extends SimpleChannelHandler {

	private static final Logger LOG = Misc.getLoggerAll(ClientMessageHandler.class.getName());

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		
		Message msg = (Message) e.getMessage();
		super.messageReceived(ctx, e);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		LOG.log(Level.WARNING, "Exception caught", e.getCause());
	}
	
}
