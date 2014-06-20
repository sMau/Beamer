package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class ChunkedFileDecoder extends ByteToMessageDecoder {

	public interface FileTransferFinishedListener {
		public void fileTransferFinished(File file) throws IOException;
	}

	private static final Logger log = LoggerBuilder.createLogger(ChunkedFileDecoder.class);

	private FileTransferFinishedListener finishListener;
	private OutputStream fileOut;
	private File savePath;
	private long length;
	private long writtenBytes;
	
	public ChunkedFileDecoder(File savePath, long length, FileTransferFinishedListener finishListener) throws IOException {
		this.savePath = savePath;
		this.length = length;
		this.finishListener = finishListener;
		this.writtenBytes = 0;
		savePath.createNewFile();
		this.fileOut = new BufferedOutputStream(Files.newOutputStream(Paths.get(savePath.getAbsolutePath()),
				StandardOpenOption.APPEND));
	}

	//XXX do not always reopen the file by using Files.write, but do it manually

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		while(writtenBytes < length) {
			if(!in.isReadable()) {
				return;
			}
			int readableBytes = in.readableBytes();
			writtenBytes += readableBytes;
			byte[] readBytes = new byte[readableBytes];
			in.readBytes(readBytes);
			fileOut.write(readBytes, 0, readBytes.length);

		} 
		
		//TODO last worked here, random file transfer failures...

		ctx.pipeline().replace(this, "MessageDecoder", new MessageDecoder());
		finishListener.fileTransferFinished(savePath);
		
		/*
		if(writtenChunksCount < chunkCount - 1) {
			
			//wait for the next chunk
			if(in.readableBytes() < chunkSize) {
				return;
			}
			
			byte[] readChunk = new byte[chunkSize];
			in.readBytes(readChunk);
			Files.write(Paths.get(savePath.getAbsolutePath()), readChunk, StandardOpenOption.APPEND);
			
			writtenChunksCount++;
			
		} else if(writtenChunksCount == chunkCount - 1) {
			

			
			long sizeOfTransmittedChunks = ((long) chunkCount - 1) * (long) chunkSize;
			int lastChunkSize = (int) (length - sizeOfTransmittedChunks);

			//wait for the last chunk
			if(in.readableBytes() < lastChunkSize) {
				return;
			}
			
			log.debug("Receiving last chunk. Size: " + lastChunkSize);

			byte[] readChunk = new byte[lastChunkSize];
			in.readBytes(readChunk);
			Files.write(Paths.get(savePath.getAbsolutePath()), readChunk, StandardOpenOption.APPEND);
			
			ctx.pipeline().replace(this, "MessageDecoder", new MessageDecoder());
			finishListener.fileTransferFinished(savePath);
			
		} else {
			throw new IOException("Error receiving chunked file. The written chunks cound is bigger than the chunk count.");
		}
*/
	}

}
