package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.client.datastructures.ClientMediaFile;

public class ClientMediaFileEncoder extends MessageToByteEncoder<ClientMediaFile> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ClientMediaFile msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub

	}

}
