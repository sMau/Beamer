package de.netprojectev.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MessageReplayingDecoder extends
		ReplayingDecoder<MessageDecoderState> {
	
	public MessageReplayingDecoder() {
		super(MessageDecoderState.READ_OPCODE);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		switch (state()) {
		case READ_OPCODE:
			byte opCode = in.readByte();
			OpCode readOpCode = OpCode.values()[(int) opCode];
			if(readOpCode.isContainsData()) {
				checkpoint(MessageDecoderState.READ_DATA);
			} else {
				checkpoint(MessageDecoderState.READ_OPCODE);
			}
			out.add(readOpCode);
			break;
		case READ_DATA:
			in.markReaderIndex();
			int length = in.readInt();
			in.readerIndex(in.readerIndex() - 4);
			ByteBuf dataFrame = in.readBytes(length + 4);
			checkpoint(MessageDecoderState.READ_OPCODE);
			out.add(dataFrame);
			break;
		default:
			throw new Error("Shouldn't reach here.");
		}

	}

}
