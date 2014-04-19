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

import old.de.netprojectev.networking.HandlerNames;
import old.de.netprojectev.networking.MessageJoin;
import old.de.netprojectev.networking.MessageReplayingDecoder;
import old.de.netprojectev.server.networking.AuthHandlerServer;
import old.de.netprojectev.server.networking.MessageHandlerServer;

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.networking.upstream.FileByteEncoder;
import de.netprojectev.networking.upstream.MessageSplit;
import de.netprojectev.networking.upstream.OpCodeByteEncoder;
import de.netprojectev.server.networking.MessageProxyServer;
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
						//TODO next work here 03.04.14
						/*
						 * use filencoder and decoder for file transfers.
						 * next to do is to add file decoders dynamically after meta data received
						 * 
						 * 1 all opcodes in bytes, Message not serializable anymore only contents as pojo
						 * 2 files in bytes
						 * 3 own object structures in bytes
						 */
						
						
						ch.pipeline().addLast(HandlerNames.OBJECT_ENCODER, new ObjectEncoder());
						ch.pipeline().addLast(HandlerNames.FILE_TO_BYTE_ENCODER, new FileByteEncoder());
						ch.pipeline().addLast(HandlerNames.OPCODE_BYTE_ENCODER, new OpCodeByteEncoder());
						
						ch.pipeline().addLast(HandlerNames.MESSAGE_SPLIT, new MessageSplit());
						
						ch.pipeline().addLast(HandlerNames.MESSAGE_REPLAYING_DECODER, new MessageReplayingDecoder());
						
						ch.pipeline().addLast(HandlerNames.OBJECT_DECODER, new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingResolver(null)));
						ch.pipeline().addLast(HandlerNames.MESSAGE_JOIN, new MessageJoin());
						ch.pipeline().addLast(HandlerNames.AUTH_HANDLER_SERVER, new AuthHandlerServer(proxy));
						ch.pipeline().addLast(HandlerNames.MESSAGE_HANDLER_SERVER, new MessageHandlerServer(proxy));
						


						
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
