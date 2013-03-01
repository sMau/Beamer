package de.netprojectev.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import de.netprojectev.server.networking.MessageHandlerServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class ServerInit {
	
	private final int port;
	private static final ChannelGroup allClients = new DefaultChannelGroup("beamer-clients");

	public ServerInit(int port) {
		this.port = port;
		bindListeningSocket();
	}
	
	private void bindListeningSocket() {
				
		final MessageProxyServer proxy = new MessageProxyServer();

		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(), new ObjectDecoder(ClassResolvers.weakCachingResolver(null)), new MessageHandlerServer(proxy));
			
			}
		});
		
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
	
		bootstrap.bind(new InetSocketAddress(port));
	}
	
	public static ChannelGroup getAllclients() {
		return allClients;
	}
	
	
	
	public static void main(String[] args) {
		int port = 9999;
		if(args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
			
			}
		}
		new ServerInit(port);
	}

}