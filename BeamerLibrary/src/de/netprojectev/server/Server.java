package de.netprojectev.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
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

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.IntegerByteCodec;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.MessageJoin;
import de.netprojectev.networking.MessageSplit;
import de.netprojectev.networking.OpCode;
import de.netprojectev.networking.OpCodeByteCodec;
import de.netprojectev.server.networking.MessageHandlerServer;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.server.networking.ServerGUI;
import de.netprojectev.utils.LoggerBuilder;

public class Server {

	private static final Logger log = LoggerBuilder.createLogger(Server.class);

	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

	private final int port;
	private final MessageProxyServer proxy;
	private HashedWheelTimer timer;

	public Server(int port, ServerGUI serverGUI) {
		this.port = port;
		this.proxy = new MessageProxyServer(this, serverGUI);

		checkAndCreateDirs();

	}

	public MessageProxyServer bindServerSocket(boolean startInFullscreen) {

		timer = new HashedWheelTimer();
		bindListeningSocket();

		if (startInFullscreen) {
			proxy.enableFullScreen();
		}
		proxy.makeGUIVisible();

		return proxy;
		/*
		 * when setup is finished make the gui visible
		 */

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

	private void bindListeningSocket() {

		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {

						ch.pipeline().addLast(new ObjectEncoder());
						
						ch.pipeline().addLast(new IntegerByteCodec());
						ch.pipeline().addLast(new OpCodeByteCodec());
						ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingResolver(null)));
						ch.pipeline().addLast(new MessageJoin());
						ch.pipeline().addLast(new MessageHandlerServer(proxy));
						
						ch.pipeline().addLast(new MessageSplit());
						
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.TCP_NODELAY, true);

		ChannelFuture f = b.bind(port);
		
		log.info("Binding listening socket to port: " + port);
	}

	public void shutdownServer() {
		log.info("Starting server shutdown, informing clients.");
		proxy.broadcastMessage(new Message(OpCode.STC_SERVER_SHUTDOWN)).awaitUninterruptibly();
		proxy.getAllClients().close().awaitUninterruptibly();

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				timer.stop();
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		});
		t.start();

		log.info("Server shutdown complete.");

		try {
			proxy.getPrefsModel().serializeAll();
		} catch (IOException e) {
			log.warn("Error during serilization.", e);
		}

		System.exit(0);
	}

	public MessageProxyServer getProxy() {
		return proxy;
	}

}
