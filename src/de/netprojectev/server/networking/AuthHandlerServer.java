package de.netprojectev.server.networking;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.model.PreferencesModelServer;

public class AuthHandlerServer extends SimpleChannelHandler {

	private static final Logger log = LoggerBuilder.createLogger(AuthHandlerServer.class);

	private final MessageProxyServer proxy;

	private boolean authSuccessful;
	private boolean chanConnected;
	
	public AuthHandlerServer(MessageProxyServer proxy) {
		super();
		this.proxy = proxy;
		authSuccessful = false;
		chanConnected = false;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Message received = (Message) e.getMessage();
		if(chanConnected && received.getOpCode().equals(OpCode.LOGIN_REQUEST)) {
			
			LoginData login = (LoginData) received.getData();
			
			if(login.getKey().equals(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_SERVER_PW))) {
				proxy.clientConnected(e.getChannel());
				authSuccessful = true;
				ctx.getPipeline().remove(this);
				log.info("Client connected successfully. Alias: " + login.getAlias());
			} else {
				denyAccessToClient(e);
			}
			
		} else if(chanConnected && authSuccessful) {
			super.messageReceived(ctx, e);
		} else {
			denyAccessToClient(e);
		}
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		chanConnected = true;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		log.warn("Exception caught in network stack.", e.getCause());
		super.exceptionCaught(ctx, e);
	}
	

	private void denyAccessToClient(MessageEvent e) {
		log.warn("Login request denied");
		e.getChannel().write(new Message(OpCode.LOGIN_DENIED)).awaitUninterruptibly();
		e.getChannel().close().awaitUninterruptibly();
	}
}
