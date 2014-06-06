package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class StringArrayEncoder extends MessageToByteEncoder<String[]> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String[] msg, ByteBuf out) throws Exception {
		ctx.write(msg.length);
		for(String s : msg) {
			ctx.write(s);
		}
		ctx.flush();
		
	}

}
