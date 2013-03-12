package de.netprojectev.server.networking;

import java.util.HashMap;
import java.util.Timer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.Channel;

import de.netprojectev.misc.Misc;
import de.netprojectev.networking.Message;
import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.datastructures.media.ServerMediaFile;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.model.MediaDoesNotExsistException;
import de.netprojectev.server.model.MediaListsEmptyException;
import de.netprojectev.server.model.MediaModelServer;
import de.netprojectev.server.model.TickerModelServer;

public class MessageProxyServer {
	
	private static final Logger LOG = Misc.getLoggerAll(MessageProxyServer.class.getName());
	
	private final HashMap<String, Channel> clients;
	private final MediaModelServer mediaModel;
	private final TickerModelServer tickerModel;
	private final DisplayFrame frame;
	private boolean automodeEnabled;
	private boolean shufflemodeEnabled;
	private ServerMediaFile currentFile;
	private Timer autoModusTimer;
	
	public MessageProxyServer() {
		this.clients = new HashMap<>();
		this.mediaModel = new MediaModelServer(this);
		this.tickerModel = new TickerModelServer(this);
		this.frame = new DisplayFrame(this); 
	}
	
	public void receiveMessage(Message msg) throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		switch (msg.getOpCode()) {
		case ADD_MEDIA_FILE:
			addMediaFile(msg);
			break;
		case REMOVE_MEDIA_FILE:
			removeMediaFile(msg);
			break;
		case SHOW_MEDIA_FILE:
			showMediaFile(msg);
			break;
		case SHOW_NEXT_MEDIA_FILE:
			showNextMediaFile();
			break;
		case QUEUE_MEDIA_FILE:
			queueMediaFile(msg);
			break;
		case ADD_LIVE_TICKER_ELEMENT:
			addLiveTickerElement(msg);
			break;
		case REMOVE_LIVE_TICKER_ELEMENT:
			removeLiveTickerElement(msg);
			break;
		default:
			unkownMessageReceived(msg);
			break;
		}
	}
	
	public void sendMessage(String alias, Message msg) {
		// TODO Auto-generated method stub
	}
	
	public void broadcastMessage(Message msg) {
		// TODO Auto-generated method stub
	}
	
	public void clientConnected(String alias, Channel chan) {
		// TODO Auto-generated method stub
	}
	
	public void clientDisconnected(String alias) {
		// TODO Auto-generated method stub
	}
	
	private void removeLiveTickerElement(Message msg) throws MediaDoesNotExsistException {
		UUID eltToRemove = (UUID) msg.getData();
		tickerModel.removeTickerElement(eltToRemove);
		LOG.log(Level.INFO, "Removing ticker element " + eltToRemove);
		//TODO implement the display part
		
	}

	private void addLiveTickerElement(Message msg) {
		TickerElement eltToAdd = (TickerElement) msg.getData();
		tickerModel.addTickerElement(eltToAdd);
		LOG.log(Level.INFO, "Adding ticker element " + eltToAdd);
		//TODO implement the display part
		
	}

	private void queueMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toQueue = (UUID) msg.getData();
		mediaModel.queue(toQueue);
		LOG.log(Level.INFO, "Queueing media file " + toQueue);
	}

	private void showNextMediaFile() throws MediaDoesNotExsistException, MediaListsEmptyException {
		ServerMediaFile fileToShow = mediaModel.getNext();
		currentFile = fileToShow;
		LOG.log(Level.INFO, "Showing next media file " + fileToShow);
		//TODO implement the display part
		
		
	}

	private void showMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toShow = (UUID) msg.getData();
		ServerMediaFile fileToShow = mediaModel.getMediaFileById(toShow);
		currentFile = fileToShow;
		LOG.log(Level.INFO, "Showing media file " + fileToShow);
		//TODO implement the display part

	}

	private void unkownMessageReceived(Message msg) throws UnkownMessageException {
		throw new UnkownMessageException("A unkown message was received. Debug information: " + msg.toString());	
	}

	private void removeMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toRemove = (UUID) msg.getData();
		mediaModel.removeMediaFile(toRemove);
		LOG.log(Level.INFO, "Removing media file " + toRemove);
	}

	private void addMediaFile(Message msg) {
		ServerMediaFile fileToAdd = (ServerMediaFile) msg.getData();
		mediaModel.addMediaFile(fileToAdd);
		LOG.log(Level.INFO, "Adding media file " + fileToAdd);
	}

	private void enableAutoModus() {
		// TODO Auto-generated method stub
	}
	
	private void startLiveTicker() {
		// TODO Auto-generated method stub
	}
	private void stopLiveTicker() {
		// TODO Auto-generated method stub
	}

	
	public boolean isAutomodeEnabled() {
		return automodeEnabled;
	}

	public void setAutomodeEnabled(boolean automodeEnabled) {
		this.automodeEnabled = automodeEnabled;
	}

	public boolean isShufflemodeEnabled() {
		return shufflemodeEnabled;
	}

	public void setShufflemodeEnabled(boolean shufflemodeEnabled) {
		this.shufflemodeEnabled = shufflemodeEnabled;
	}

	public Timer getAutoModusTimer() {
		return autoModusTimer;
	}

	public void setAutoModusTimer(Timer autoModusTimer) {
		this.autoModusTimer = autoModusTimer;
	}

	public HashMap<String, Channel> getClients() {
		return clients;
	}

	public MediaModelServer getMediaModel() {
		return mediaModel;
	}

	public TickerModelServer getTickerModel() {
		return tickerModel;
	}

	public DisplayFrame getFrame() {
		return frame;
	}

	public ServerMediaFile getCurrentFile() {
		return currentFile;
	}

}
