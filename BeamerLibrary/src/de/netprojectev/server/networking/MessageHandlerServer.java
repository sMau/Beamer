package de.netprojectev.server.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.utils.LoggerBuilder;

public class MessageHandlerServer extends ChannelInboundHandlerAdapter {
	
	private static final Logger log = LoggerBuilder.createLogger(MessageHandlerServer.class); 
	
	private final MessageProxyServer proxy;
	
	public MessageHandlerServer(MessageProxyServer proxy) {
		super();
		this.proxy = proxy;
		
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message received = (Message) msg;
		if(received.getOpCode().equals(OpCode.CTS_DISCONNECT)) {
			
			proxy.clientDisconnected(ctx.channel(), (String) received.getData()[0]);
		} else {
			proxy.receiveMessage(received, ctx.channel());
		}
		log.debug("Message received: " + received);
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		log.warn("Exception caught in MessageHandler", e.getCause());
		proxy.clientTimedOut(ctx.channel());
		ctx.channel().write(new Message(OpCode.STC_FORCE_RECONNECT));
		ctx.channel().close();
	}
}
