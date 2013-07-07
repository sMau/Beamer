package de.netprojectev.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import de.netprojectev.client.networking.ClientMessageHandler;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;

public class Client {
	
	private static final Logger log = LoggerBuilder.createLogger(Client.class);
	
	private LoginData login;
	
	private final ClientMessageProxy proxy;
	private final String host;
	private final int port;
	
	private boolean loginSuccess;
	
	private ChannelFactory factory;
	
	public Client(String host, int port, LoginData login) {
		this.login = login;
		this.host = host;
		this.port = port;
		
		this.proxy = new ClientMessageProxy(this);
		
	}
	
	public boolean connect() {
		factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectDecoder(ClassResolvers.weakCachingResolver(null)), new ClientMessageHandler(proxy), new ObjectEncoder());
			}
		});
		
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		
		ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress(host, port));
		connectFuture.awaitUninterruptibly(5000);
		if(connectFuture.isSuccess()) {
			proxy.setChannelToServer(connectFuture.getChannel());
			log.info("Client successfully connected to " + host + ":" + port);
			
			// TODO use awaitUnint. with timeout and react
			proxy.sendMessageToServer(new Message(OpCode.LOGIN_REQUEST, login)).awaitUninterruptibly();
			log.info("Login request sent to server");
			return true;
		} else {
			log.warn("Connection failed. Reason: ", connectFuture.getCause());
			connectFuture.getChannel().getCloseFuture().awaitUninterruptibly();
			factory.releaseExternalResources();
			return false;
		}
	}

	public void disconnect() {
		log.info("Client disconnecting");
		proxy.sendMessageToServer(new Message(OpCode.DISCONNECT)).awaitUninterruptibly();
		proxy.getChannelToServer().close().awaitUninterruptibly();
		factory.releaseExternalResources();
		log.info("Disconnecting complete");
	}

	public void loginSuccess() {
		loginSuccess = true;
	}

	public ClientMessageProxy getProxy() {
		return proxy;
	}

	public void loginDenied(String reason) {
		//TODO
		
	}
	

}
