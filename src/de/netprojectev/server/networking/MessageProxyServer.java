package de.netprojectev.server.networking;

import java.util.Timer;
import java.util.UUID;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.model.MediaModelServer;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.server.model.TickerModelServer;

public class MessageProxyServer {
	
	private static final Logger log = LoggerBuilder.createLogger(MessageProxyServer.class);
	
	private final DefaultChannelGroup allClients;
	private final MediaModelServer mediaModel;
	private final TickerModelServer tickerModel;
	private final PreferencesModelServer prefsModel;
	private final DisplayFrame frame;
	private boolean automodeEnabled;
	private boolean shufflemodeEnabled;
	private ServerMediaFile currentFile;
	private Timer autoModusTimer;
	
	public MessageProxyServer() {
		this.allClients = new DefaultChannelGroup("beamer-clients");
		this.mediaModel = new MediaModelServer(this);
		this.tickerModel = new TickerModelServer(this);
		this.prefsModel = new PreferencesModelServer(this);
		this.frame = new DisplayFrame(this); 
	}
	
	public void receiveMessage(Message msg) throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException {
		switch (msg.getOpCode()) {
		case CTS_ADD_MEDIA_FILE:
			addMediaFile(msg);
			break;
		case CTS_REMOVE_MEDIA_FILE:
			removeMediaFile(msg);
			break;
		case CTS_SHOW_MEDIA_FILE:
			showMediaFile(msg);
			break;
		case CTS_SHOW_NEXT_MEDIA_FILE:
			showNextMediaFile();
			break;
		case CTS_QUEUE_MEDIA_FILE:
			queueMediaFile(msg);
			break;
		case CTS_ADD_LIVE_TICKER_ELEMENT:
			addLiveTickerElement(msg);
			break;
		case CTS_REMOVE_LIVE_TICKER_ELEMENT:
			removeLiveTickerElement(msg);
			break;
		case CTS_ADD_THEME:
			addTheme(msg);
			break;
		case CTS_ADD_PRIORITY:
			addPriority(msg);
			break;
		case CTS_REMOVE_THEME:
			removeTheme(msg);
			break;
		case CTS_REMOVE_PRIORITY:
			removePriority(msg);
			break;
		case CTS_TOGGLE_LIVE_TICKER_START:
			toggleLiveTickerStart();
			break;
		case CTS_TOGGLE_AUTO_MODE:
			toggleAutoMode();
			break;
		case CTS_TOGGLE_FULLSCREEN:
			toggleFullScreen();
			break;
		default:
			unkownMessageReceived(msg);
			break;
		}
	}
	
	public ChannelGroupFuture broadcastMessage(Message msg) {
		log.debug("Broadcasting message: " + msg);
		return allClients.write(msg);
	}
	
	public void clientConnected(Channel chan) {
		log.info("Client connected.");
		allClients.add(chan);
	}
	
	public void clientDisconnected(Channel chan) {
		log.info("Client disconnected.");
		allClients.remove(chan);
	}
	
	private void removePriority(Message msg) {
		UUID prioToRemove = (UUID) msg.getData();
		prefsModel.removePriority(prioToRemove);
	}

	private void removeTheme(Message msg) {
		UUID themeToRemove = (UUID) msg.getData();
		prefsModel.removeTheme(themeToRemove);
	}

	private void addPriority(Message msg) {
		Priority prioToAdd = (Priority) msg.getData();
		prefsModel.addPriority(prioToAdd);
	}

	private void addTheme(Message msg) {
		Theme themeToAdd = (Theme) msg.getData();
		prefsModel.addTheme(themeToAdd);
	}
	
	private void removeLiveTickerElement(Message msg) throws MediaDoesNotExsistException {
		UUID eltToRemove = (UUID) msg.getData();
		tickerModel.removeTickerElement(eltToRemove);
		
		broadcastMessage(new Message(OpCode.STC_REMOVE_LIVE_TICKER_ELEMENT_ACK, eltToRemove));
	}

	private void addLiveTickerElement(Message msg) {
		ServerTickerElement eltToAdd = (ServerTickerElement) msg.getData();
		tickerModel.addTickerElement(eltToAdd);
		//TODO implement the display part
		broadcastMessage(new Message(OpCode.STC_ADD_LIVE_TICKER_ELEMENT_ACK, new ClientTickerElement(eltToAdd)));
	}

	private void queueMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toQueue = (UUID) msg.getData();
		mediaModel.queue(toQueue);		
		
		broadcastMessage(new Message(OpCode.STC_QUEUE_MEDIA_FILE_ACK, toQueue));
	}

	private void showNextMediaFile() throws MediaDoesNotExsistException, MediaListsEmptyException {
		ServerMediaFile fileToShow = mediaModel.getNext();
		currentFile = fileToShow;
		//TODO implement the display part
		
	}

	private void showMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toShow = (UUID) msg.getData();
		ServerMediaFile fileToShow = mediaModel.getMediaFileById(toShow);
		currentFile = fileToShow;
		
		//TODO implement the display part

	}

	private void unkownMessageReceived(Message msg) throws UnkownMessageException {
		throw new UnkownMessageException("A unkown message was received. Debug information: " + msg.toString());	
	}

	private void removeMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toRemove = (UUID) msg.getData();
		mediaModel.removeMediaFile(toRemove);
		
		broadcastMessage(new Message(OpCode.STC_REMOVE_MEDIA_FILE_ACK, toRemove));
	}

	private void addMediaFile(Message msg) {
		ServerMediaFile fileToAdd = (ServerMediaFile) msg.getData();
		mediaModel.addMediaFile(fileToAdd);
		
		broadcastMessage(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, new ClientMediaFile(fileToAdd)));
	}
	
	private void toggleFullScreen() {
		// TODO Auto-generated method stub
		
	}

	private void toggleAutoMode() {
		// TODO Auto-generated method stub
		
	}

	private void toggleLiveTickerStart() {
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

	public DefaultChannelGroup getAllClients() {
		return allClients;
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

	public PreferencesModelServer getPrefsModel() {
		return prefsModel;
	}

}
