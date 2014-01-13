package de.netprojectev.server.networking;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.utils.LoggerBuilder;

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
			
			proxy.clientDisconnected(e.getChannel(), (String) received.getData()[0]);
		} else {
			proxy.receiveMessage(received, e.getChannel());
		}
		log.debug("Message received: " + received);
		
	}
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.writeRequested(ctx, e);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		log.warn("Exception caught in MessageHandler", e.getCause());
		proxy.clientTimedOut(ctx.getChannel());
		ctx.getChannel().write(new Message(OpCode.STC_FORCE_RECONNECT));
		e.getChannel().close();
	}
}
