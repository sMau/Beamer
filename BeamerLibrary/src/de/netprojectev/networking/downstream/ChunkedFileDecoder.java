package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Logger;

import de.netprojectev.utils.LoggerBuilder;

public class ChunkedFileDecoder extends ByteToMessageDecoder {

	public interface FileTransferFinishedListener {
		public void fileTransferFinished(File file) throws IOException;
	}

	private static final Logger log = LoggerBuilder.createLogger(ChunkedFileDecoder.class);

	private FileTransferFinishedListener finishListener;
	private FileOutputStream fileOut;
	private File savePath;
	private long length;
	private long writtenBytes;
	
	public ChunkedFileDecoder(File savePath, long length, FileTransferFinishedListener finishListener) throws IOException {
		this.savePath = savePath;
		this.length = length;
		this.finishListener = finishListener;
		this.writtenBytes = 0;
		savePath.createNewFile();
		this.fileOut = new FileOutputStream(savePath);
	}

	//XXX do not always reopen the file by using Files.write, but do it manually

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		while(writtenBytes < length) {
			if(!in.isReadable()) {
				return;
			}
			
			int readableBytes = in.readableBytes();
			byte[] readBytes;
			
			if(length > writtenBytes + readableBytes) {
				readBytes = new byte[readableBytes];
				writtenBytes += readableBytes;
			} else {
				readBytes = new byte[(int) (length - writtenBytes)];
				writtenBytes += length - writtenBytes;
			}

			log.debug("Writing next bytes. Writtenbytes: " + writtenBytes
					+ " currently readable bytes: " + readableBytes);
			
			in.readBytes(readBytes);
			fileOut.write(readBytes);
			fileOut.flush();

		} 
		
		finishListener.fileTransferFinished(savePath);
		ctx.pipeline().replace(this, "MessageDecoder", new MessageDecoder());
		
	}

}
