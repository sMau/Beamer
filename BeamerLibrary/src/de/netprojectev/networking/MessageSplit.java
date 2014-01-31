package de.netprojectev.networking;

import java.io.Serializable;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class MessageSplit extends MessageToMessageEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg,
			List<Object> out) throws Exception {
		out.add(msg.getOpCode());
		for(Serializable s : msg.getData()) {
			out.add(s);
		}
	}
}
