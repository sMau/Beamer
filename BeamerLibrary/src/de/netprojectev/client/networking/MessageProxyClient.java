package de.netprojectev.client.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Level;

import de.netprojectev.client.Client;
import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.datastructures.Priority;
import de.netprojectev.datastructures.Theme;
import de.netprojectev.datastructures.TickerElement;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.networking.DequeueData;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.utils.LoggerBuilder;
import de.netprojectev.utils.MediaFileFilter;

public class MessageProxyClient extends MessageToMessageDecoder<Message> {

	public interface ServerPropertyUpdateListener {
		public void propertyUpdated();
	}

	public interface ServerShutdownListener {
		public void serverShutdown();
	}

	public interface TimeSyncListener {
		public void timesync(long timeLeftInSeconds);
	}

	public interface MessageReceivedListener {
		public void received(Message msg);
	}

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(MessageProxyClient.class);

	private final Client client;

	private final MediaModelClient mediaModel;
	private final TickerModelClient tickerModel;
	private final PreferencesModelClient prefs;

	private final Class<? extends PreferencesModelClient> prefsModelClazz;

	private TimeSyncListener timeSyncListener;
	private ServerShutdownListener serverShutdownListener;
	private ServerPropertyUpdateListener serverPropertyUpdateListener;

	public void setMsgReceivedListener(MessageReceivedListener msgReceivedListener) {
		this.msgReceivedListener = msgReceivedListener;
	}

	private MessageReceivedListener msgReceivedListener;

	private Timer autoReconnectTimer;

	private boolean fullsync;

	private Channel channelToServer;

	public MessageProxyClient(Client client, Class<? extends PreferencesModelClient> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
		this.mediaModel = new MediaModelClient(this);
		this.tickerModel = new TickerModelClient(this);

		this.prefsModelClazz = clazz;

		this.prefs = (PreferencesModelClient) clazz.getConstructors()[0].newInstance(this);

		this.client = client;
		this.fullsync = false;

	}

	private void autoModeDisabled(Message msg) throws PriorityDoesNotExistException {
		this.prefs.disableAutomode();
	}

	private void autoModeEnabled(Message msg) throws PriorityDoesNotExistException {
		this.prefs.enableAutomode(this.fullsync);
	}

	private void connectionSuccessful(Message msg) {
		if (this.autoReconnectTimer != null) {
			this.autoReconnectTimer.cancel();
			this.autoReconnectTimer.purge();
			this.autoReconnectTimer = null;
		}
		this.client.loginSuccess();
		sendRequestFullSync();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
		receiveMessage(msg);
	}

	public void errorRequestFullSync(Exception e) {
		log.log(Level.WARNING, "The media read for updating does not exist. Requesting full sync.", e);
		sendRequestFullSync();
		this.client.getGui().errorRequestingFullsyncDialog();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.log(Level.WARNING, "Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

	private void fullscreenDisabled(Message msg) {
		this.prefs.disableFullscreen();
	}

	private void fullscreenEnabled(Message msg) {
		this.prefs.enableFullscreen();
	}

	private void fullSyncStart() {
		this.fullsync = true;
	}

	private void fullSyncStop() {
		this.fullsync = false;
	}

	public Channel getChannelToServer() {
		return this.channelToServer;
	}

	public Client getClient() {
		return this.client;
	}

	public MediaModelClient getMediaModel() {
		return this.mediaModel;
	}

	public PreferencesModelClient getPrefs() {
		return this.prefs;
	}

	public Class<? extends PreferencesModelClient> getPrefsModelClazz() {
		return this.prefsModelClazz;
	}

	public TickerModelClient getTickerModel() {
		return this.tickerModel;
	}

	private void hearbeatRequest() {
		sendMessageToServer(new Message(OpCode.CTS_HEARTBEAT_ACK));
	}

	private void initServerProperties(Message msg) {
		Properties serverProps = (Properties) msg.getData().get(0);
		this.prefs.initServerProperties(serverProps);
	}

	private void liveTickerDisabled(Message msg) {
		this.prefs.disableLiveTicker();
	}

	private void liveTickerElementAdded(Message msg) {
		TickerElement toAdd = (TickerElement) msg.getData().get(0);
		this.tickerModel.addTickerElement(toAdd);
	}

	private void liveTickerElementEdited(Message msg) {
		TickerElement e = (TickerElement) msg.getData().get(0);
		try {
			this.tickerModel.replaceTickerElement(e);
		} catch (MediaDoesNotExsistException e1) {
			errorRequestFullSync(e1);
		}
	}

	private void liveTickerElementRemoved(Message msg) throws MediaDoesNotExsistException {
		UUID toRemove = (UUID) msg.getData().get(0);
		this.tickerModel.removeTickerElement(toRemove);
	}

	private void liveTickerEnabled(Message msg) {
		this.prefs.enableLiveTicker();
	}

	private void loginDenied(Message msg) {
		if (this.autoReconnectTimer != null) {
			this.autoReconnectTimer.cancel();
			this.autoReconnectTimer.purge();
			this.autoReconnectTimer = null;
		}
		this.client.loginFailed((String) msg.getData().get(0));
	}

	private void mediaFileAdded(Message msg) {
		ClientMediaFile toAdd = (ClientMediaFile) msg.getData().get(0);
		this.mediaModel.addMediaFile(toAdd);
	}

	private void mediaFileDequeued(Message msg) throws MediaDoesNotExsistException,
	OutOfSyncException {
		DequeueData toDequeue = (DequeueData) msg.getData().get(0);
		this.mediaModel.dequeueMediaFile(toDequeue.getRow(), toDequeue.getId());

	}

	private void mediaFileEdited(Message msg) {
		ClientMediaFile media = (ClientMediaFile) msg.getData().get(0);
		try {
			this.mediaModel.replaceMediaFile(media);
		} catch (MediaDoesNotExsistException e) {
			errorRequestFullSync(e);
		}
	}

	private void mediaFileQueued(Message msg) throws MediaDoesNotExsistException {
		UUID toQueue = (UUID) msg.getData().get(0);
		this.mediaModel.queueMediaFile(toQueue);
	}

	private void mediaFileRemoved(Message msg) throws MediaDoesNotExsistException {

		UUID toRemove = (UUID) msg.getData().get(0);
		this.mediaModel.removeMediaFile(toRemove);
	}

	private void mediaFileShowing(Message msg) throws MediaDoesNotExsistException, PriorityDoesNotExistException {
		UUID fileShowing = (UUID) msg.getData().get(0);
		this.mediaModel.setAsCurrent(fileShowing);
	}

	private void priorityAdded(Message msg) {
		Priority toAdd = (Priority) msg.getData().get(0);
		this.prefs.prioAdded(toAdd);
	}

	private void priorityRemoved(Message msg) {
		UUID toRemove = (UUID) msg.getData().get(0);
		this.prefs.prioRemoved(toRemove);
	}

	private void receivedServerFonts(Message msg) {
		String[] fontFamilies = (String[]) msg.getData().get(0);
		this.prefs.setServerFonts(fontFamilies);
	}

	public void receiveMessage(Message msg) throws UnkownMessageException,
	MediaDoesNotExsistException, OutOfSyncException, PriorityDoesNotExistException {
		log.fine("Receiving message: " + msg.toString());
		switch (msg.getOpCode()) {
		case STC_ADD_MEDIA_FILE_ACK:
			mediaFileAdded(msg);
			break;
		case STC_ADD_LIVE_TICKER_ELEMENT_ACK:
			liveTickerElementAdded(msg);
			break;
		case STC_ADD_PRIORITY_ACK:
			priorityAdded(msg);
			break;
		case STC_ADD_THEME_ACK:
			themeAdded(msg);
			break;
		case STC_REMOVE_LIVE_TICKER_ELEMENT_ACK:
			liveTickerElementRemoved(msg);
			break;
		case STC_REMOVE_MEDIA_FILE_ACK:
			mediaFileRemoved(msg);
			break;
		case STC_REMOVE_PRIORITY_ACK:
			priorityRemoved(msg);
			break;
		case STC_REMOVE_THEME_ACK:
			themeRemoved(msg);
			break;
		case STC_EDIT_LIVE_TICKER_ELEMENT_ACK:
			liveTickerElementEdited(msg);
			break;
		case STC_EDIT_MEDIA_FILE_ACK:
			mediaFileEdited(msg);
			break;
		case STC_CONNECTION_ACK:
			connectionSuccessful(msg);
			break;
		case STC_LOGIN_DENIED:
			loginDenied(msg);
			break;
		case STC_QUEUE_MEDIA_FILE_ACK:
			mediaFileQueued(msg);
			break;
		case STC_DEQUEUE_MEDIAFILE_ACK:
			mediaFileDequeued(msg);
			break;
		case STC_SHOW_MEDIA_FILE_ACK:
			mediaFileShowing(msg);
			break;
		case STC_ENABLE_AUTO_MODE_ACK:
			autoModeEnabled(msg);
			break;
		case STC_DISABLE_AUTO_MODE_ACK:
			autoModeDisabled(msg);
			break;
		case STC_ENABLE_FULLSCREEN_ACK:
			fullscreenEnabled(msg);
			break;
		case STC_DISABLE_FULLSCREEN_ACK:
			fullscreenDisabled(msg);
			break;
		case STC_ENABLE_LIVE_TICKER_ACK:
			liveTickerEnabled(msg);
			break;
		case STC_DISABLE_LIVE_TICKER_ACK:
			liveTickerDisabled(msg);
			break;
		case STC_FULL_SYNC_START:
			fullSyncStart();
			break;
		case STC_FULL_SYNC_STOP:
			fullSyncStop();
			break;
		case STC_RESET_SHOW_COUNT_ACK:
			resetShowCount(msg);
			break;
		case STC_TIMELEFT_SYNC:
			timeleftSync(msg);
			break;
		case STC_SERVER_SHUTDOWN:
			serverShutdown(msg);
			break;
		case STC_HEARTBEAT_REQUEST:
			hearbeatRequest();
			break;
		case STC_INIT_PROPERTIES:
			initServerProperties(msg);
			break;
		case STC_PROPERTY_UPDATE_ACK:
			updatePropertyAck(msg);
			break;
		case STC_FORCE_RECONNECT:
			reconnectForced();
			break;
		case STC_ALL_FONTS:
			receivedServerFonts(msg);
			break;
		default:
			unkownMessageReceived(msg);
			break;
		}

		if(msgReceivedListener != null) {
			msgReceivedListener.received(msg);
		}

	}

	public void reconnectForced() {
		sendDisconnectRequest();
		if (this.autoReconnectTimer == null) {
			this.autoReconnectTimer = new Timer();
			this.autoReconnectTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					try {
						MessageProxyClient.this.client.connect();
					} catch (InterruptedException e) {
						log.log(Level.WARNING, "Error during connection setup", e);
					}
				}
			}, 2000, 5000);
		}
	}

	private void resetShowCount(Message msg) throws MediaDoesNotExsistException {
		UUID toReset = (UUID) msg.getData().get(0);
		this.mediaModel.resetShowCount(toReset);
	}

	public void sendAddAndShowCountdown(Countdown countdown) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_COUNTDOWN, countdown)).awaitUninterruptibly(5000);
		sendShowMediaFile(countdown.getId());
	}

	public void sendAddCountdown(String name, int minutesToShow) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_COUNTDOWN, name, minutesToShow));
	}

	public void sendAddImageFile(File file) throws IOException {
		log.fine("sending add image file, name: " + file.getName());
		sendMessageToServer(new Message(OpCode.CTS_ADD_IMAGE_FILE, file.getName(), file));
	}

	public void sendAddImageFiles(File[] files) throws IOException {
		for (int i = 0; i < files.length; i++) {
			sendAddImageFile(files[i]);
		}
	}

	public void sendAddMediaFiles(File[] selectedFiles) throws IOException {
		for (int i = 0; i < selectedFiles.length; i++) {
			if (!selectedFiles[i].isDirectory()) {
				if (MediaFileFilter.isImageFile(selectedFiles[i].getName())) {
					sendAddImageFile(selectedFiles[i]);
				} else if (MediaFileFilter.isVideoFile(selectedFiles[i].getName())) {
					sendAddVideoFile(selectedFiles[i]);
				}
			}

		}
	}

	public void sendAddPriority(Priority prioToAdd) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_PRIORITY, prioToAdd));
	}

	public void sendAddTheme(String name, byte[] bgImg) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_THEME, name, bgImg));
	}

	public void sendAddThemeSlide(String name, UUID theme, File fileToSend) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_THEMESLIDE, name, theme, fileToSend));
	}

	public void sendAddTickerElement(String text) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT, new TickerElement(text)));
	}

	public void sendAddVideoFile(File file) throws IOException {
		sendMessageToServer(new Message(OpCode.CTS_ADD_VIDEO_FILE, file.getName(), file));
	}

	public void sendAutoModeToggle(boolean selected) {
		if (selected) {
			sendMessageToServer(new Message(OpCode.CTS_ENABLE_AUTO_MODE));
		} else {
			sendMessageToServer(new Message(OpCode.CTS_DISABLE_AUTO_MODE));
		}
	}

	public void sendDequeueSelectedMedia(int row) {
		sendMessageToServer(new Message(OpCode.CTS_DEQUEUE_MEDIAFILE, new DequeueData(row,
				this.mediaModel.getCustomQueue().get(row))));
	}

	public void sendDequeueSelectedMedia(int[] selectedRowsCustomQueue) {
		Arrays.sort(selectedRowsCustomQueue);
		for (int i = selectedRowsCustomQueue.length - 1; i >= 0; i--) {
			sendDequeueSelectedMedia(selectedRowsCustomQueue[i]);
		}
	}

	public ChannelFuture sendDisconnectRequest() {
		return sendMessageToServer(new Message(OpCode.CTS_DISCONNECT, this.client.getLogin().getAlias()));
	}

	public void sendEditMediaFile(ClientMediaFile fileToEdit) {
		sendMessageToServer(new Message(OpCode.CTS_EDIT_MEDIA_FILE, fileToEdit));
	}

	public void sendEditTickerElement(TickerElement eltToEdit) {
		sendMessageToServer(new Message(OpCode.CTS_EDIT_LIVE_TICKER_ELEMENT, eltToEdit));
	}

	public void sendEnterFullscreen() {
		sendMessageToServer(new Message(OpCode.CTS_ENABLE_FULLSCREEN));
	}

	public void sendExitFullscreen() {
		sendMessageToServer(new Message(OpCode.CTS_DISABLE_FULLSCREEN));
	}

	public ChannelFuture sendLoginRequest(LoginData login) {
		return sendMessageToServer(new Message(OpCode.CTS_LOGIN_REQUEST, login));
	}

	private ChannelFuture sendMessageToServer(Message msgToSend) {
		log.fine("Sending message to server: " + msgToSend);
		return this.channelToServer.writeAndFlush(msgToSend);
	}

	public void sendPropertyUpdate(String key, String newValue) {
		log.fine("Sending property update for: " + key + ", to: " + newValue);
		sendMessageToServer(new Message(OpCode.CTS_PROPERTY_UPDATE, key, newValue));
	}

	public void sendQueueSelectedMedia(int selectedRow) {
		sendMessageToServer(new Message(OpCode.CTS_QUEUE_MEDIA_FILE, this.mediaModel.getValueAt(
				selectedRow).getId()));
	}

	public void sendQueueSelectedMedia(int[] selectedRows) {
		for (int i = 0; i < selectedRows.length; i++) {
			sendQueueSelectedMedia(selectedRows[i]);
		}
	}

	public void sendRemovePriority(UUID toRemove) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_PRIORITY, toRemove));
	}

	public void sendRemoveSelectedMedia(int row) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, this.mediaModel.getValueAt(row)
				.getId()));
	}

	public void sendRemoveSelectedMedia(int[] selectedRowsAllMedia) {
		Arrays.sort(selectedRowsAllMedia);
		for (int i = selectedRowsAllMedia.length - 1; i >= 0; i--) {
			sendRemoveSelectedMedia(selectedRowsAllMedia[i]);
		}
	}

	public void sendRemoveSelectedTickerElement(int row) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_LIVE_TICKER_ELEMENT, this.tickerModel
				.getValueAt(row).getId()));
	}

	public void sendRemoveSelectedTickerElements(int[] selectedRowsLiveTicker) {
		Arrays.sort(selectedRowsLiveTicker);
		for (int i = selectedRowsLiveTicker.length - 1; i >= 0; i--) {
			sendRemoveSelectedTickerElement(selectedRowsLiveTicker[i]);
		}
	}

	public void sendRemoveTheme(UUID toRemove) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_THEME, toRemove));
	}

	public void sendRequestFullSync() {
		sendMessageToServer(new Message(OpCode.CTS_REQUEST_FULL_SYNC));
	}

	public void sendRequestServerShutdown() {
		sendMessageToServer(new Message(OpCode.CTS_REQUEST_SERVER_SHUTDOWN));
	}

	public void sendResetShowCount(UUID mediaToReset) {
		sendMessageToServer(new Message(OpCode.CTS_RESET_SHOW_COUNT, mediaToReset));
	}

	public void sendShowMediaFile(int row) {
		sendMessageToServer(new Message(OpCode.CTS_SHOW_MEDIA_FILE, this.mediaModel.getValueAt(row)
				.getId()));
	}

	public void sendShowMediaFile(UUID id) {
		sendMessageToServer(new Message(OpCode.CTS_SHOW_MEDIA_FILE, id));
	}

	public void sendShowNextMediaFile() {
		sendMessageToServer(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE));
	}

	public void sendStartLiveTicker() {
		sendMessageToServer(new Message(OpCode.CTS_ENABLE_LIVE_TICKER));
	}

	public void sendStopLiveTicker() {
		sendMessageToServer(new Message(OpCode.CTS_DISABLE_LIVE_TICKER));
	}

	private void serverShutdown(Message msg) {
		this.client.serverShutdown();
		this.serverShutdownListener.serverShutdown();
	}

	public void setChannelToServer(Channel channelToServer) {
		this.channelToServer = channelToServer;
	}

	public void setServerPropertyUpdateListener(ServerPropertyUpdateListener serverPropertyUpdateListener) {
		this.serverPropertyUpdateListener = serverPropertyUpdateListener;
	}

	public void setServerShutdownListener(ServerShutdownListener serverShutdownListener) {
		this.serverShutdownListener = serverShutdownListener;
	}

	public void setTimeSyncListener(TimeSyncListener timeSyncListener) {
		this.timeSyncListener = timeSyncListener;
	}

	private void themeAdded(Message msg) {
		Theme toAdd = (Theme) msg.getData().get(0);
		this.prefs.themeAdded(toAdd);
	}

	private void themeRemoved(Message msg) {
		UUID toRemove = (UUID) msg.getData().get(0);
		this.prefs.themeRemoved(toRemove);
	}

	private void timeleftSync(Message msg) {
		long currentTimeLeftInSeconds = (Long) msg.getData().get(0);
		this.timeSyncListener.timesync(currentTimeLeftInSeconds);

	}

	private void unkownMessageReceived(Message msg) throws UnkownMessageException {
		throw new UnkownMessageException("A unkown message was received: " + msg.toString());
	}

	private void updatePropertyAck(Message msg) {
		String key = (String) msg.getData().get(0);
		String newValue = (String) msg.getData().get(1);
		this.prefs.serverPropertyUpdated(key, newValue);
		if (this.serverPropertyUpdateListener != null) {
			this.serverPropertyUpdateListener.propertyUpdated();
		}
	}

}
