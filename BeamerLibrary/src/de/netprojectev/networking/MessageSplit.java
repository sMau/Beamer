package de.netprojectev.networking;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.Serializable;
import java.util.List;

@Sharable
public class MessageSplit extends MessageToMessageEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg,
			List<Object> out) throws Exception {
		out.add(msg.getOpCode());
		out.add(msg.getData().length);
		for(Serializable s : msg.getData()) {
			out.add(s);
		}
	}
}
