package de.netprojectev.networking.upstream;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author Samuel Schüppen
 * 
 * Class used to write the total data count of the msg.
 * Therefore only the 8 lower order Bits are written, as there will never be more than 255 data objects in a msg.
 *
 */

public class DataCountByteEncoder extends MessageToByteEncoder<Integer> {
	private static final Logger log = LoggerBuilder.createLogger(DataCountByteEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
		out.writeByte(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close(); //XXX
	}

}
