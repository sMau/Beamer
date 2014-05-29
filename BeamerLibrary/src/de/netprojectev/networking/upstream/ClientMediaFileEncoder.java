package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import de.netprojectev.client.datastructures.ClientMediaFile;

public class ClientMediaFileEncoder extends MessageToByteEncoder<ClientMediaFile> {

	@Override
	protected void encode(ChannelHandlerContext ctx, ClientMediaFile msg, ByteBuf out) throws Exception {
		out.writeInt(7);
		ctx.write(msg.getId());
		ctx.write(msg.getName());
		ctx.write(msg.getPreview());
		ctx.write(msg.getPriorityID());
		ctx.write(msg.getShowCount());
		ctx.write(msg.getType());
		ctx.write(msg.isCurrent());
	}

}
