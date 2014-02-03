package de.netprojectev.client.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.Message;
import de.netprojectev.utils.LoggerBuilder;

public class ClientMessageHandler extends SimpleChannelInboundHandler<Message> {

	
	private static final Logger log = LoggerBuilder.createLogger(ClientMessageHandler.class);

	private final ClientMessageProxy proxy;
	
	public ClientMessageHandler(ClientMessageProxy proxy) {
		this.proxy = proxy;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", e.getCause());
		//TODO correct reconnect handling (means override the alias in use thing) proxy.reconnectForced();		
		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		proxy.receiveMessage((Message) msg);
	}
	
}
