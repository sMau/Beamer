package de.netprojectev.client.networking;

import java.util.UUID;

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
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;

public class ClientMessageProxy {
	
	private static final Logger log = LoggerBuilder.createLogger(ClientMessageProxy.class);
	
	private final Client client;
	
	private final MediaModelClient mediaModel;
	private final TickerModelClient tickerModel;
	private final PreferencesModelClient prefs;
	
	private Channel channelToServer;

	public ClientMessageProxy(Client client) {
		mediaModel = new MediaModelClient(this);
		tickerModel = new TickerModelClient(this);
		prefs = new PreferencesModelClient(this);
		this.client = client;
	}
	
	
	public ChannelFuture sendMessageToServer(Message msgToSend) {
		log.debug("Sending message to server: " + msgToSend);
		return channelToServer.write(msgToSend);
	}


	public void receiveMessage(Message msg) throws UnkownMessageException, MediaDoesNotExsistException {
		log.debug("Receiving message: " + msg.toString());
		switch (msg.getOpCode()) {
		case ADD_MEDIA_FILE_ACK:
			mediaFileAdded(msg);
			break;
		case ADD_LIVE_TICKER_ELEMENT_ACK:
			liveTickerElementAdded(msg);
			break;
		case ADD_PRIORITY_ACK:
			priorityAdded(msg);
			break;
		case ADD_THEME_ACK:
			themeAdded(msg);
			break;
		case REMOVE_LIVE_TICKER_ELEMENT_ACK:
			liveTickerElementRemoved(msg);
			break;
		case REMOVE_MEDIA_FILE_ACK:
			mediaFileRemoved(msg);
			break;
		case REMOVE_PRIORITY_ACK:
			priorityRemoved(msg);
			break;
		case REMOVE_THEME_ACK:
			themeRemoved(msg);
			break;
		case EDIT_LIVE_TICKER_ELEMENT_ACK:
			liveTickerElementEdited(msg);
			break;
		case EDIT_MEDIA_FILE_ACK:
			mediaFileEdited(msg);
			break;
		case CONNECTION_ACK:
			connectionSuccessful(msg);
			break;
		case LOGIN_DENIED:
			loginDenied(msg);
			break;
		case QUEUE_MEDIA_FILE_ACK:
			mediaFileQueued(msg);
			break;
		case SHOW_MEDIA_FILE_ACK:
			mediaFileShowing(msg);
			break;
		case SHOW_NEXT_MEDIA_FILE_ACK:
			showingNextMediaFile(msg);
			break;
		case TOGGLE_AUTO_MODE_ACK:
			autoModeToggled(msg);
			break;
		case TOGGLE_FULLSCREEN_ACK:
			fullscreenToggled(msg);
			break;
		case TOGGLE_LIVE_TICKER_START_ACK:
			liveTickerStartToggled(msg);
			break;
		default:
			unkownMessageReceived(msg);
			break;
		}
		
		
	}

	private void showingNextMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID fileShowing = (UUID) msg.getData();
		showMediaFile(fileShowing);
	}


	private void autoModeToggled(Message msg) {
		prefs.toggleAutomode();
	}


	private void fullscreenToggled(Message msg) {
		prefs.toggleFullscreen();
	}


	private void liveTickerStartToggled(Message msg) {
		prefs.toggleLiveTickerRunning();
	}


	private void mediaFileShowing(Message msg) throws MediaDoesNotExsistException {
		UUID fileShowing = (UUID) msg.getData();
		showMediaFile(fileShowing);		
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
	
	private void showMediaFile(UUID id) throws MediaDoesNotExsistException {
		mediaModel.getMediaFileById(id).setCurrent(true).increaseShowCount();
		
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
	
}
