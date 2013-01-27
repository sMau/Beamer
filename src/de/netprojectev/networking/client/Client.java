package de.netprojectev.networking.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import de.netprojectev.misc.Misc;
import de.netprojectev.networking.Message;

public class Client {
	
	private static final Logger LOG = Misc.getLoggerAll(Client.class.getName());
	
	private final String alias;
	
	private final String host;
	private final int port;
	
	private Channel channelToServer;
	
	public Client(String host, int port, String alias) {
		this.alias = alias;
		this.host = host;
		this.port = port;
		
		boolean successfulConnected = connect();
		
		if(!successfulConnected) {
			//TODO			
		}
		
	}
	
	private boolean connect() {
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(), new ObjectDecoder(ClassResolvers.weakCachingResolver(null)), new ClientMessageHandler());
			}
		});
		
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		
		ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress(host, port));
		connectFuture.awaitUninterruptibly(5000);
		if(connectFuture.isSuccess()) {
			channelToServer = connectFuture.getChannel();
			LOG.log(Level.INFO, "Client successfully connected to " + host + ":" + port);
			return true;
		} else {
			LOG.log(Level.WARNING, "Connection failed. Reason: " + connectFuture.getCause());
			connectFuture.getChannel().getCloseFuture().awaitUninterruptibly();
			factory.releaseExternalResources();
			return false;
		}
	}
	
	public void sendMessageToServer(Message msgToSend) {
		channelToServer.write(msgToSend);
	}

	public String getAlias() {
		return alias;
	}
}
