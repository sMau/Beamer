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

import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.server.networking.AuthHandlerServer;
import de.netprojectev.server.networking.MessageHandlerServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class Server {
		
	private static final Logger log = LoggerBuilder.createLogger(Server.class);
	
	private final int port;
	private final MessageProxyServer proxy;
	private ChannelFactory factory;

	public Server(int port) {
		this.port = port;
		
		//TODO this is only for testing, a clean setup of all necessary files is better here
		File savePath = new File(ConstantsServer.SERVER_SAVE_PATH + ConstantsServer.SERVER_CACHE_FOLDER);
		if(!savePath.exists()) {
			savePath.mkdirs();
		}
		
		proxy = new MessageProxyServer();
		bindListeningSocket();
	}
	
	private void bindListeningSocket() {
		
		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingResolver(null)), new AuthHandlerServer(proxy), new MessageHandlerServer(proxy), new ObjectEncoder());
			
			}
		});
		
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
	
		bootstrap.bind(new InetSocketAddress(port));
		log.info("Binding listening socket to port: " + port);
	}
	
	public static void main(String[] args) {
		int port = 11111;
		if(args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
			
			}
		}
		new Server(port);
	}

	public void shutdownServer() {
		log.info("Starting server shutdown, informing clients.");
		proxy.broadcastMessage(new Message(OpCode.STC_SERVER_SHUTDOWN)).awaitUninterruptibly();
		proxy.getAllClients().close().awaitUninterruptibly();
		factory.releaseExternalResources();
		try {
			PreferencesModelServer.saveProperties();
		} catch (IOException e) {
			log.warn("Error during saving properties.", e);
		}
		log.info("Server shutdown complete.");
	}
	
	public MessageProxyServer getProxy() {
		return proxy;
	}

}
