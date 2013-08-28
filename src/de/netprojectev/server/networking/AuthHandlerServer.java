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
		if(chanConnected && received.getOpCode().equals(OpCode.CTS_LOGIN_REQUEST)) {
			
			LoginData login = (LoginData) received.getData()[0];
			
			if(login.getKey().equals(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_PW))) {
				
				if(proxy.findUserByAlias(login.getAlias()) == null) {
					proxy.clientConnected(e.getChannel(), login.getAlias());
					authSuccessful = true;
					e.getChannel().write(new Message(OpCode.STC_CONNECTION_ACK));
					e.getChannel().getPipeline().remove(this);
					log.info("Client connected successfully. Alias: " + login.getAlias());
				} else {
					denyAccessToClient("Alias already in use.", e);
				}
				
			} else {
				denyAccessToClient("No valid login.", e);
			}
			
		} else if(chanConnected && authSuccessful) {
			super.messageReceived(ctx, e);
		} else {
			denyAccessToClient("Unknown error occured.", e);
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
	

	private void denyAccessToClient(String reason, MessageEvent e) {
		log.warn("Login request denied: " + reason); 
		e.getChannel().write(new Message(OpCode.STC_LOGIN_DENIED, reason)).awaitUninterruptibly();
		e.getChannel().close().awaitUninterruptibly();
	}
}
