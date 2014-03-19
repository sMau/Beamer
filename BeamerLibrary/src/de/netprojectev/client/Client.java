package de.netprojectev.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.networking.ClientMessageHandler;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.MessageJoin;
import de.netprojectev.networking.MessageReplayingDecoder;
import de.netprojectev.networking.MessageSplit;
import de.netprojectev.networking.OpCode;
import de.netprojectev.networking.OpCodeByteEncoder;
import de.netprojectev.server.networking.MessageHandlerServer;
import de.netprojectev.utils.LoggerBuilder;

public class Client {

	private static final Logger log = LoggerBuilder.createLogger(Client.class);

	private LoginData login;

	private final ClientMessageProxy proxy;
	private final String host;
	private final int port;
	private final ClientGUI gui;

	private boolean loginSuccess;

	private EventLoopGroup group = new NioEventLoopGroup();

	public Client(String host, int port, LoginData login, ClientGUI gui, Class<? extends PreferencesModelClient> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		this.login = login;
		this.host = host;
		this.port = port;
		this.gui = gui;

		this.proxy = new ClientMessageProxy(this, clazz);

	}

	public ClientMessageProxy connect() throws InterruptedException {

		Bootstrap b = new Bootstrap();
		b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {

						ch.pipeline().addLast(new MessageReplayingDecoder());
						ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingResolver(null)));
						ch.pipeline().addLast(new MessageJoin());
						ch.pipeline().addLast(new ClientMessageHandler(proxy));
						
						ch.pipeline().addLast(new ObjectEncoder());
						ch.pipeline().addLast(new OpCodeByteEncoder());
						ch.pipeline().addLast(new MessageSplit());
						
						
					}
				});

		b.option(ChannelOption.TCP_NODELAY, true);
		b.option(ChannelOption.SO_KEEPALIVE, true);

		ChannelFuture connectFuture = b.connect(host, port).sync();
		connectFuture.awaitUninterruptibly(5000); //TODO change this fucking shit!
		if (connectFuture.isSuccess()) {
			proxy.setChannelToServer(connectFuture.channel());
			log.info("Client successfully connected to " + host + ":" + port);

			boolean loginSend = proxy.sendMessageToServer(new Message(OpCode.CTS_LOGIN_REQUEST, login)).awaitUninterruptibly(600000); //TODO change this fucking shit!
			if (!loginSend) {
				log.error("login message could not be send");
				gui.errorDuringLogin("Login message could not be sent.");
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
		if (proxy.getChannelToServer() != null) {
			proxy.getChannelToServer().close().awaitUninterruptibly();
		}
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
	            group.shutdownGracefully();
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
