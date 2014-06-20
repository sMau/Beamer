package de.netprojectev.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.downstream.MessageDecoder;
import de.netprojectev.networking.upstream.DequeueDataByteEncoder;
import de.netprojectev.networking.upstream.FileByteEncoder;
import de.netprojectev.networking.upstream.LoginByteEncoder;
import de.netprojectev.networking.upstream.MessageSplit;
import de.netprojectev.networking.upstream.PriorityByteEncoder;
import de.netprojectev.networking.upstream.PropertiesByteEncoder;
import de.netprojectev.networking.upstream.StringArrayEncoder;
import de.netprojectev.networking.upstream.ThemeByteEncoder;
import de.netprojectev.networking.upstream.TickerElementEncoder;
import de.netprojectev.networking.upstream.UUIDByteEncoder;
import de.netprojectev.networking.upstream.primitives.BooleanByteEncoder;
import de.netprojectev.networking.upstream.primitives.ByteArrayByteEncoder;
import de.netprojectev.networking.upstream.primitives.IntByteEncoder;
import de.netprojectev.networking.upstream.primitives.LongByteEncoder;
import de.netprojectev.networking.upstream.primitives.MediaTypeByteEncoder;
import de.netprojectev.networking.upstream.primitives.OpCodeByteEncoder;
import de.netprojectev.networking.upstream.primitives.StringByteEncoder;
import de.netprojectev.utils.LoggerBuilder;

public class Client {

	private static final Logger log = LoggerBuilder.createLogger(Client.class);

	private LoginData login;

	private final MessageProxyClient proxy;
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

		this.proxy = new MessageProxyClient(this, clazz);

	}

	public MessageProxyClient connect() throws InterruptedException {

		Bootstrap b = new Bootstrap();
		b.group(this.group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						
						ch.pipeline().addLast(new BooleanByteEncoder(), new ByteArrayByteEncoder(), new IntByteEncoder(), new LongByteEncoder(),
								new StringByteEncoder(), new MediaTypeByteEncoder(), new OpCodeByteEncoder(),
								new UUIDByteEncoder(), new DequeueDataByteEncoder(), new ThemeByteEncoder(), 
								new PriorityByteEncoder(), new LoginByteEncoder(), new PropertiesByteEncoder(), 
								new StringArrayEncoder(), new FileByteEncoder(),
								new TickerElementEncoder(), new MessageSplit());
						ch.pipeline().addLast(new MessageDecoder(), proxy);
					}
				});

		b.option(ChannelOption.TCP_NODELAY, true);
		b.option(ChannelOption.SO_KEEPALIVE, true);

		ChannelFuture connectFuture = b.connect(this.host, this.port).sync();
		connectFuture.awaitUninterruptibly(120000); // TODO change this fucking
													// shit!
		if (connectFuture.isSuccess()) {
			this.proxy.setChannelToServer(connectFuture.channel());
			log.info("Client successfully connected to " + this.host + ":" + this.port);

			boolean loginSend = this.proxy.sendLoginRequest(this.login).awaitUninterruptibly(120000); // TODO shit!
			if (!loginSend) {
				log.error("login message could not be send");
				this.gui.errorDuringLogin("Login message could not be sent.");
			}
			log.info("Login request sent to server");

		} else {
			log.warn("Connection failed. Reason: Host not reachable.");
			releaseExternalRessources();
			loginFailed("Connection failed. Reason: Host not reachable.");
		}

		return this.proxy;
	}

	public void disconnect() {
		log.info("Client disconnecting");
		this.proxy.sendDisconnectRequest().awaitUninterruptibly(120000);// TODO shit!
		releaseExternalRessources();
		log.info("Disconnecting complete");
	}

	public ClientGUI getGui() {
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
