package de.netprojectev.client.networking;

import java.awt.image.BufferedImage;
import java.io.File;
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
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.DequeueData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.datastructures.Themeslide;

public class ClientMessageProxy {
	
	private static final Logger log = LoggerBuilder.createLogger(ClientMessageProxy.class);
	
	private final Client client;
	
	private final MediaModelClient mediaModel;
	private final TickerModelClient tickerModel;
	private final PreferencesModelClient prefs;
	
	private boolean fullsync;
	
	private Channel channelToServer;
	
	public ClientMessageProxy(Client client) {
		mediaModel = new MediaModelClient(this);
		tickerModel = new TickerModelClient(this);
		prefs = new PreferencesModelClient(this);
		this.client = client;
		this.fullsync = false;
	}
	
	//TODO mark this as private at the moment only public for testing
	public ChannelFuture sendMessageToServer(Message msgToSend) {
		log.debug("Sending message to server: " + msgToSend);
		return channelToServer.write(msgToSend);
	}
	
	public void sendDisconnectRequest() {
		client.disconnect();
	}
	
	public void sendRequestFullSync() {
		sendMessageToServer(new Message(OpCode.CTS_REQUEST_FULL_SYNC));
	}
	
	public void sendShowMediaFile(int row) {
		sendMessageToServer(new Message(OpCode.CTS_SHOW_MEDIA_FILE, mediaModel.getValueAt(row).getId()));
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
	
	public void sendAddImageFile(File file) throws IOException {
		String name = file.getName();
		BufferedImage image = ImageIO.read(file);
		ImageIcon icon = new ImageIcon(image);
		sendMessageToServer(new Message(OpCode.CTS_ADD_MEDIA_FILE, new ImageFile(name, icon, prefs.getDefaultPriority())));
	}
	
	public void sendAddImageFiles(File[] files) throws IOException {
		for(int i = 0; i < files.length; i++) {
			sendAddImageFile(files[i]);
		}
	}
	
	public void sendAddTickerElement(String text) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_LIVE_TICKER_ELEMENT, new ServerTickerElement(text)));
	}
	
	public void sendAddThemeSlide(Themeslide themeslide) {
		sendMessageToServer(new Message(OpCode.CTS_ADD_MEDIA_FILE, themeslide));
	}
	
	public void sendRemoveSelectedMedia(int[] selectedRowsAllMedia) {
		Arrays.sort(selectedRowsAllMedia);
		for(int i = selectedRowsAllMedia.length - 1; i >= 0; i--) {
			sendRemoveSelectedMedia(selectedRowsAllMedia[i]);
		}
	}
	
	public void sendRemoveSelectedMedia(int row) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_MEDIA_FILE, mediaModel.getValueAt(row).getId()));
	}

	public void sendDequeueSelectedMedia(int[] selectedRowsCustomQueue) {
		Arrays.sort(selectedRowsCustomQueue);
		for(int i = selectedRowsCustomQueue.length - 1; i >= 0; i--) {
			sendDequeueSelectedMedia(selectedRowsCustomQueue[i]);
		}
	}
	
	public void sendDequeueSelectedMedia(int row) {
		sendMessageToServer(new Message(OpCode.CTS_DEQUEUE_MEDIAFILE, new DequeueData(row, mediaModel.getCustomQueue().get(row))));
	}
	
	public void sendQueueSelectedMedia(int[] selectedRows) {
		for(int i = 0; i < selectedRows.length; i++) {
			sendQueueSelectedMedia(selectedRows[i]);
		}
	}
	
	public void sendQueueSelectedMedia(int selectedRow) {
		sendMessageToServer(new Message(OpCode.CTS_QUEUE_MEDIA_FILE, mediaModel.getValueAt(selectedRow).getId()));
	}
	
	public void sendRemoveSelectedTickerElements(int[] selectedRowsLiveTicker) {		
		Arrays.sort(selectedRowsLiveTicker);
		for(int i = selectedRowsLiveTicker.length - 1; i >= 0; i--) {
			sendRemoveSelectedTickerElement(selectedRowsLiveTicker[i]);
		}
	}
	
	public void sendRemoveSelectedTickerElement(int row) {
		sendMessageToServer(new Message(OpCode.CTS_REMOVE_LIVE_TICKER_ELEMENT, tickerModel.getValueAt(row).getId()));
	}
	
	public void sendResetShowCount(UUID medieToReset) {
		sendMessageToServer(new Message(OpCode.CTS_RESET_SHOW_COUNT, medieToReset));
	}

	public void receiveMessage(Message msg) throws UnkownMessageException, MediaDoesNotExsistException, OutOfSyncException {
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
		default:
			unkownMessageReceived(msg);
			break;
		}
		
		
	}

	private void resetShowCount(Message msg) throws MediaDoesNotExsistException {
		UUID toReset = (UUID) msg.getData();
		mediaModel.resetShowCount(toReset);
	}

	private void liveTickerDisabled(Message msg) {
		// TODO Auto-generated method stub
		
	}

	private void liveTickerEnabled(Message msg) {
		// TODO Auto-generated method stub
		
	}

	private void fullscreenDisabled(Message msg) {
		// TODO Auto-generated method stub
		
	}

	private void fullscreenEnabled(Message msg) {
		// TODO Auto-generated method stub
		
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

	private void mediaFileDequeued(Message msg) throws MediaDoesNotExsistException, OutOfSyncException {
		DequeueData toDequeue = (DequeueData) msg.getData();
		mediaModel.dequeueMediaFile(toDequeue.getRow(), toDequeue.getId());
		
	}


	private void mediaFileShowing(Message msg) throws MediaDoesNotExsistException {
		UUID fileShowing = (UUID) msg.getData();
		mediaModel.setAsCurrent(fileShowing);
	}


	private void mediaFileQueued(Message msg) throws MediaDoesNotExsistException {
		UUID toQueue = (UUID) msg.getData();
		mediaModel.queueMediaFile(toQueue);
	}


	private void loginDenied(Message msg) {
		client.loginDenied((String) msg.getData());
	}


	private void connectionSuccessful(Message msg) {
		client.loginSuccess();
		sendRequestFullSync();
	}

	private void mediaFileEdited(Message msg) {
		ClientMediaFile media = (ClientMediaFile) msg.getData();
		try {
			mediaModel.replaceMediaFile(media);
		} catch (MediaDoesNotExsistException e) {
			// TODO reload data from server to get in sync again
			log.error("Error replacing mediaFile", e);
		}
	}

	private void liveTickerElementEdited(Message msg) {
		ClientTickerElement e = (ClientTickerElement) msg.getData();
		try {
			tickerModel.replaceTickerElement(e);
		} catch (MediaDoesNotExsistException e1) {
			// TODO reload data from server to get in sync again
			log.error("Error replacing tickerElement", e);
		}
	}

	private void themeRemoved(Message msg) {
		UUID toRemove = (UUID) msg.getData();
		prefs.themeRemoved(toRemove);
	}

	private void priorityRemoved(Message msg) {
		UUID toRemove = (UUID) msg.getData();
		prefs.prioRemoved(toRemove);
	}
	
	private void themeAdded(Message msg) {
		Theme toAdd = (Theme) msg.getData();
		prefs.themeAdded(toAdd);
	}


	private void priorityAdded(Message msg) {
		Priority toAdd = (Priority) msg.getData();
		prefs.prioAdded(toAdd);
	}

	private void mediaFileRemoved(Message msg) throws MediaDoesNotExsistException {

		UUID toRemove = (UUID) msg.getData();
		mediaModel.removeMediaFile(toRemove);
	}


	private void liveTickerElementRemoved(Message msg) throws MediaDoesNotExsistException {
		UUID toRemove = (UUID) msg.getData();
		tickerModel.removeTickerElement(toRemove);
	}

	private void liveTickerElementAdded(Message msg) {
		ClientTickerElement toAdd = (ClientTickerElement) msg.getData();
		tickerModel.addTickerElement(toAdd);
	}


	private void mediaFileAdded(Message msg) {
		ClientMediaFile toAdd = (ClientMediaFile) msg.getData();
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

	public void sendAutoModeToggle(boolean selected) {
		if(selected) {
			sendMessageToServer(new Message(OpCode.CTS_ENABLE_AUTO_MODE));
		} else {
			sendMessageToServer(new Message(OpCode.CTS_DISABLE_AUTO_MODE));
		}
	}

	
}
