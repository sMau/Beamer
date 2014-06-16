package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.File;
import java.io.IOException;
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

	private int writtenChunksCount = 0;
	private FileTransferFinishedListener finishListener;

	private File savePath;
	private MessageDecoder currentMsgDecoder;
	private int chunkSize;
	private int chunkCount;
	private long length;

	public ChunkedFileDecoder(MessageDecoder currentMsgDecoder, File savePath, long length,
			int chunkSize, int chunkCount, FileTransferFinishedListener finishListener) throws IOException {
		this.savePath = savePath;
		this.currentMsgDecoder = currentMsgDecoder;
		this.chunkCount = chunkCount;
		this.length = length;
		this.chunkSize = chunkSize;
		this.finishListener = finishListener;
		
		savePath.createNewFile();
		
	}

	//XXX do not always reopen the file by using Files.write, but do it manually
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		if(writtenChunksCount < chunkCount - 1) {
			
			//wait for the next chunk
			if(in.readableBytes() < chunkSize) {
				return;
			}
			
			log.debug("Reciving chunk #" + writtenChunksCount);
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
			
			ctx.pipeline().replace(this, "MessageDecoder", currentMsgDecoder);
			finishListener.fileTransferFinished(savePath);
			
		} else {
			throw new IOException("Error receiving chunked file. The written chunks cound is bigger than the chunk count.");
		}

	}

}
