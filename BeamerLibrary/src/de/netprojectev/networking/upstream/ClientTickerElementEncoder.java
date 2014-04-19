package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.client.datastructures.ClientTickerElement;

public class ClientTickerElementEncoder extends MessageToByteEncoder<ClientTickerElement> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ClientTickerElement msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub

	}

}
