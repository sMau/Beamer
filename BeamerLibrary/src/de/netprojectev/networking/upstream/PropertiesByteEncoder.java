package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Properties;
import java.util.logging.Level;

import de.netprojectev.utils.LoggerBuilder;

public class PropertiesByteEncoder extends MessageToByteEncoder<Properties> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(PropertiesByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Properties msg, ByteBuf out) throws Exception {

		//terrible evaluation of the size of the properties set, but .size() always yields 0
		int i = 0;
		for (String key : msg.stringPropertyNames()) {
			i++;
		}
		log.info("Init Properties key val count: " + i);

		out.writeInt(i);

		for (String key : msg.stringPropertyNames()) {
			ctx.write(key);
			ctx.write(msg.getProperty(key));
		}
		ctx.flush();

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
