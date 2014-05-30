package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Properties;

public class PropertiesByteEncoder extends MessageToByteEncoder<Properties> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Properties msg, ByteBuf out) throws Exception {
		if(msg.isEmpty()) {
			return;
		}
		out.writeInt(msg.size() * 2);
		
		for(String key : msg.stringPropertyNames()) {
			ctx.write(key);
			ctx.write(msg.getProperty(key));
		}
		ctx.flush();
		
	}

}
