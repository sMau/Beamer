package de.netprojectev.networking;

import de.netprojectev.server.datastructures.Countdown;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgToByteEncoder extends MessageToByteEncoder<Message<?>> {

	private ByteBuf out;
	private Message<?> msg;
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message<?> msg, ByteBuf out)
			throws Exception {
		
		this.out = out;
		this.msg = msg;
		
		out.writeByte(msg.getOpCode().ordinal());
		if(msg.getData() != null) {
			
			switch (msg.getOpCode()) {
			case CTS_ADD_COUNTDOWN:
				encodeCountdown();
				break;
			case CTS_ADD_IMAGE_FILE:
				encodeImageFile();
				break;
			case CTS_ADD_LIVE_TICKER_ELEMENT:
				encodeLiveTickerElement();
				break;
			case CTS_ADD_PRIORITY:
				encodePriority();
				break;
			case CTS_ADD_THEME:
				encodeTheme();
				break;
			case CTS_ADD_THEMESLIDE:
				encodeThemeslide();
				break;
			case CTS_ADD_VIDEO_FILE:
				encodeVideoFile();
				break;
			case CTS_DEQUEUE_MEDIAFILE:
				encodeUUID();
				break;
			case CTS_DISCONNECT:
				encodeDisconnect();
				break;
			case CTS_EDIT_LIVE_TICKER_ELEMENT:
				encodeLiveTickerElementEdit();
				break;
			case CTS_EDIT_MEDIA_FILE:
				encodeMediaFileEdit();
				break;
			case CTS_ENABLE_AUTO_MODE:
				break;
			case CTS_ENABLE_FULLSCREEN:
				break;
			case CTS_ENABLE_LIVE_TICKER:
				break;
			case CTS_HEARTBEAT_ACK:
				break;
			case CTS_LOGIN_REQUEST:
				break;
			case CTS_PROPERTY_UPDATE:
				break;
			case CTS_QUEUE_MEDIA_FILE:
				break;
			case CTS_REMOVE_LIVE_TICKER_ELEMENT:
				break;
			case CTS_REMOVE_MEDIA_FILE:
				break;
			case CTS_REMOVE_PRIORITY:
				break;
			case CTS_REMOVE_THEME:
				break;
			case CTS_REQUEST_FULL_SYNC:
				break;
			case CTS_REQUEST_SERVER_SHUTDOWN:
				break;
			case CTS_RESET_SHOW_COUNT:
				break;
			case CTS_SHOW_MEDIA_FILE:
				break;
			case CTS_SHOW_NEXT_MEDIA_FILE:
				break;
			case CTS_SHOW_PREVIOUS_MEDIA_FILE:
				break;
			case STC_ADD_LIVE_TICKER_ELEMENT_ACK:
				break;
			case STC_ADD_MEDIA_FILE_ACK:
				break;
			case STC_ADD_PRIORITY_ACK:
				break;
			case STC_ADD_THEME_ACK:
				break;
			case STC_ALL_FONTS:
				break;
			case STC_CONNECTION_ACK:
				break;
			case STC_DEQUEUE_MEDIAFILE_ACK:
				break;
			case STC_DISABLE_AUTO_MODE_ACK:
				break;
			case STC_DISABLE_FULLSCREEN_ACK:
				break;
			case STC_DISABLE_LIVE_TICKER_ACK:
				break;
			case STC_EDIT_LIVE_TICKER_ELEMENT_ACK:
				break;
			case STC_EDIT_MEDIA_FILE_ACK:
				break;
			case STC_ENABLE_AUTO_MODE_ACK:
				break;
			case STC_ENABLE_FULLSCREEN_ACK:
				break;
			case STC_ENABLE_LIVE_TICKER_ACK:
				break;
			case STC_FORCE_RECONNECT:
				break;
			case STC_FULL_SYNC_START:
				break;
			case STC_FULL_SYNC_STOP:
				break;
			case STC_HEARTBEAT_REQUEST:
				break;
			case STC_INIT_PROPERTIES:
				break;
			case STC_LOGIN_DENIED:
				break;
			case STC_PROPERTY_UPDATE_ACK:
				break;
			case STC_QUEUE_MEDIA_FILE_ACK:
				break;
			case STC_REMOVE_LIVE_TICKER_ELEMENT_ACK:
				break;
			case STC_REMOVE_MEDIA_FILE_ACK:
				break;
			case STC_REMOVE_PRIORITY_ACK:
				break;
			case STC_REMOVE_THEME_ACK:
				break;
			case STC_RESET_SHOW_COUNT_ACK:
				break;
			case STC_SERVER_SHUTDOWN:
				break;
			case STC_SHOW_MEDIA_FILE_ACK:
				break;
			case STC_TIMELEFT_SYNC:
				break;
			default:
				break;
			
		
			}
		}
		
	}

	private void encodeMediaFileEdit() {
		// TODO Auto-generated method stub
		
	}

	private void encodeLiveTickerElementEdit() {
		// TODO Auto-generated method stub
		
	}

	private void encodeDisconnect() {
		// TODO Auto-generated method stub
		
	}

	private void encodeUUID() {
		// TODO Auto-generated method stub
		
	}

	private void encodeLiveTickerElement() {
		// TODO Auto-generated method stub
		
	}

	private void encodeVideoFile() {
		// TODO Auto-generated method stub
		
	}

	private void encodeThemeslide() {
		// TODO Auto-generated method stub
		
	}

	private void encodeTheme() {
		// TODO Auto-generated method stub
		
	}

	private void encodePriority() {
		// TODO Auto-generated method stub
		
	}

	private void encodeString() {
		// TODO Auto-generated method stub
		
	}

	private void encodeImageFile() {
		// TODO Auto-generated method stub
		
	}

	private void encodeCountdown() {
		Countdown cntDwn = (Countdown) msg.getData()[0];
		
	}

}
