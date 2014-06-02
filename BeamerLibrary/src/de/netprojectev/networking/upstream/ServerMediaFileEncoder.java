package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

import org.apache.logging.log4j.Logger;

import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.utils.LoggerBuilder;

public class ServerMediaFileEncoder extends MessageToByteEncoder<ServerMediaFile> {

	private static final Logger log = LoggerBuilder.createLogger(ServerMediaFileEncoder.class);

	
	@Override
	protected void encode(ChannelHandlerContext ctx, ServerMediaFile msg, ByteBuf out) throws Exception {
		if (msg instanceof ImageFile) {
			ImageFile image = (ImageFile) msg;
			encodeImageFile(ctx, image);
			ctx.flush();
		} else if (msg instanceof Themeslide) {
			encodeImageFile(ctx, ((Themeslide) msg).getImageRepresantation());
			ctx.writeAndFlush(((Themeslide) msg).getThemeId());
		} else if (msg instanceof VideoFile) {
			// TODO
		} else if (msg instanceof Countdown) {
			// TODO
		} else {
			throw new UnkownMessageException("Unknown server media file type sent.");
		}

	}

	private void encodeImageFile(ChannelHandlerContext ctx, ImageFile image) throws IOException {
		ctx.write(image.getName());
		ctx.write(image.get());
		ctx.write(image.getPriorityID());
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
