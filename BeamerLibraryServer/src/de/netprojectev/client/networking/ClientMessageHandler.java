package de.netprojectev.client.networking;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;

public class ClientMessageHandler extends SimpleChannelHandler {

	
	private static final Logger log = LoggerBuilder.createLogger(ClientMessageHandler.class);

	private final ClientMessageProxy proxy;
	
	public ClientMessageHandler(ClientMessageProxy proxy) {
		this.proxy = proxy;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Message msg = (Message) e.getMessage();
		proxy.receiveMessage(msg);
		// super.messageReceived(ctx, e);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", e.getCause());
		//TODO correct reconnect handling (means override the alias in use thing) proxy.reconnectForced();		
		e.getChannel().close();
	}
	
}