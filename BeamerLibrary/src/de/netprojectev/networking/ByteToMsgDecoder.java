package de.netprojectev.networking;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ByteToMsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		OpCode opcode = OpCode.values()[in.readByte()];
		
		if(!in.isReadable()) {
			out.add(new Message<Object>(opcode));
		} else {
			
		}
		
	}

}
