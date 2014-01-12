package de.netprojectev.server;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.jboss.netty.util.HashedWheelTimer;

import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.networking.AuthHandlerServer;
import de.netprojectev.server.networking.MessageHandlerServer;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.server.networking.ServerGUI;

public class Server {

	private static final Logger log = LoggerBuilder.createLogger(Server.class);

	private final int port;
	private final MessageProxyServer proxy;
	private ChannelFactory factory;
	private HashedWheelTimer timer;

	public Server(int port, ServerGUI serverGUI) {
		this.port = port;
		this.proxy = new MessageProxyServer(this, serverGUI);
		
		checkAndCreateDirs();
		
	}
	
	public MessageProxyServer bindServerSocket(boolean startInFullscreen) {
		
		
		timer = new HashedWheelTimer();
		bindListeningSocket();
		
		if(startInFullscreen) {
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

		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				
				return Channels.pipeline(
						new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers
								.weakCachingResolver(null)), new AuthHandlerServer(proxy), 
						new MessageHandlerServer(proxy), new ObjectEncoder());

			}
		});

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);

		bootstrap.bind(new InetSocketAddress(port));
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
				factory.releaseExternalResources();
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
