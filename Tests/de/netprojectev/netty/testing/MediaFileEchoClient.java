package de.netprojectev.netty.testing;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import de.netprojectev.server.datastructures.VideoFile;

public class MediaFileEchoClient {

	private static Channel clientChannel;
	private static ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "localhost";
		int port = 8080;
		ClientBootstrap bootstrap = new ClientBootstrap(factory);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(), new MediaFileEchoClientHandler());
			}
		});
		
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
		future.awaitUninterruptibly();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
			System.exit(0);
		} else {
			System.out.println("client connected successfully");
			clientChannel = future.getChannel();
		}
		
		try {
			sendSomeMessages();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private static void sendSomeMessages() throws InterruptedException {
		VideoFile vid1 = new VideoFile("client1 vid1", new File("/home"));
		VideoFile vid2 = new VideoFile("client1 vid2", new File("/home"));
		VideoFile vid3 = new VideoFile("client1 vid3", new File("/home"));
		VideoFile vid4 = new VideoFile("client1 vid4", new File("/home"));
		
		
		Thread.sleep(3000);
		
		clientChannel.write(vid1);
		Thread.sleep(2000);
		clientChannel.write(vid2);
		Thread.sleep(2000);
		clientChannel.write(vid3);
		Thread.sleep(2000);
		ChannelFuture futureOfWrite = clientChannel.write(vid4);
		futureOfWrite.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()) {
					closeConnection();
				} else {
					System.out.println(future.getCause());
					closeConnection();
				}
			}
		});
		
	}


	public static void closeConnection() {
		System.out.println("closing connection");
		clientChannel.getCloseFuture().awaitUninterruptibly();
        factory.releaseExternalResources();
	}

}
