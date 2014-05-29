package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.server.datastructures.ServerMediaFile;

public class ServerMediaFileEncoder extends MessageToByteEncoder<ServerMediaFile> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ServerMediaFile msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub

	}

}
