package de.netprojectev.client;

import de.netprojectev.common.networking.FileEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationTargetException;

import de.netprojectev.client.datamodel.PreferencesModelClient;
import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.common.networking.LoginData;
import de.netprojectev.common.utils.LoggerBuilder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Client {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(Client.class);

	private LoginData login;

	private final MessageProxyClient proxy;
	private final String host;
	private final int port;
	private final GUIClient gui;

	private boolean loginSuccess;

	private EventLoopGroup group = new NioEventLoopGroup();

	public Client(String host, int port, LoginData login, GUIClient gui, Class<? extends PreferencesModelClient> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		this.login = login;
		this.host = host;
		this.port = port;
		this.gui = gui;

		this.proxy = new MessageProxyClient(this, clazz);

	}

	public MessageProxyClient connect() throws InterruptedException {

		Bootstrap b = new Bootstrap();
		b.group(this.group)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {

				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null)));
				ch.pipeline().addLast(new FileEncoder());
				ch.pipeline().addLast(Client.this.proxy);
			}
		});
		b.option(ChannelOption.SO_BACKLOG, 128);
		b.option(ChannelOption.TCP_NODELAY, true);
		b.option(ChannelOption.SO_KEEPALIVE, true);

		ChannelFuture connectFuture = b.connect(this.host, this.port);
		connectFuture.awaitUninterruptibly(5000); 
		// shit!
		if (connectFuture.isSuccess()) {
			this.proxy.setChannelToServer(connectFuture.channel());
			log.info("Client successfully connected to " + this.host + ":" + this.port);

			boolean loginSend = this.proxy.sendLoginRequest(this.login).awaitUninterruptibly(5000); 
			
			if (!loginSend) {
				log.severe("login message could not be send");
				this.gui.errorDuringLogin("Login message could not be sent.");
			}
			log.info("Login request sent to server");

		} else {
			log.warning("Connection failed. Reason: Host not reachable.");
			releaseExternalRessources();
			loginFailed("Connection failed. Reason: Host not reachable.");
		}

		return this.proxy;
	}

	public void disconnect() {
		log.info("Client disconnecting");
		this.proxy.sendDisconnectRequest().awaitUninterruptibly(5000);
		releaseExternalRessources();
		log.info("Disconnecting complete");
	}

	public GUIClient getGui() {
		return this.gui;
	}

	public String getHost() {
		return this.host;
	}

	public LoginData getLogin() {
		return this.login;
	}

	public int getPort() {
		return this.port;
	}

	public MessageProxyClient getProxy() {
		return this.proxy;
	}

	public boolean isLoginSuccess() {
		return this.loginSuccess;
	}

	public void loginFailed(String reason) {
		log.info("Login failed, releasing all external ressources.");
		releaseExternalRessources();
		this.gui.errorDuringLogin(reason);
	}

	public void loginSuccess() {
		this.loginSuccess = true;
		this.gui.loginSuccess();
	}

	private Thread releaseExternalRessources() {
		if (this.proxy.getChannelToServer() != null) {
			this.proxy.getChannelToServer().close().awaitUninterruptibly();
		}
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Client.this.group.shutdownGracefully();
				log.info("Ressources releasing complete.");
			}
		});
		t.start();

		return t;
	}

	public void serverShutdown() {

		log.info("Server shutdown, releasing all external ressources.");
		releaseExternalRessources();
	}

}
