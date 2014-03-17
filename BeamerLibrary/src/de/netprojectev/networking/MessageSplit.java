package de.netprojectev.networking;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

@Sharable
public class MessageSplit extends MessageToMessageEncoder<Message> {
	private static final Logger log = LoggerBuilder.createLogger(MessageSplit.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg,
			List<Object> out) throws Exception {
		out.add(msg.getOpCode());
		out.add(msg.getData().length);
		for(Serializable s : msg.getData()) {
			out.add(s);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close();
	}
}