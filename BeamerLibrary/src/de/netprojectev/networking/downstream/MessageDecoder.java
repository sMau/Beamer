package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.math.BigInteger;
import java.util.List;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;

public class MessageDecoder extends ByteToMessageDecoder {

	/*
	 * this one passes MEssage Objects to the next Handler,
	 * which should either be a clientMessageProxy or a ServerMessageProxy
	 * 
	 * it only gets "really active" for file receiving as they are written to disk directly
	 * and only a reference is passed.
	 * 
	 * depending on opcodes different data encoding steps should be done
	 */
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		Message msg;
		OpCode opCode;
		int dataObjectCount = 0;
		
		/*
		 * wait for the header to be available
		 */
		if(in.isReadable() && in.readableBytes() >= 2) {
			opCode = OpCode.values()[(int) in.readByte()];
			dataObjectCount = in.readByte();
		} else {
			return;
		}
		
		if(dataObjectCount <= 0) {
			out.add(new Message(opCode));
		} else {
			
			for (int i = 0; i < dataObjectCount; i++) {
				while(in.readableBytes() < 8) {
				}		
				long dataObjectLength = in.readLong();
				
				/*
				 * @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // Wait until the length prefix is available.
        if (in.readableBytes() < 5) {
            return;
        }

        in.markReaderIndex();

        // Check the magic number.
        int magicNumber = in.readUnsignedByte();
        if (magicNumber != 'F') {
            in.resetReaderIndex();
            throw new CorruptedFrameException(
                    "Invalid magic number: " + magicNumber);
        }

        // Wait until the whole data is available.
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        // Convert the received data into a new BigInteger.
        byte[] decoded = new byte[dataLength];
        in.readBytes(decoded);

        out.add(new BigInteger(decoded));
    } 
				 */
				
				
				
			}
			
			
			
			
		}
		
		
	}

}
