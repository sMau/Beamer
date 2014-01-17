package de.netprojectev.client;

import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.networking.ClientMessageHandler;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.utils.LoggerBuilder;

public class Client {
	
	private static final Logger log = LoggerBuilder.createLogger(Client.class);
	
	private LoginData login;
	
	private final ClientMessageProxy proxy;
	private final String host;
	private final int port;
	private final ClientGUI gui;
	
	private boolean loginSuccess;
	
	private ChannelFactory factory;
	
	public Client(String host, int port, LoginData login, ClientGUI gui, Class<? extends PreferencesModelClient> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		this.login = login;
		this.host = host;
		this.port = port;
		this.gui = gui;
		
		this.proxy = new ClientMessageProxy(this, clazz);

	}
	
	public ClientMessageProxy connect() {
		factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				
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
			
			boolean loginSend = proxy.sendMessageToServer(new Message(OpCode.CTS_LOGIN_REQUEST, login)).awaitUninterruptibly(10000);
			if(!loginSend) {
				gui.errorDuringLogin("Login message could not be sent.");
				log.error("login message could not be send");
			}
			log.info("Login request sent to server");
			
		} else {
			log.warn("Connection failed. Reason: Host not reachable.");
			releaseExternalRessources();		
			loginFailed("Connection failed. Reason: Host not reachable.");
		}
		
		return proxy;
	}

	public void disconnect() {
		log.info("Client disconnecting");
		proxy.sendMessageToServer(new Message(OpCode.CTS_DISCONNECT, login.getAlias())).awaitUninterruptibly();
		releaseExternalRessources();
		log.info("Disconnecting complete");
	}

	public void loginSuccess() {
		loginSuccess = true;
		gui.loginSuccess();
	}

	public ClientMessageProxy getProxy() {
		return proxy;
	}

	public void loginFailed(String reason) {
		log.info("Login failed, releasing all external ressources.");
		releaseExternalRessources();
		gui.errorDuringLogin(reason);
	}

	public boolean isLoginSuccess() {
		return loginSuccess;
	}

	public void serverShutdown() {
		
		log.info("Server shutdown, releasing all external ressources.");
		releaseExternalRessources();
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

	public ClientGUI getGui() {
		return gui;
	}

	public LoginData getLogin() {
		return login;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
	
}
