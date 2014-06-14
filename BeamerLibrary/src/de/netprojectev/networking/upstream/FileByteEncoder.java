package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class FileByteEncoder extends MessageToByteEncoder<File> {

	private static final Logger log = LoggerBuilder.createLogger(FileByteEncoder.class);
	private int chunkSize = 131072; // 128KB
	//XXX maybe test different chunksizes and eval performance
	/**
	 * The default chunkSize is chosen. 128KB
	 */
	public FileByteEncoder() {
		
	}
	
	public FileByteEncoder(int chunkSizeInKB) {
		this.chunkSize = chunkSizeInKB * 1024;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, File msg, ByteBuf out) throws Exception {
		
		int chunkCount = ((int) Math.ceil((msg.length() / chunkSize))) + 1;

		out.writeLong(msg.length());
		out.writeInt(chunkSize);
		out.writeInt(chunkCount);	
		
		
		log.debug("Writting file to out. Length: " + msg.length()
					+ " chunkSize: " + chunkSize
					+ " chunkCount: " + chunkCount);
		
		ChunkedNioFile chunkedFile = new ChunkedNioFile(msg, chunkSize);
		
		try {
			for(int i = 0; i < chunkCount; i++) {
				log.debug("Writing chunk #" + i);
				out.writeBytes(chunkedFile.readChunk(ctx));
			}
		} finally {
			chunkedFile.close();
		}
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
