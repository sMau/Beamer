package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.datastructures.Priority;
import de.netprojectev.datastructures.Theme;
import de.netprojectev.datastructures.TickerElement;
import de.netprojectev.exceptions.DecodeMessageException;
import de.netprojectev.networking.DequeueData;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.utils.LoggerBuilder;

public class MessageDecoder extends ReplayingDecoder<Void> {

	private static final Logger log = LoggerBuilder.createLogger(MessageDecoder.class);

	/*
	 * this one passes MEssage Objects to the next Handler, which should either
	 * be a clientMessageProxy or a ServerMessageProxy
	 * 
	 * it only gets "really active" for file receiving as they are written to
	 * disk directly and only a reference is passed.
	 * 
	 * depending on opcodes different data encoding steps should be done
	 */

	private ByteBuf in;
	private ArrayList<Object> data = new ArrayList<Object>(15);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		OpCode opCode;
		opCode = OpCode.values()[in.readByte()];
		boolean containsData = in.readBoolean();
		
		/*
		 * wait for the data to be available
		 */
		if (!containsData) {
			out.add(new Message(opCode));
		} else {
			this.in = in;			
			decodeData(opCode);
			out.add(new Message(opCode, data));
		}

	}

	private void decodeData(OpCode opCode) throws DecodeMessageException {

		this.data.clear();
		
		/*
		 * switch over all opcodes, indicating that data is contained
		 */
		switch (opCode) {

		/*
		 * Server to client
		 */

		case STC_PROPERTY_UPDATE_ACK:
			String key = decodeString();
			String value = decodeString();
			this.data.add(key);
			this.data.add(value);
			break;
		case STC_INIT_PROPERTIES:
			int keyValueCount = in.readInt();
			log.info("Init Properties key val count: " + keyValueCount);
			decodeProperties(keyValueCount);
			break;
		case STC_TIMELEFT_SYNC:
			long timeleft = decodeLong();
			this.data.add(timeleft);
			break;
		case STC_ADD_THEME_ACK:
			this.data.add(decodeThemeAck());
			break;
		case STC_ADD_PRIORITY_ACK:
			this.data.add(decodePriority());
			break;
		case STC_ADD_LIVE_TICKER_ELEMENT_ACK:
			this.data.add(decodeTickerElement());
			break;
		case STC_EDIT_LIVE_TICKER_ELEMENT_ACK:
			this.data.add(decodeTickerElement());
			break;
		case STC_ADD_MEDIA_FILE_ACK:
			this.data.add(decodeClientMediaFile());
			break;
		case STC_EDIT_MEDIA_FILE_ACK:
			this.data.add(decodeClientMediaFile());
			break;
		case STC_LOGIN_DENIED:
			this.data.add(decodeString());
			break;
		case STC_RESET_SHOW_COUNT_ACK:
			this.data.add(decodeUUID());
			break;
		case STC_REMOVE_LIVE_TICKER_ELEMENT_ACK:
			this.data.add(decodeUUID());
			break;
		case STC_SHOW_MEDIA_FILE_ACK:
			this.data.add(decodeUUID());
			break;
		case STC_DEQUEUE_MEDIAFILE_ACK:
			this.data.add(decodeDequeueData());
			break;
		case STC_QUEUE_MEDIA_FILE_ACK:
			this.data.add(decodeUUID());
			break;
		case STC_REMOVE_MEDIA_FILE_ACK:
			this.data.add(decodeUUID());
			break;
		case STC_REMOVE_THEME_ACK:
			this.data.add(decodeUUID());
			break;
		case STC_REMOVE_PRIORITY_ACK:
			this.data.add(decodeUUID());
			break;

		/*
		 * Client to server
		 */
		case CTS_ADD_LIVE_TICKER_ELEMENT:
			this.data.add(decodeTickerElement());
			break;
		case CTS_PROPERTY_UPDATE:
			String key1 = decodeString();
			String value1 = decodeString();
			this.data.add(key1);
			this.data.add(value1);
			break;
		case CTS_RESET_SHOW_COUNT:
			this.data.add(decodeUUID());
			break;
		case CTS_ADD_THEME:
			this.data.add(decodeTheme());
			break;
		case CTS_ADD_PRIORITY:
			this.data.add(decodePriority());
			break;
		case CTS_EDIT_LIVE_TICKER_ELEMENT:
			this.data.add(decodeTickerElement());
			break;
		case CTS_EDIT_MEDIA_FILE:
			this.data.add(decodeClientMediaFile());
			break;
		case CTS_ADD_IMAGE_FILE:
			this.data.add(decodeImageFile());
			break;
		case CTS_ADD_THEMESLIDE:
			this.data.add(decodeThemeslide());
			break;
		case CTS_LOGIN_REQUEST:
			this.data.add(decodeLoginData());
			break;
		case CTS_DISCONNECT:
			this.data.add(decodeString());
			break;
		case CTS_ADD_COUNTDOWN:
			// TODO
			break;
		case CTS_ADD_VIDEO_FILE_DATA:
			// TODO
			break;
		case CTS_DEQUEUE_MEDIAFILE:
			this.data.add(decodeDequeueData());
			break;
		case CTS_REMOVE_LIVE_TICKER_ELEMENT:
			this.data.add(decodeUUID());
			break;
		case CTS_SHOW_MEDIA_FILE:
			this.data.add(decodeUUID());
			break;
		case CTS_REMOVE_THEME:
			this.data.add(decodeUUID());
			break;
		case CTS_REMOVE_PRIORITY:
			this.data.add(decodeUUID());
			break;
		case CTS_REMOVE_MEDIA_FILE:
			this.data.add(decodeUUID());
			break;
		case CTS_QUEUE_MEDIA_FILE:
			this.data.add(decodeUUID());
			break;
		default:
			throw new DecodeMessageException("The header says the message contains data, but it does not.");
		}

	}
	
 //TODO add states to the replaying decoder (http://netty.io/4.0/api/io/netty/handler/codec/ReplayingDecoder.html)
	// to improve the performance for longer messages

	private int decodeInt() {
		return this.in.readInt();
	}
	
	private long decodeLong() {
		return this.in.readLong();
	}
	private MediaType decodeMediaType() {
		return MediaType.values()[this.in.readByte()];
	}
	
	private boolean decodeBoolean() {
		return this.in.readBoolean();
	}

	private byte[] decodeByteArray() {
		byte[] decoded = new byte[this.in.readInt()];
		this.in.readBytes(decoded);
		return decoded;
	}

	private Properties decodeProperties(int dataObjectCount) {
		Properties props = new Properties();
		
		for(int i = 0; i < dataObjectCount; i++) {
			String key = decodeString();
			String value  = decodeString();
			props.setProperty(key, value);
		}
		
		return props;
	}
	

	private ClientMediaFile decodeClientMediaFile() {
		UUID id = decodeUUID();
		String name = decodeString();
		byte[] preview = decodeByteArray();
		UUID priorityID = decodeUUID();
		int showCount = decodeInt();
		MediaType type = decodeMediaType();
		boolean current = decodeBoolean();

		return ClientMediaFile.reconstruct(id, name, preview, priorityID, showCount, type, current);
	}
	
	private DequeueData decodeDequeueData() {
		UUID id = decodeUUID();
		int row = decodeInt();
		return new DequeueData(row, id);
	}

	private ImageFile decodeImageFile() {
		String name = decodeString();
		byte[] data = decodeByteArray();
		return new ImageFile(name, PreferencesModelServer.getDefaultPriority(), data);
	}

	private Object decodeLoginData() {
		String alias = decodeString();
		String key = decodeString();
		return new LoginData(alias, key);
	}

	private Priority decodePriority() {
		UUID id = decodeUUID();
		int minToShow = decodeInt();
		String name = decodeString();
		boolean defaultPriority = decodeBoolean();

		Priority prio = Priority.reconstruct(name, minToShow, id);
		prio.setDefaultPriority(defaultPriority);
		return prio;
	}

	private String decodeString() {
		return new String(decodeByteArray());
	}
	
	private Theme decodeTheme() {
		String themeName = decodeString();
		byte[] imageData = decodeByteArray();
		return new Theme(themeName, imageData);
	}

	private Theme decodeThemeAck() {
		UUID themeID = decodeUUID();
		String themeName = decodeString();
		byte[] imageData = decodeByteArray();
		return Theme.reconstruct(themeName, imageData, themeID);
	}

	private Themeslide decodeThemeslide() {
		ImageFile imageFile = decodeImageFile();
		UUID themeID = decodeUUID();
		return new Themeslide(imageFile.getName(), themeID, imageFile.getPriorityID(), imageFile);
	}

	private TickerElement decodeTickerElement() {
		UUID id = decodeUUID();
		String text = decodeString();
		boolean show = decodeBoolean();
		return new TickerElement(text, id).setShow(show);
	}

	private UUID decodeUUID() {
		long least = decodeLong();
		long most = decodeLong();
		return new UUID(most, least);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

}
