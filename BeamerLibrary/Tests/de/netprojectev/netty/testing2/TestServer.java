package de.netprojectev.netty.testing2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

public class TestServer {

	private int port = 8080;
	private ServerBootstrap bootstrap;
	
	
	public TestServer() {
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				
				return Channels.pipeline(new ObjectDecoder(ClassResolvers.weakCachingResolver(null)), new CommandMessageDecoder(), new ObjectEncoder());
			}
		});
		
	}
	
	public void bind() {
		bootstrap.bind(new InetSocketAddress(port));
	}
	

}
