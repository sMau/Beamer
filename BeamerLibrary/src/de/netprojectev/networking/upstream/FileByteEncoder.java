package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;

import de.netprojectev.utils.LoggerBuilder;

public class FileByteEncoder extends MessageToByteEncoder<File> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(FileByteEncoder.class);

	@Override
	protected void encode(final ChannelHandlerContext ctx, File msg, ByteBuf out) throws Exception {

		ctx.writeAndFlush(msg.length());

		log.fine("Writting file to out. Length: " + msg.length());

		final FileInputStream fis = new FileInputStream(msg);
		DefaultFileRegion region = new DefaultFileRegion(fis.getChannel(), 0, msg.length());
		ctx.write(region).addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (!future.isSuccess()) {
					log.log(Level.SEVERE, "Error during writing file to network.", future.cause());
				}
				log.fine("Writing file to network opertion completed successful");
				fis.close();
			}
		});
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
