package old.de.netprojectev.networking;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ByteToFileDecoder extends ByteToMessageDecoder {

	private String pathToSave;
	private FileOutputStream output;

	public ByteToFileDecoder(String pathToSave) {
		super();
		this.pathToSave = pathToSave;

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		if (in.readableBytes() < 8) {
			return;
		}

		long fileLength = in.readLong();
		this.output = new FileOutputStream(new File(pathToSave));
		long readBytesCounter = 0;

		while (readBytesCounter < fileLength) {

			if (in.isReadable()) {
				output.write(in.readByte());
				readBytesCounter++;
			}
		}

	}

}
