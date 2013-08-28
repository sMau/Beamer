package de.netprojectev.client;

import java.io.File;
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

import de.netprojectev.client.gui.main.LoginDialog;
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
	private LoginDialog dialog;
	
	private boolean loginSuccess;
	
	private ChannelFactory factory;
	
	public Client(String host, int port, LoginData login, LoginDialog loginDialog) {
		this.login = login;
		this.host = host;
		this.port = port;
		
		File savePath = new File(ConstantsClient.SAVE_PATH);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		
		this.proxy = new ClientMessageProxy(this);
		
		this.dialog = loginDialog;
		
	}
	
	public void connect() {
		factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				
				//TODO maybe adjust the size to somethin useful, also in the server class
				return Channels.pipeline(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingResolver(null)), new ClientMessageHandler(proxy), new ObjectEncoder());
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
			proxy.sendMessageToServer(new Message(OpCode.CTS_LOGIN_REQUEST, login)).awaitUninterruptibly();
			log.info("Login request sent to server");
			
		} else {
			log.warn("Connection failed. Reason: Host not reachable.");
			releaseExternalRessources();		
			loginFailed("Connection failed. Reason: Host not reachable.");
		}
	}

	public void disconnect() {
		log.info("Client disconnecting");
		proxy.sendMessageToServer(new Message(OpCode.CTS_DISCONNECT, login.getAlias())).awaitUninterruptibly();
		releaseExternalRessources();
		log.info("Disconnecting complete");
	}

	public void loginSuccess() {
		loginSuccess = true;
		dialog.loginSuccess(proxy);
	}

	public ClientMessageProxy getProxy() {
		return proxy;
	}

	public void loginFailed(String reason) {
		log.info("Login failed, releasing all external ressources.");
		releaseExternalRessources();
		dialog.loginFailed(reason);
	}

	public boolean isLoginSuccess() {
		return loginSuccess;
	}

	public void serverShutdown() {
		
		log.info("Server shutdown, releasing all external ressources.");
		releaseExternalRessources();
		//TODO serialization or something like that
	}
	

	private Thread releaseExternalRessources() {
		if(proxy.getChannelToServer() != null) {
			proxy.getChannelToServer().close().awaitUninterruptibly();
		}
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				factory.releaseExternalResources();
				log.info("Ressources releasing complete.");
			}
		});
		t.start();
		
		return t;
	}
	
}
