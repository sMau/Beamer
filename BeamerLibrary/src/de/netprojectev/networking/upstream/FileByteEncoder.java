package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class FileByteEncoder extends MessageToByteEncoder<File> {

	private static final Logger log = LoggerBuilder.createLogger(FileByteEncoder.class);
	private int chunkSize = 4096; // 4KB

	@Override
	protected void encode(ChannelHandlerContext ctx, File msg, ByteBuf out) throws Exception {
		
		int chunkCount = ((int) Math.ceil((msg.length() / chunkSize))) + 1;

		out.writeLong(msg.length());
		out.writeInt(chunkSize);
		out.writeInt(chunkCount);	
		
		ChunkedFile chunkedFile = new ChunkedFile(msg, chunkSize);
		
		log.debug("Chunk count sending: " + chunkCount);
		log.debug("Chunksize sending: " + chunkSize);
		
		
		try {
			for(int i = 0; i < chunkCount; i++) {
				log.debug("writing chunk #i: " + i);
				out.writeBytes(chunkedFile.readChunk(ctx));
			}
		} finally {
			chunkedFile.close();
		}
		

		/*
		
		RandomAccessFile raf = new RandomAccessFile(msg, "r");
				
        FileChannel inChannel = raf.getChannel();
                
        long sizeOfTransmittedChunks = ((long) chunkCount - 1) * (long) chunkSize;
		int lastChunkSize = (int) (msg.length() - sizeOfTransmittedChunks);
		
        int remainingChunks = chunkCount;
        for(long pos = 0; pos < msg.length(); ) {
        	MappedByteBuffer fileMap;
        	if(remainingChunks > 1) {
        		fileMap = inChannel.map(MapMode.READ_ONLY, pos, pos + chunkSize);
        	} else {
        		fileMap = inChannel.map(MapMode.READ_ONLY, pos, pos + lastChunkSize);
        	}
        	//TODO last worked here, this at the moment throws exception. problem is the chunking and the last chunk espacially
        	fileMap.flip();
            out.writeBytes(fileMap);
            fileMap = null;
        }
        
        inChannel.close();		
		raf.close();*/
		
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
