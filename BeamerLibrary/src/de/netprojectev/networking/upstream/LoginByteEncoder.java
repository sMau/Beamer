package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.networking.LoginData;

public class LoginByteEncoder extends MessageToByteEncoder<LoginData> {

	@Override
	protected void encode(ChannelHandlerContext ctx, LoginData msg, ByteBuf out) throws Exception {
		ctx.write(msg.getAlias());
		ctx.writeAndFlush(msg.getKey());
	}

}
