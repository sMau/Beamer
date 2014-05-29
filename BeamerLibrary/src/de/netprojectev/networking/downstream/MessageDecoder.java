package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sun.org.mozilla.javascript.internal.ObjArray;
import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.exceptions.DecodeMessageException;
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

	private ByteBuf inDup;
	private ArrayList<Object> data = new ArrayList<Object>(2);

	public MessageDecoder() {

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		OpCode opCode;
		int dataObjectCount = 0;

		/*
		 * wait for the header to be available
		 */

		in.markReaderIndex();

		if (in.readableBytes() < 5) {
			in.resetReaderIndex();
			return;
		}
		
		opCode = OpCode.values()[(int) in.readByte()];
		dataObjectCount = in.readInt();

		if (dataObjectCount <= 0) {
			out.add(new Message(opCode));
		} else {
			inDup = in.duplicate();
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

			}
			// now all data should be available
			out.add(new Message(opCode, decodeData(opCode, dataObjectCount)));
		}

	}

	private ArrayList<Object> decodeData(OpCode opCode, int dataObjectCount) throws DecodeMessageException {

		/*
		 * switch over all opcodes, indicating that data is contained
		 */

		data.clear();

		switch (opCode) {

		/*
		 * Server to client
		 */
		case STC_ALL_FONTS:
			break;
		case STC_PROPERTY_UPDATE_ACK:
			break;
		case STC_INIT_PROPERTIES:
			break;
		case STC_TIMELEFT_SYNC:
			break;
		case STC_ADD_THEME_ACK:
			break;
		case STC_ADD_PRIORITY_ACK:
			break;
		case STC_ADD_LIVE_TICKER_ELEMENT_ACK:
			break;
		case STC_EDIT_LIVE_TICKER_ELEMENT_ACK:
			break;
		case STC_ADD_MEDIA_FILE_ACK:

			break;
		case STC_RESET_SHOW_COUNT_ACK:
			break;
		case STC_LOGIN_DENIED:
			break;
		case STC_EDIT_MEDIA_FILE_ACK:
			break;

		case STC_REMOVE_LIVE_TICKER_ELEMENT_ACK:
			data.add(decodeUUID());
			break;
		case STC_SHOW_MEDIA_FILE_ACK:
			data.add(decodeUUID());
			break;
		case STC_DEQUEUE_MEDIAFILE_ACK:
			data.add(decodeUUID());
			break;
		case STC_QUEUE_MEDIA_FILE_ACK:
			data.add(decodeUUID());
			break;
		case STC_REMOVE_MEDIA_FILE_ACK:
			data.add(decodeUUID());
			break;
		case STC_REMOVE_THEME_ACK:
			data.add(decodeUUID());
			break;
		case STC_REMOVE_PRIORITY_ACK:
			data.add(decodeUUID());
			break;

		/*
		 * Client to server
		 */
		case CTS_ADD_LIVE_TICKER_ELEMENT:

			break;
		case CTS_ADD_VIDEO_FILE_DATA:

			break;
		case CTS_DEQUEUE_MEDIAFILE:

			break;
		case CTS_EDIT_MEDIA_FILE:

			break;
		case CTS_PROPERTY_UPDATE:

			break;
		case CTS_DISCONNECT:

			break;
		case CTS_RESET_SHOW_COUNT:

			break;
		case CTS_LOGIN_REQUEST:
			break;
		case CTS_ADD_THEME:

			break;
		case CTS_ADD_PRIORITY:
			UUID id = decodeUUID();
			int minToShow = decodeInt();
			String name = decodeString();
			boolean defaultPriority = decodeBoolean();

			Priority prio = new Priority(name, minToShow);
			prio.setDefaultPriority(defaultPriority);
			prio.setId(id);
			data.add(prio);

			break;
		case CTS_EDIT_LIVE_TICKER_ELEMENT:

			break;

		case CTS_ADD_COUNTDOWN:

			break;
		case CTS_ADD_IMAGE_FILE:

			break;
		case CTS_ADD_THEMESLIDE:

			break;
		case CTS_REMOVE_LIVE_TICKER_ELEMENT:
			data.add(decodeUUID());
			break;
		case CTS_SHOW_MEDIA_FILE:
			data.add(decodeUUID());
			break;
		case CTS_REMOVE_THEME:
			data.add(decodeUUID());
			break;
		case CTS_REMOVE_PRIORITY:
			data.add(decodeUUID());
			break;
		case CTS_REMOVE_MEDIA_FILE:
			data.add(decodeUUID());
			break;

		case CTS_QUEUE_MEDIA_FILE:
			data.add(decodeUUID());
			break;

		default:
			throw new DecodeMessageException("The header says the message contains data, but it does not.");
		}

		return data;
	}

	private String decodeString() {
		byte[] rawString = new byte[(int) inDup.readLong()];
		inDup.readBytes(rawString);
		return new String(rawString);
	}

	private boolean decodeBoolean() {
		inDup.readLong();
		return inDup.readBoolean();
	}

	private int decodeInt() {
		inDup.readLong();
		return inDup.readInt();
	}

	private UUID decodeUUID() {
		inDup.readLong(); // read length
		long least = inDup.readLong();
		long most = inDup.readLong();
		return new UUID(most, least);
	}

}
