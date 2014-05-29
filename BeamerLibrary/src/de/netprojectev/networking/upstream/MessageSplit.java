package de.netprojectev.networking.upstream;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.utils.LoggerBuilder;

/**
 * 
 * @author Samuel Schüppen
 * 
 * Class to split a {@link Message} in its {@link OpCode} and its data objects.
 *
 */

@Sharable
public class MessageSplit extends MessageToMessageEncoder<Message> {
	private static final Logger log = LoggerBuilder.createLogger(MessageSplit.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg,
			List<Object> out) throws Exception {
		out.add(msg.getOpCode());
		if(msg.getOpCode().isDataContained()) {
			out.add(msg.getData().size());
			for(Object o : msg.getData()) {
				out.add(o);
			}
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close(); //XXX
	}
}
