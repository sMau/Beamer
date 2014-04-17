package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.File;
import java.io.FileInputStream;

public class FileByteEncoder extends MessageToByteEncoder<File> {

	@Override
	protected void encode(ChannelHandlerContext ctx, File msg, ByteBuf out) throws Exception {
		FileInputStream fis = new FileInputStream(msg);
		FileRegion fileRegion = new DefaultFileRegion(fis.getChannel(), 0, msg.length());
		
		out.writeLong(msg.length());
		ctx.write(fileRegion); //XXX not sure if this works
		ctx.flush();
		
		fis.close();
	}

}
