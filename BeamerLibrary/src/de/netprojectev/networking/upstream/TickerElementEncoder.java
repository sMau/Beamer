package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.datastructures.TickerElement;

public class TickerElementEncoder extends MessageToByteEncoder<TickerElement> {

	@Override
	protected void encode(ChannelHandlerContext ctx, TickerElement msg, ByteBuf out) throws Exception {
		out.writeInt(3);
		ctx.write(msg.getId());
		ctx.write(msg.getText());
		ctx.writeAndFlush(msg.isShow());
	}

}
