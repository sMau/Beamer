package de.netprojectev.netty.testing2;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

public class TestClient {

	private String host = "localhost";
	private int port = 8080;
	private ClientBootstrap bootstrap;
	private Channel channel;
	
	public TestClient() {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(),new ObjectDecoder(ClassResolvers.weakCachingResolver(null)), new CommandMessageEncoder());
			}
		});
	}
	
	
	public void connect() {		
		
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
		future.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()) {
					System.out.println("client connected ");
					channel = future.getChannel();
				} else {
					System.out.println("connection failed");
				}
				
			}
		});

	}
	
	public void writeMessage(byte b) {
		channel.write(new CommandMessage(b));
	}

}
