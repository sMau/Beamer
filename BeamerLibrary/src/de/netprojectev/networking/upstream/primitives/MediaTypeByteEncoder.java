package de.netprojectev.networking.upstream.primitives;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.client.datastructures.MediaType;

public class MediaTypeByteEncoder extends MessageToByteEncoder<MediaType> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MediaType msg, ByteBuf out) throws Exception {
		out.writeLong(1);
		out.writeByte(msg.ordinal());
	}

}
