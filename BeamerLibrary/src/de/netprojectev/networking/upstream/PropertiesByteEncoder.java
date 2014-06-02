package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Properties;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class PropertiesByteEncoder extends MessageToByteEncoder<Properties> {

	private static final Logger log = LoggerBuilder.createLogger(PropertiesByteEncoder.class);

	
	@Override
	protected void encode(ChannelHandlerContext ctx, Properties msg, ByteBuf out) throws Exception {
		if(msg.isEmpty()) {
			return;
		}
		out.writeInt(msg.size());
		
		for(String key : msg.stringPropertyNames()) {
			ctx.write(key);
			ctx.write(msg.getProperty(key));
		}
		ctx.flush();
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
