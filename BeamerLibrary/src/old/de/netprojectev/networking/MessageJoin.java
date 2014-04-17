package old.de.netprojectev.networking;

import java.io.Serializable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.logging.log4j.Logger;

import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.utils.LoggerBuilder;

public class MessageJoin extends ChannelInboundHandlerAdapter {
	
	private static final Logger log = LoggerBuilder.createLogger(MessageJoin.class);
	
	private MessageDecoderState currentState = MessageDecoderState.READ_OPCODE;
		
	private OpCode currentOpCode;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		switch (currentState) {
		case READ_OPCODE:
			currentOpCode = (OpCode) msg;
			if(currentOpCode.isDataContained()) {
				currentState = MessageDecoderState.READ_DATA;
			} else {
				ctx.fireChannelRead(new Message(currentOpCode));
			}
			break;
		case READ_DATA:
			ctx.fireChannelRead(new Message(currentOpCode, (Serializable[]) msg));
			currentOpCode = null;
			currentState = MessageDecoderState.READ_OPCODE;
			break;
		default:
			log.error("should never be reached");
			break;
		}
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler, forcing reconnect.", cause.getCause());
		ctx.channel().close(); //XXX
	}

}
