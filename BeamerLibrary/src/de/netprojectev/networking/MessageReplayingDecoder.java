package de.netprojectev.networking;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class MessageReplayingDecoder extends
		ReplayingDecoder<ReplayingDecoderState> {

	private int length;
	
	public MessageReplayingDecoder() {
		super(ReplayingDecoderState.READ_OPCODE);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		switch (state()) {
		case READ_OPCODE:
			byte opCode = in.readByte();			
			checkpoint(ReplayingDecoderState.READ_DATA_LENGTH);
			out.add(OpCode.values()[(int) opCode]);
			break;
		case READ_DATA_LENGTH:
			length = in.readInt();
			if(length > 0) {
				checkpoint(ReplayingDecoderState.READ_DATA);
			} else {
				checkpoint(ReplayingDecoderState.READ_OPCODE);
			}
			break;
		case READ_DATA:
			in.readerIndex(in.readerIndex() - 4);
			ByteBuf dataFrame = in.readBytes(length + 4);
			checkpoint(ReplayingDecoderState.READ_OPCODE);
			out.add(dataFrame);
			break;
		default:
			throw new Error("Shouldn't reach here.");
		}

	}

}
