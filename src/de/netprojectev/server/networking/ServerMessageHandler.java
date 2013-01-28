package de.netprojectev.server.networking;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.Misc;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;

public class ServerMessageHandler extends SimpleChannelHandler {
	
	private static final Logger LOG = Misc.getLoggerAll(ServerMessageHandler.class.getName());
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Server.getAllclients().add(e.getChannel());
		LOG.log(Level.INFO, "Client connected");
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
	
		e.getChannel().write(new Message(OpCode.CONNECTION_ACK));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		
		LOG.log(Level.WARNING, "connection error, closing the connection now." + e.getCause());
		e.getChannel().close();
	}
}
