package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.netprojectev.datastructures.Priority;
import de.netprojectev.datastructures.Theme;
import de.netprojectev.datastructures.TickerElement;
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

		/*
		 * wait for the data to be available
		 */
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

		case STC_PROPERTY_UPDATE_ACK:
			String key = decodeString();
			String value = decodeString();
			data.add(key);
			data.add(value);
			break;
		case STC_INIT_PROPERTIES:
			// TODO properties object is sent here, maybe change to repeatedly
			// sending single props
			break;
		case STC_TIMELEFT_SYNC:
			long timeleft = decodeLong();
			data.add(timeleft);
			break;
		case STC_ADD_THEME_ACK:
			data.add(decodeTheme());
			break;
		case STC_ADD_PRIORITY_ACK:
			data.add(decodePriority());
			break;
		case STC_ADD_LIVE_TICKER_ELEMENT_ACK:
			data.add(decodeTickerElement());
			break;
		case STC_EDIT_LIVE_TICKER_ELEMENT_ACK:
			data.add(decodeTickerElement());
			break;
		case STC_ADD_MEDIA_FILE_ACK:
			// TODO polymorphism at the end :D
			break;
		case STC_EDIT_MEDIA_FILE_ACK:
			// TODO polymorphism at the end :D
			break;
		case STC_LOGIN_DENIED:
			// TODO anyway check the complete login procedure
			break;
		case STC_RESET_SHOW_COUNT_ACK:
			data.add(decodeUUID());
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
			data.add(decodeTickerElement());
			break;
		case CTS_PROPERTY_UPDATE:
			String key1 = decodeString();
			String value1 = decodeString();
			data.add(key1);
			data.add(value1);
			break;
		case CTS_RESET_SHOW_COUNT:
			data.add(decodeUUID());
			break;
		case CTS_ADD_THEME:
			data.add(decodeTheme());
			break;
		case CTS_ADD_PRIORITY:
			data.add(decodePriority());
			break;
		case CTS_EDIT_LIVE_TICKER_ELEMENT:
			data.add(decodeTickerElement());
			break;
		case CTS_ADD_COUNTDOWN:
			
			break;
		case CTS_EDIT_MEDIA_FILE:
			break;
		case CTS_ADD_IMAGE_FILE:
			break;
		case CTS_ADD_THEMESLIDE:
			break;
		case CTS_ADD_VIDEO_FILE_DATA:
			break;
		case CTS_LOGIN_REQUEST:
			break;
		case CTS_DISCONNECT:
			break;
		case CTS_DEQUEUE_MEDIAFILE:
			data.add(decodeUUID());
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

	private Object decodeTickerElement() {
		UUID id = decodeUUID();
		String text = decodeString();
		boolean show = decodeBoolean();
		return new TickerElement(text, id).setShow(show);
	}

	private Theme decodeTheme() {
		UUID themeID = decodeUUID();
		String themeName = decodeString();
		byte[] imageData = decodeByteArray();
		return new Theme(themeName, imageData, themeID);
	}

	private Priority decodePriority() {
		UUID id = decodeUUID();
		int minToShow = decodeInt();
		String name = decodeString();
		boolean defaultPriority = decodeBoolean();

		Priority prio = new Priority(name, minToShow, id);
		prio.setDefaultPriority(defaultPriority);
		return prio;
	}

	private byte[] decodeByteArray() {
		byte[] decoded = new byte[(int) inDup.readLong()];
		inDup.readBytes(decoded);
		return decoded;
	}

	private long decodeLong() {
		inDup.readLong();
		return inDup.readLong();
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
