package de.netprojectev.client.networking;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import de.netprojectev.client.Client;
import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.client.gui.main.MainClientGUIWindow.ServerShutdownListener;
import de.netprojectev.client.gui.main.MainClientGUIWindow.TimeSyncListener;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.MediaFileFilter;
import de.netprojectev.networking.DequeueData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.networking.VideoFileData;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientMessageProxy {
	
	private static final Logger log = LoggerBuilder.createLogger(ClientMessageProxy.class);

	private final Client client;

	private final MediaModelClient mediaModel;
	private final TickerModelClient tickerModel;
	private final PreferencesModelClient prefs;

	private TimeSyncListener timeSyncListener;
	private ServerShutdownListener serverShutdownListener;
	
	private boolean fullsync;

	private Channel channelToServer;

	public ClientMessageProxy(Client client) {
		mediaModel = new MediaModelClient(this);
		tickerModel = new TickerModelClient(this);
		prefs = new PreferencesModelClient(this);
		this.client = client;
		this.fullsync = false;
	}

	// TODO mark this as private at the moment only public for testing
	public ChannelFuture sendMessageToServer(Message msgToSend) {
		log.debug("Sending message to server: " + msgToSend);
		return channelToServer.write(msgToSend);
	}

	public void sendDisconnectRequest() {
		client.disconnect();
	}

	public void sendRequestServerShutdown() {
		sendMessageToServer(new Message(OpCode.CTS_REQUEST_SERVER_SHUTDOWN));
	}
	
	public void sendRequestFullSync() {
		sendMessageToServer(new Message(OpCode.CTS_REQUEST_FULL_SYNC));
	}

	public void sendShowMediaFile(int row) {
		sendMessageToServer(new Message(OpCode.CTS_SHOW_MEDIA_FILE, mediaModel.getValueAt(row)
				.getId()));
	}

	public void sendShowNextMediaFile() {
		sendMessageToServer(new Message(OpCode.CTS_SHOW_NEXT_MEDIA_FILE));
	}

	public void sendEditMediaFile(ClientMediaFile fileToEdit) {
		fileToEdit.setPreview(null);
		sendMessageToServer(new Message(OpCode.CTS_EDIT_MEDIA_FILE, fileToEdit));
	}

	public void sendEditTickerElement(ClientTickerElement eltToEdit) {
		sendMessageToServer(new Message(OpCode.CTS_EDIT_LIVE_TICKER_ELEMENT, eltToEdit));
	}

	public void sendAddTheme(Theme themeToAdd) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_THEME, themeToAdd));
	}

	public void sendAddPriority(Priority prioToAdd) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_PRIORITY, prioToAdd));
	}

	public void sendRemoveTheme(UUID toRemove) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_THEME, toRemove));
	}

	public void sendRemovePriority(UUID toRemove) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_PRIORITY, toRemove));
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

	public void sendAddVideoFile(File file) throws IOException {

		VideoFile toSend = new VideoFile(file.getName(), file);
		FileInputStream fileInputStream = new FileInputStream(toSend.getVideoFile());
		BufferedInputStream buf = new BufferedInputStream(fileInputStream);
		
		int bestChunckSize = 1024*1024*8;
		int messageNumber = 0;
		long lengthInBytes = toSend.getVideoFile().length();
		long overallBytesRead = 0L;
		
		int messagesToSend = ((int) lengthInBytes / bestChunckSize ) + 1;
		
		log.debug("Starting video file transfer to server, with file length: " + lengthInBytes + " Msgs To Send: " + messagesToSend);
		
		sendMessageToServer(new Message(OpCode.CTS_ADD_VIDEO_FILE_START, toSend.getId(), messagesToSend));

		while(overallBytesRead < lengthInBytes) {
			int chunckSize = (int) Math.min(bestChunckSize, (lengthInBytes - overallBytesRead));
			byte[] bytesRead = new byte[chunckSize];

			buf.read(bytesRead, 0, chunckSize);

			log.debug("Sending video message data to server, Num: " + messageNumber);
			
			sendMessageToServer(new Message(OpCode.CTS_ADD_VIDEO_FILE_DATA, new VideoFileData(bytesRead, toSend.getId(), messageNumber)));
			
			overallBytesRead += chunckSize;
			messageNumber++;
		}
		
		buf.close();
		fileInputStream.close();
		
		sendMessageToServer(new Message(OpCode.CTS_ADD_VIDEO_FILE_FINISH, toSend));
		
	}

	public void sendAddImageFile(File file) throws IOException {
		String name = file.getName();
		BufferedImage image = ImageIO.read(file);
		ImageIcon icon = new ImageIcon(image);
		sendMessageToServer(new Message(OpCode.CTS_ADD_MEDIA_FILE, new ImageFile(name, icon,
				prefs.getDefaultPriority())));
	}

	public void sendAddImageFiles(File[] files) throws IOException {
		for (int i = 0; i < files.length; i++) {
			sendAddImageFile(files[i]);
		}
	}

	public void sendAddTickerElement(String text) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT,
				new ServerTickerElement(text)));
	}

	public void sendAddThemeSlide(Themeslide themeslide) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_MEDIA_FILE, themeslide));
	}

	public void sendRemoveSelectedMedia(int[] selectedRowsAllMedia) {
		Arrays.sort(selectedRowsAllMedia);
		for (int i = selectedRowsAllMedia.length - 1; i >= 0; i--) {
			sendRemoveSelectedMedia(selectedRowsAllMedia[i]);
		}
	}

	public void sendRemoveSelectedMedia(int row) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, mediaModel.getValueAt(row)
				.getId()));
	}

	public void sendDequeueSelectedMedia(int[] selectedRowsCustomQueue) {
		Arrays.sort(selectedRowsCustomQueue);
		for (int i = selectedRowsCustomQueue.length - 1; i >= 0; i--) {
			sendDequeueSelectedMedia(selectedRowsCustomQueue[i]);
		}
	}

	public void sendDequeueSelectedMedia(int row) {
		sendMessageToServer(new Message(OpCode.CTS_DEQUEUE_MEDIAFILE, new DequeueData(row,
				mediaModel.getCustomQueue().get(row))));
	}

	public void sendQueueSelectedMedia(int[] selectedRows) {
		for (int i = 0; i < selectedRows.length; i++) {
			sendQueueSelectedMedia(selectedRows[i]);
		}
	}

	public void sendQueueSelectedMedia(int selectedRow) {
		sendMessageToServer(new Message(OpCode.CTS_QUEUE_MEDIA_FILE, mediaModel.getValueAt(
				selectedRow).getId()));
	}

	public void sendRemoveSelectedTickerElements(int[] selectedRowsLiveTicker) {
		Arrays.sort(selectedRowsLiveTicker);
		for (int i = selectedRowsLiveTicker.length - 1; i >= 0; i--) {
			sendRemoveSelectedTickerElement(selectedRowsLiveTicker[i]);
		}
	}

	public void sendRemoveSelectedTickerElement(int row) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_LIVE_TICKER_ELEMENT, tickerModel
				.getValueAt(row).getId()));
	}

	public void sendResetShowCount(UUID medieToReset) {
		sendMessageToServer(new Message(OpCode.CTS_RESET_SHOW_COUNT, medieToReset));
	}

	public void sendAutoModeToggle(boolean selected) {
		if (selected) {
			sendMessageToServer(new Message(OpCode.CTS_ENABLE_AUTO_MODE));
		} else {
			sendMessageToServer(new Message(OpCode.CTS_DISABLE_AUTO_MODE));
		}
	}

	public void sendStartLiveTicker() {
		sendMessageToServer(new Message(OpCode.CTS_ENABLE_LIVE_TICKER));
	}

	public void sendStopLiveTicker() {
		sendMessageToServer(new Message(OpCode.CTS_DISABLE_LIVE_TICKER));
	}

	public void sendEnterFullscreen() {
		sendMessageToServer(new Message(OpCode.CTS_ENABLE_FULLSCREEN));
	}

	public void sendExitFullscreen() {
		sendMessageToServer(new Message(OpCode.CTS_DISABLE_FULLSCREEN));
	}

	public void sendAddCountdown(Countdown countdown) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_MEDIA_FILE, countdown));
	}

	public void receiveMessage(Message msg) throws UnkownMessageException,
			MediaDoesNotExsistException, OutOfSyncException {
		log.debug("Receiving message: " + msg.toString());
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
		default:
			unkownMessageReceived(msg);
			break;
		}

	}

	private void hearbeatRequest() {
		sendMessageToServer(new Message(OpCode.CTS_HEARTBEAT_ACK));
	}

	private void serverShutdown(Message msg) {
		client.serverShutdown();
		serverShutdownListener.serverShutdown();
	}

	private void timeleftSync(Message msg) {
		long currentTimeLeftInSeconds = (Long) msg.getData()[0];
		timeSyncListener.timesync(currentTimeLeftInSeconds);
		
	}

	private void resetShowCount(Message msg) throws MediaDoesNotExsistException {
		UUID toReset = (UUID) msg.getData()[0];
		mediaModel.resetShowCount(toReset);
	}

	private void liveTickerDisabled(Message msg) {
		prefs.disableLiveTicker();
	}

	private void liveTickerEnabled(Message msg) {
		prefs.enableLiveTicker();
	}

	private void fullscreenDisabled(Message msg) {
		prefs.disableFullscreen();
	}

	private void fullscreenEnabled(Message msg) {
		prefs.enableFullscreen();
	}

	private void autoModeDisabled(Message msg) {
		prefs.disableAutomode();
	}

	private void autoModeEnabled(Message msg) {
		prefs.enableAutomode(fullsync);
	}

	private void fullSyncStop() {
		// TODO add gui handling
		fullsync = false;

	}

	private void fullSyncStart() {
		// TODO add gui handling
		fullsync = true;
	}

	private void mediaFileDequeued(Message msg) throws MediaDoesNotExsistException,
			OutOfSyncException {
		DequeueData toDequeue = (DequeueData) msg.getData()[0];
		mediaModel.dequeueMediaFile(toDequeue.getRow(), toDequeue.getId());

	}

	private void mediaFileShowing(Message msg) throws MediaDoesNotExsistException {
		UUID fileShowing = (UUID) msg.getData()[0];
		mediaModel.setAsCurrent(fileShowing);
	}

	private void mediaFileQueued(Message msg) throws MediaDoesNotExsistException {
		UUID toQueue = (UUID) msg.getData()[0];
		mediaModel.queueMediaFile(toQueue);
	}

	private void loginDenied(Message msg) {
		client.loginFailed((String) msg.getData()[0]);
	}

	private void connectionSuccessful(Message msg) {
		client.loginSuccess();
		sendRequestFullSync();
	}

	private void mediaFileEdited(Message msg) {
		ClientMediaFile media = (ClientMediaFile) msg.getData()[0];
		try {
			mediaModel.replaceMediaFile(media);
		} catch (MediaDoesNotExsistException e) {
			// TODO reload data from server to get in sync again
			log.error("Error replacing mediaFile", e);
		}
	}

	private void liveTickerElementEdited(Message msg) {
		ClientTickerElement e = (ClientTickerElement) msg.getData()[0];
		try {
			tickerModel.replaceTickerElement(e);
		} catch (MediaDoesNotExsistException e1) {
			// TODO reload data from server to get in sync again
			log.error("Error replacing tickerElement", e);
		}
	}

	private void themeRemoved(Message msg) {
		UUID toRemove = (UUID) msg.getData()[0];
		prefs.themeRemoved(toRemove);
	}

	private void priorityRemoved(Message msg) {
		UUID toRemove = (UUID) msg.getData()[0];
		prefs.prioRemoved(toRemove);
	}

	private void themeAdded(Message msg) {
		Theme toAdd = (Theme) msg.getData()[0];
		prefs.themeAdded(toAdd);
	}

	private void priorityAdded(Message msg) {
		Priority toAdd = (Priority) msg.getData()[0];
		prefs.prioAdded(toAdd);
	}

	private void mediaFileRemoved(Message msg) throws MediaDoesNotExsistException {

		UUID toRemove = (UUID) msg.getData()[0];
		mediaModel.removeMediaFile(toRemove);
	}

	private void liveTickerElementRemoved(Message msg) throws MediaDoesNotExsistException {
		UUID toRemove = (UUID) msg.getData()[0];
		tickerModel.removeTickerElement(toRemove);
	}

	private void liveTickerElementAdded(Message msg) {
		ClientTickerElement toAdd = (ClientTickerElement) msg.getData()[0];
		tickerModel.addTickerElement(toAdd);
	}

	private void mediaFileAdded(Message msg) {
		ClientMediaFile toAdd = (ClientMediaFile) msg.getData()[0];
		mediaModel.addMediaFile(toAdd);
	}

	private void unkownMessageReceived(Message msg) throws UnkownMessageException {
		throw new UnkownMessageException("A unkown message was received: " + msg.toString());
	}

	public Channel getChannelToServer() {
		return channelToServer;
	}

	public void setChannelToServer(Channel channelToServer) {
		this.channelToServer = channelToServer;
	}

	public MediaModelClient getMediaModel() {
		return mediaModel;
	}

	public TickerModelClient getTickerModel() {
		return tickerModel;
	}

	public PreferencesModelClient getPrefs() {
		return prefs;
	}

	public void setTimeSyncListener(TimeSyncListener timeSyncListener) {
		this.timeSyncListener = timeSyncListener;
	}

	public void setServerShutdownListener(ServerShutdownListener serverShutdownListener) {
		this.serverShutdownListener = serverShutdownListener;
	}

}
