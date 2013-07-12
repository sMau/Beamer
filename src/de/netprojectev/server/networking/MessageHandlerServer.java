package de.netprojectev.server.networking;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;

public class MessageHandlerServer extends SimpleChannelHandler {
	
	private static final Logger log = LoggerBuilder.createLogger(MessageHandlerServer.class); 
	
	private final MessageProxyServer proxy;
	
	public MessageHandlerServer(MessageProxyServer proxy) {
		super();
		this.proxy = proxy;
		
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Message received = (Message) e.getMessage();
		if(received.getOpCode().equals(OpCode.CTS_DISCONNECT)) {
			proxy.clientDisconnected(e.getChannel());
		} else {
			proxy.receiveMessage(received);
		}
		log.debug("Message received: " + received);
		
	}
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.writeRequested(ctx, e);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		log.warn("Exception caught in MessageHandler", e.getCause());
		
		//TODO check if closing is the correct behavior
		e.getChannel().close();
	}
}
