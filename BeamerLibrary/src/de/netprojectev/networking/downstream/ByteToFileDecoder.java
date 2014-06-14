package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class ByteToFileDecoder extends ByteToMessageDecoder {

	private static final Logger log = LoggerBuilder.createLogger(ByteToFileDecoder.class);
	
	private File savePath;
	private int writtenChunksCount = 0;
	private long length;
	private int chunkSize;
	private int chunkCount;
	
	public ByteToFileDecoder(long length, int chunkSize, int chunkCount, String savePath) {
		this.savePath = new File(savePath + UUID.randomUUID());
		this.length = length;
		this.chunkCount = chunkCount;
		this.chunkSize = chunkSize;
	}
	
	//TODO last worked here, use a non replaying decoder for the file decoding. Or change message decoder to an 
	// non replaying decoder and make it possible to return from the decoding method
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		if(writtenChunksCount < chunkCount - 1 && in.readableBytes() < chunkSize) {
			return;
		}
		
		try {

			log.debug("Receiving file. Length: " + length
					+ " chunkSize: " + chunkSize
					+ " chunkCount: " + chunkCount);
			
			if(writtenChunksCount < 1) {
				savePath.createNewFile();
			}
			
			for(; writtenChunksCount < chunkCount - 1; writtenChunksCount++) {
				log.debug("Reciving chunk #" + writtenChunksCount);
				byte[] readChunk = new byte[chunkSize];
				in.readBytes(readChunk);
				Files.write(Paths.get(savePath.getAbsolutePath()), readChunk, StandardOpenOption.APPEND);
			}
			
			long sizeOfTransmittedChunks = ((long) chunkCount - 1) * (long) chunkSize;
			int lastChunkSize = (int) (length - sizeOfTransmittedChunks);
			
			if(in.readableBytes() < lastChunkSize) {
				return;
			}
			
			log.debug("Receiving last chunk. Size: " + lastChunkSize);			
			
			byte[] readChunk = new byte[lastChunkSize];
			in.readBytes(readChunk);
			Files.write(Paths.get(savePath.getAbsolutePath()), readChunk, StandardOpenOption.APPEND);
		} finally {
			
			
			
		}

	}

}
