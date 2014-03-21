package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.ByteToMessageCodec;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class FileByteCodec extends ByteToMessageCodec<File>{

	@Override
	protected void encode(ChannelHandlerContext ctx, File msg, ByteBuf out) throws Exception {
		FileInputStream fis = new FileInputStream(msg);
		FileRegion fileRegion = new DefaultFileRegion(fis.getChannel(), 0, msg.length());
		
		out.writeInt((int) msg.length()); //XXX dirty cast works only for files <=4GB, solution work with frames..
		ctx.write(fileRegion);
		ctx.flush();
		
		fis.close();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		if(in.readableBytes() < 4) {
			return;
		}
		
		in.markReaderIndex();
		
		int byteCount = in.readInt();
		if(in.readableBytes() < byteCount) {
			in.resetReaderIndex();
			return;
		}
		
		byte[] decoded = new byte[byteCount]; //XXX could cause overflows
        in.readBytes(decoded);

        out.add(decoded);
		
	}

}
