package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.File;
import java.io.FileInputStream;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class FileByteEncoder extends MessageToByteEncoder<File> {

	private static final Logger log = LoggerBuilder.createLogger(FileByteEncoder.class);

	@Override
	protected void encode(final ChannelHandlerContext ctx, File msg, ByteBuf out) throws Exception {
		
		ctx.writeAndFlush(msg.length());

		log.debug("Writting file to out. Length: " + msg.length());
		
		FileInputStream fis = new FileInputStream(msg);
		DefaultFileRegion region = new DefaultFileRegion(fis.getChannel(), 0, msg.length());
		ctx.write(region);
		ctx.flush();
		fis.close();
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
