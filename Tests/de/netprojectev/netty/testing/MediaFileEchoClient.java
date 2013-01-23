package de.netprojectev.netty.testing;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import de.netprojectev.netty.examples.SimpleExampleClientHandler;
import de.netprojectev.netty.examples.TimeDecoder;

public class MediaFileEchoClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = "localhost";
		int port = 8080;
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new ObjectEncoder(), new MediaFileEchoClientHandler());
			}
		});
		
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
		/*future.awaitUninterruptibly();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
		}
		future.getChannel().getCloseFuture().awaitUninterruptibly();
        factory.releaseExternalResources();*/
		
		
	}

}
