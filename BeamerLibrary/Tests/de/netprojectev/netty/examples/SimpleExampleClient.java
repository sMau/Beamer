package de.netprojectev.netty.examples;

import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;

import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.util.concurrent.Executors;

public class SimpleExampleClient {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 8080;

		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new TimeDecoder(), new SimpleExampleClientHandler());
			}
		});

		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);

		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
		future.awaitUninterruptibly();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
		}
		future.getChannel().getCloseFuture().awaitUninterruptibly();
        factory.releaseExternalResources();

	}
}
