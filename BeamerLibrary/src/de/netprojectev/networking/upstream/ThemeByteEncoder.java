package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.datastructures.media.Theme;

public class ThemeByteEncoder extends MessageToByteEncoder<Theme> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Theme msg, ByteBuf out) throws Exception {
		out.writeInt(3);
		ctx.write(msg.getId());
		ctx.write(msg.getName());
		ctx.writeAndFlush(msg.getBackgroundImage());
	}

}
