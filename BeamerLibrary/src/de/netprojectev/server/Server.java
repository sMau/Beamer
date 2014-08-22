package de.netprojectev.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.HashedWheelTimer;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.networking.downstream.MessageDecoder;
import de.netprojectev.networking.upstream.ClientMediaFileEncoder;
import de.netprojectev.networking.upstream.DequeueDataByteEncoder;
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
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.utils.LoggerBuilder;

public class Server {

	/*TODO last worked here, work here again
	 * TODO LIST WHAT TO DO NEXT 23.07.14
	 *  Next check all functions for working
	 *  Clean up and todo list check and resolve the single todos 
	 *  clean switch to the master branch
	 *  new branch for the new server GUI
	 * 	android appliction
	 */
	
	private static final Logger log = LoggerBuilder.createLogger(Server.class);

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
						
						ch.pipeline().addLast(new BooleanByteEncoder(), new ByteArrayByteEncoder(), new IntByteEncoder(), new LongByteEncoder(),
								new StringByteEncoder(), new MediaTypeByteEncoder(), new OpCodeByteEncoder(),
								new UUIDByteEncoder(), new DequeueDataByteEncoder(), new ThemeByteEncoder(),
								new PriorityByteEncoder(), new LoginByteEncoder(), new PropertiesByteEncoder(),
								new StringArrayEncoder(), new ClientMediaFileEncoder(),
								new TickerElementEncoder(), new MessageSplit());
						ch.pipeline().addLast(new MessageDecoder(), proxy);
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
			log.warn("Error during serilization.", e);
		}

		System.exit(0);
	}

}
