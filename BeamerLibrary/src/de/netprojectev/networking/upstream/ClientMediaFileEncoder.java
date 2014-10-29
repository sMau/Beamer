package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.utils.LoggerBuilder;

public class ClientMediaFileEncoder extends MessageToByteEncoder<ClientMediaFile> {
	private static final Logger log = LoggerBuilder.createLogger(ClientMediaFileEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, ClientMediaFile msg, ByteBuf out) throws Exception {
		ctx.write(msg.getId());
		ctx.write(msg.getPriorityID());
		ctx.write(msg.getShowCount());
		ctx.write(msg.getName());
		ctx.write(msg.getType());
		ctx.writeAndFlush(msg.isCurrent());
		ctx.writeAndFlush(msg.getPreview());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
