package de.netprojectev.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.HashedWheelTimer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.networking.old.downstream.MessageDecoder;
import de.netprojectev.networking.old.upstream.ClientMediaFileEncoder;
import de.netprojectev.networking.old.upstream.DequeueDataByteEncoder;
import de.netprojectev.networking.old.upstream.LoginByteEncoder;
import de.netprojectev.networking.old.upstream.MessageSplit;
import de.netprojectev.networking.old.upstream.PriorityByteEncoder;
import de.netprojectev.networking.old.upstream.PropertiesByteEncoder;
import de.netprojectev.networking.old.upstream.StringArrayEncoder;
import de.netprojectev.networking.old.upstream.ThemeByteEncoder;
import de.netprojectev.networking.old.upstream.TickerElementEncoder;
import de.netprojectev.networking.old.upstream.UUIDByteEncoder;
import de.netprojectev.networking.old.upstream.primitives.BooleanByteEncoder;
import de.netprojectev.networking.old.upstream.primitives.ByteArrayByteEncoder;
import de.netprojectev.networking.old.upstream.primitives.IntByteEncoder;
import de.netprojectev.networking.old.upstream.primitives.LongByteEncoder;
import de.netprojectev.networking.old.upstream.primitives.MediaTypeByteEncoder;
import de.netprojectev.networking.old.upstream.primitives.OpCodeByteEncoder;
import de.netprojectev.networking.old.upstream.primitives.StringByteEncoder;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.utils.LoggerBuilder;

public class Server {

	/*
	 * TODO last worked here, work here
	 * the new server GUI android application
	 */

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(Server.class);

	private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private final int port;
	private final MessageProxyServer proxy;
	private HashedWheelTimer timer;

	public Server(int port, ServerGUI serverGUI) {
		this.port = port;
		this.proxy = new MessageProxyServer(this, serverGUI);

		checkAndCreateDirs();

	}

	private void bindListeningSocket() {

		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {

				ch.pipeline().addLast(new ObjectEncoder());
				ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null)));
				ch.pipeline().addLast(Server.this.proxy);
			}
		})
		.option(ChannelOption.SO_BACKLOG, 128)
		.childOption(ChannelOption.SO_KEEPALIVE, true)
		.childOption(ChannelOption.TCP_NODELAY, true);

		b.bind(this.port);

		log.info("Binding listening socket to port: " + this.port);
	}

	public MessageProxyServer bindServerSocket(boolean startInFullscreen) {

		this.timer = new HashedWheelTimer();
		bindListeningSocket();

		if (startInFullscreen) {
			this.proxy.enableFullScreen();
		}

		this.proxy.makeGUIVisible();

		return this.proxy;

	}

	private void checkAndCreateDirs() {
		File savePath = new File(ConstantsServer.SAVE_PATH
				+ ConstantsServer.CACHE_PATH_IMAGES);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		savePath = new File(ConstantsServer.SAVE_PATH
				+ ConstantsServer.CACHE_PATH_VIDEOS);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		savePath = new File(ConstantsServer.SAVE_PATH
				+ ConstantsServer.CACHE_PATH_THEMESLIDES);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
	}

	public MessageProxyServer getProxy() {
		return this.proxy;
	}

	public void shutdownServer() {
		log.info("Starting server shutdown, informing clients.");
		this.proxy.broadcastMessage(new Message(OpCode.STC_SERVER_SHUTDOWN)).awaitUninterruptibly();
		this.proxy.getAllClients().close().awaitUninterruptibly();

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Server.this.timer.stop();
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		});
		t.start();

		log.info("Server shutdown complete.");

		try {
			this.proxy.getPrefsModel().serializeAll();
		} catch (IOException e) {
			log.log(Level.WARNING, "Error during serilization.", e);
		}

		System.exit(0);
	}

}
