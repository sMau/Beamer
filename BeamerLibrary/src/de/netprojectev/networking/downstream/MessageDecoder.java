package de.netprojectev.networking.downstream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;

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
import de.netprojectev.networking.downstream.ChunkedFileDecoder.FileTransferFinishedListener;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.utils.LoggerBuilder;

public class MessageDecoder extends ByteToMessageDecoder {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(MessageDecoder.class);

	private ByteBuf in;
	private ChannelHandlerContext ctx;
	private ArrayList<Object> data = new ArrayList<Object>(16);

	private boolean dataDecodeSuccess;
	private boolean decodingFile;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		// wait for the header
		if (in.readableBytes() < 2) {
			return;
		}

		in.markReaderIndex();

		OpCode opCode;
		opCode = OpCode.values()[in.readByte()];
		boolean containsData = in.readBoolean();
		this.dataDecodeSuccess = true;
		this.decodingFile = false;

		log.fine("Receiving new message, OpCode: " + opCode + ". Contains data: " + containsData);

		if (!containsData) {
			out.add(new Message(opCode));
		} else {
			this.in = in;
			this.ctx = ctx;
			decodeData(opCode);

			if (this.dataDecodeSuccess) {
				if (!this.decodingFile) {
					out.add(new Message(opCode, this.data));
				}
			} else {
				in.resetReaderIndex();
				return;
			}

		}

	}

	private boolean decodeBoolean() {
		if (this.in.readableBytes() < 1) {
			this.dataDecodeSuccess = false;
			return false;
		}
		return this.in.readBoolean();
	}

	private byte[] decodeByteArray() {
		if (this.in.readableBytes() < 4) {
			this.dataDecodeSuccess = false;
			return null;
		}
		byte[] decoded = new byte[this.in.readInt()];

		if (this.in.readableBytes() < decoded.length) {
			this.dataDecodeSuccess = false;
			return null;
		}

		this.in.readBytes(decoded);
		return decoded;
	}

	private ClientMediaFile decodeClientMediaFile() {
		UUID id = decodeUUID();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		UUID priorityID = decodeUUID();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		int showCount = decodeInt();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		String name = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		MediaType type = decodeMediaType();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		boolean current = decodeBoolean();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		byte[] preview = decodeByteArray();
		if (this.dataDecodeSuccess == false) {
			return null;
		}
		return ClientMediaFile.reconstruct(id, name, preview, priorityID, showCount, type, current);
	}

	private Countdown decodeCountdown() {
		String name = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		int durationInMinutes = decodeInt();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		return new Countdown(name, durationInMinutes);
	}

	private void decodeData(OpCode opCode) throws DecodeMessageException, IOException {

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
			int keyValueCount = this.in.readInt();
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
		case STC_ALL_FONTS:
			this.data.add(decodeFonts());
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
			decodeImageFile();
			break;
		case CTS_ADD_THEMESLIDE:
			decodeThemeslide();
			break;
		case CTS_LOGIN_REQUEST:
			this.data.add(decodeLoginData());
			break;
		case CTS_DISCONNECT:
			this.data.add(decodeString());
			break;
		case CTS_ADD_COUNTDOWN:
			this.data.add(decodeCountdown());
			break;
		case CTS_ADD_VIDEO_FILE:
			decodeVideoFile();
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

	private DequeueData decodeDequeueData() {
		UUID id = decodeUUID();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		int row = decodeInt();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		return new DequeueData(row, id);
	}

	private void decodeFile(OpCode opCode, FileTransferFinishedListener l) throws IOException {

		File pathOnDisk = null;
		switch (opCode) {
		case CTS_ADD_IMAGE_FILE:
			pathOnDisk = new File(ConstantsServer.SAVE_PATH + ConstantsServer.CACHE_PATH_IMAGES + UUID.randomUUID());
			break;
		case CTS_ADD_THEMESLIDE:
			pathOnDisk = new File(ConstantsServer.SAVE_PATH + ConstantsServer.CACHE_PATH_THEMESLIDES + UUID.randomUUID());
			break;
		case CTS_ADD_VIDEO_FILE:
			pathOnDisk = new File(ConstantsServer.SAVE_PATH + ConstantsServer.CACHE_PATH_VIDEOS + UUID.randomUUID());
			break;
		default:
			break;
		}

		// wait for file meta data
		if (this.in.readableBytes() < 16) {
			this.dataDecodeSuccess = false;
			return;
		}

		this.decodingFile = true;

		// decode file meta data
		long length = decodeLong();
		// int chunkSize = decodeInt();
		// int chunkCount = decodeInt();

		log.fine("Receiving file. Length: " + length);

		// replace with a file decode handler, which replaces itself after
		// completion again with this one here
		this.ctx.pipeline().replace(this, "ChunkedFileDecoder",
				new ChunkedFileDecoder(pathOnDisk, length, l));

	}

	private String[] decodeFonts() {
		String[] res = new String[this.in.readInt()];
		int i = 0;
		while (i < res.length) {
			res[i] = decodeString();
			if (this.dataDecodeSuccess == false) {
				return null;
			}
			i++;
		}
		return res;
	}

	private void decodeImageFile() throws IOException {
		final String name = decodeString();
		log.fine("Decoded file name: " + name);

		decodeFile(OpCode.CTS_ADD_IMAGE_FILE, new FileTransferFinishedListener() {

			@Override
			public void fileTransferFinished(File file) throws IOException {
				MessageDecoder.this.data.add(new ImageFile(name, PreferencesModelServer.getDefaultPriority(), file));
				sendUpstream(new Message(OpCode.CTS_ADD_IMAGE_FILE, MessageDecoder.this.data));
			}
		});
	}

	private int decodeInt() {
		if (this.in.readableBytes() < 4) {
			this.dataDecodeSuccess = false;
			return 0;
		}
		return this.in.readInt();
	}

	private Object decodeLoginData() {
		String alias = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		String key = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		return new LoginData(alias, key);
	}

	private long decodeLong() {
		if (this.in.readableBytes() < 8) {
			this.dataDecodeSuccess = false;
			return 0;
		}
		return this.in.readLong();
	}

	private MediaType decodeMediaType() {
		if (this.in.readableBytes() < 1) {
			this.dataDecodeSuccess = false;
			return null;
		}
		return MediaType.values()[this.in.readByte()];
	}

	private Priority decodePriority() {
		UUID id = decodeUUID();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		int minToShow = decodeInt();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		String name = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		boolean defaultPriority = decodeBoolean();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		Priority prio = Priority.reconstruct(name, minToShow, id);
		prio.setDefaultPriority(defaultPriority);
		return prio;
	}

	private Properties decodeProperties(int dataObjectCount) {
		Properties props = new Properties();

		for (int i = 0; i < dataObjectCount; i++) {
			String key = decodeString();
			String value = decodeString();
			if (this.dataDecodeSuccess == false) {
				return null;
			}
			props.setProperty(key, value);
		}

		return props;
	}

	private String decodeString() {
		byte[] bytes = decodeByteArray();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		return new String(bytes);
	}

	private Theme decodeTheme() {
		String themeName = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		byte[] imageData = decodeByteArray();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		return new Theme(themeName, imageData);
	}

	private Theme decodeThemeAck() {
		UUID themeID = decodeUUID();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		String themeName = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		byte[] imageData = decodeByteArray();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		return Theme.reconstruct(themeName, imageData, themeID);
	}

	private void decodeThemeslide() throws IOException {

		final String name = decodeString();
		if (this.dataDecodeSuccess == false) {
			return;
		}

		log.fine("Decoded file name: " + name);
		final UUID themeID = decodeUUID();
		if (this.dataDecodeSuccess == false) {
			return;
		}

		log.fine("Decoded theme id: " + themeID);

		decodeFile(OpCode.CTS_ADD_THEMESLIDE, new FileTransferFinishedListener() {
			@Override
			public void fileTransferFinished(File file) throws IOException {
				ImageFile imgFile = new ImageFile(name, PreferencesModelServer.getDefaultPriority(), file);
				MessageDecoder.this.data.add(new Themeslide(name, themeID, imgFile.getPriorityID(), imgFile));
				sendUpstream(new Message(OpCode.CTS_ADD_THEMESLIDE, MessageDecoder.this.data));
			}
		});

	}

	private TickerElement decodeTickerElement() {
		UUID id = decodeUUID();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		String text = decodeString();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		boolean show = decodeBoolean();
		if (this.dataDecodeSuccess == false) {
			return null;
		}

		return new TickerElement(text, id).setShow(show);
	}

	private UUID decodeUUID() {
		if (this.in.readableBytes() < 16) {
			this.dataDecodeSuccess = false;
			return null;
		}
		long least = decodeLong();
		long most = decodeLong();
		return new UUID(most, least);
	}

	private void decodeVideoFile() throws IOException {
		final String name = decodeString();
		if (this.dataDecodeSuccess == false) {
			return;
		}
		log.fine("Decoded file name: " + name);

		decodeFile(OpCode.CTS_ADD_VIDEO_FILE, new FileTransferFinishedListener() {

			@Override
			public void fileTransferFinished(File file) throws IOException {
				MessageDecoder.this.data.add(new VideoFile(name, file));
				sendUpstream(new Message(OpCode.CTS_ADD_VIDEO_FILE, MessageDecoder.this.data));
			}
		});

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

	private void sendUpstream(Message msg) {
		this.ctx.fireChannelRead(msg);
	}

}
