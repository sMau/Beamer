package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import de.netprojectev.datastructures.TickerElement;
import de.netprojectev.utils.LoggerBuilder;

import java.util.logging.Level;

public class TickerElementEncoder extends MessageToByteEncoder<TickerElement> {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(TickerElementEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, TickerElement msg, ByteBuf out) throws Exception {
		ctx.write(msg.getId());
		ctx.write(msg.getText());
		ctx.writeAndFlush(msg.isShow());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
