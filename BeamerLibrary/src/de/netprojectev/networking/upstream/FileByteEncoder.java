package de.netprojectev.networking.upstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class FileByteEncoder extends MessageToByteEncoder<File> {

	private static final Logger log = LoggerBuilder.createLogger(FileByteEncoder.class);
	private int chunkSize = 52428800; // 50MB

	public FileByteEncoder() {}
	
	/**
	 * 
	 * @param chunkSize the chunksize in byte
	 * 
	 */
	public FileByteEncoder(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, File msg, ByteBuf out) throws Exception {
		int chunkCount = ((int) Math.ceil((msg.length() / chunkSize))) + 1;

		ctx.write(msg.getName());
		out.writeLong(msg.length());
		ctx.write(chunkCount);
		ctx.writeAndFlush(chunkSize);		
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
		raf.close();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
