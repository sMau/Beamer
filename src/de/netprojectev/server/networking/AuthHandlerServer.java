package de.netprojectev.server.networking;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import de.netprojectev.misc.Misc;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.model.PreferencesModelServer;

public class AuthHandlerServer extends SimpleChannelHandler {

	private static final Logger LOG = Misc.getLoggerAll(AuthHandlerServer.class.getName());

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
		LOG.log(Level.INFO, "message received");
		if(chanConnected && received.getOpCode().equals(OpCode.LOGIN_REQUEST)) {
			
			LoginData login = (LoginData) received.getData();
			
			if(login.getKey().equals(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_SERVER_PW))) {
				proxy.clientConnected(e.getChannel());
				authSuccessful = true;
				ctx.getPipeline().remove(this);
				LOG.log(Level.INFO, "login successful");
			} else {
				//TODO access denied
			}
			
			
		} else if(chanConnected && authSuccessful) {
			super.messageReceived(ctx, e);
		} else {
			//TODO release all resources -> disconnect the client best with access denied msg
		}
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		chanConnected = true;
		LOG.log(Level.INFO, "channel connected");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, e);
	}
}
