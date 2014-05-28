package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;

public class MessageDecoder extends ByteToMessageDecoder {

	/*
	 * this one passes MEssage Objects to the next Handler, which should either
	 * be a clientMessageProxy or a ServerMessageProxy
	 * 
	 * it only gets "really active" for file receiving as they are written to
	 * disk directly and only a reference is passed.
	 * 
	 * depending on opcodes different data encoding steps should be done
	 */

	private final DataObjectDecoder dataDecoder;
	private ArrayList<byte[]> encodedDataObjects;

	public MessageDecoder() {
		this.dataDecoder = new DataObjectDecoder();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		Message msg;
		OpCode opCode;
		int dataObjectCount = 0;

		/*
		 * wait for the header to be available
		 */

		in.markReaderIndex();

		if (in.isReadable() && in.readableBytes() >= 2) {
			opCode = OpCode.values()[(int) in.readByte()];
			dataObjectCount = in.readByte();
		} else {
			in.resetReaderIndex();
			return;
		}

		if (dataObjectCount <= 0) {
			msg = new Message(opCode);
		} else {

			encodedDataObjects = new ArrayList<byte[]>();
			for (int i = 0; i < dataObjectCount; i++) {
				if (in.readableBytes() < 8) {
					in.resetReaderIndex();
					return;
				}
				// TODO big file transfer is ignored here, have to fix this
				long dataObjectLength = in.readLong();
				if (in.readableBytes() < dataObjectLength) {
					in.resetReaderIndex();
					return;
				}
				byte[] encodedData = new byte[(int) dataObjectLength];
				in.readBytes(encodedData);
				encodedDataObjects.add(encodedData);
			}

			msg = dataDecoder.decodeMessage(opCode, encodedDataObjects);

		}
		out.add(msg);
	}

}
