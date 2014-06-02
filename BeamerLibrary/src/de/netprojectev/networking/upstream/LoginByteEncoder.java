package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.LoginData;
import de.netprojectev.utils.LoggerBuilder;

public class LoginByteEncoder extends MessageToByteEncoder<LoginData> {

	private static final Logger log = LoggerBuilder.createLogger(LoginByteEncoder.class);

	
	@Override
	protected void encode(ChannelHandlerContext ctx, LoginData msg, ByteBuf out) throws Exception {
		ctx.write(msg.getAlias());
		ctx.writeAndFlush(msg.getKey());
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
