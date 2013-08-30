package de.netprojectev.server.networking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.DequeueData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.networking.VideoFileData;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.Server;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.gui.DisplayFrame.VideoFinishListener;
import de.netprojectev.server.model.MediaModelServer;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.server.model.TickerModelServer;
import de.netprojectev.server.networking.VideoFileReceiveHandler.ToManyMessagesException;

public class MessageProxyServer {
	
	private static final Logger log = LoggerBuilder.createLogger(MessageProxyServer.class);
	
	private final DefaultChannelGroup allClients;
	private final ArrayList<ConnectedUser> connectedUsers;
	private final MediaModelServer mediaModel;
	private final TickerModelServer tickerModel;
	private final PreferencesModelServer prefsModel;
	private final VideoFileReceiveHandler videoFileReceiveHandler;
	private final DisplayFrame display;
	private final Server server;
	private final HashedWheelTimer timeoutChecker;
	private int timeoutInSeconds;
	private boolean automodeEnabled;
	private boolean fullscreenEnabled;
	private boolean liveTickerEnabled;
	private ServerMediaFile currentFile;
	private Timer autoModusTimer;
	
	private class AutomodeTimerTask extends TimerTask {

		@Override
		public void run() {
			try {
				showNextMediaFile();
			} catch (MediaDoesNotExsistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MediaListsEmptyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	private class TimeoutTimerTask implements org.jboss.netty.util.TimerTask {

		@Override
		public void run(Timeout timeout) throws Exception {
			
			ArrayList<ConnectedUser> timedOuts = new ArrayList<>();
			
			timeoutChecker.newTimeout(new TimeoutTimerTask(), timeoutInSeconds, TimeUnit.SECONDS);
			for(ConnectedUser user : connectedUsers) {
				if(!user.isAlive()) {
					timedOuts.add(user);
				}
				user.setAlive(false);
			}
			for(ConnectedUser user : timedOuts) {
				clientTimedOut(user.getChannel());
			}
			broadcastMessage(new Message(OpCode.STC_HEARTBEAT_REQUEST));	
		}
		
		
	}
	
	public MessageProxyServer(Server server) {
		this.allClients = new DefaultChannelGroup("beamer-clients");
		this.mediaModel = new MediaModelServer(this);
		this.tickerModel = new TickerModelServer(this);
		this.prefsModel = new PreferencesModelServer(this);
		this.videoFileReceiveHandler = new VideoFileReceiveHandler();
		this.display = new DisplayFrame(this);
		this.server = server;
		this.connectedUsers = new ArrayList<>();
		this.timeoutChecker = new HashedWheelTimer();
		
		try {
			this.timeoutInSeconds = Integer.parseInt(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_HEARTBEAT_INTERVALL));
		} catch (NumberFormatException e) {
			try {
				this.timeoutInSeconds = Integer.parseInt(ConstantsServer.DEFAULT_HEARTBEAT_INTERVALL);
			} catch (NumberFormatException e1) {
				this.timeoutInSeconds = 30;
			}
			log.warn("Read timeout is no number.", e);
		}
		
		this.timeoutChecker.newTimeout(new TimeoutTimerTask(), timeoutInSeconds, TimeUnit.SECONDS);
		
	}
	//TODO add propper exception handling, e.g. force a resync of client after a outofsyncexc.
	public void receiveMessage(Message msg, Channel channel) throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException, ToManyMessagesException {
		switch (msg.getOpCode()) {
		case CTS_ADD_IMAGE_FILE:
			addImageFile(msg);
			break;
		case CTS_ADD_COUNTDOWN:
			addCountdown(msg);
			break;
		case CTS_ADD_THEMESLIDE:
			addThemeSlide(msg);
			break;
		case CTS_REMOVE_MEDIA_FILE:
			removeMediaFile(msg);
			break;
		case CTS_SHOW_MEDIA_FILE:
			showMediaFile(msg);
			break;
		case CTS_EDIT_MEDIA_FILE:
			editMediaFile(msg);
			break;
		case CTS_EDIT_LIVE_TICKER_ELEMENT:
			editLiveTickerElement(msg);
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
		case CTS_ENABLE_LIVE_TICKER:
			enableLiveTicker();
			break;
		case CTS_DISABLE_LIVE_TICKER:
			disableLiveTicker();
			break;
		case CTS_ENABLE_AUTO_MODE:
			enableAutoMode();
			break;
		case CTS_DISABLE_AUTO_MODE:
			disableAutoMode();
			break;
		case CTS_ENABLE_FULLSCREEN:
			enableFullScreen();
			break;
		case CTS_DISABLE_FULLSCREEN:
			disableFullScreen();
			break;
		case CTS_DEQUEUE_MEDIAFILE:
			dequeueMediaFile(msg);
			break;
		case CTS_REQUEST_FULL_SYNC:
			fullSyncRequested(msg, channel);
			break;
		case CTS_RESET_SHOW_COUNT:
			resetShowCount(msg);
			break;
		case CTS_ADD_VIDEO_FILE_START:
			videoTransferStarted(msg);
			break;
		case CTS_ADD_VIDEO_FILE_FINISH:
			videoTransferFinished(msg);
			break;
		case CTS_ADD_VIDEO_FILE_DATA:
			videoTransferReceiveData(msg);
			break;
		case CTS_REQUEST_SERVER_SHUTDOWN:
			serverShutdownRequested();
			break;
		case CTS_HEARTBEAT_ACK:
			hearbeatack(channel);
			break;
		case CTS_PROPERTY_UPDATE:
			propertyUpdate(msg);
			break;
		default:
			unkownMessageReceived(msg);
			break;
		}
	}

	private void addMediaFile(ServerMediaFile toAdd) throws FileNotFoundException, IOException {
		mediaModel.addMediaFile(toAdd);
		broadcastMessage(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, new ClientMediaFile(toAdd)));
	}
	
	private void addImageFile(Message msg) throws FileNotFoundException, IOException {
		String name = (String) msg.getData()[0];
		Priority priority = (Priority) msg.getData()[1];
		int[][] rgbaValues = (int[][]) msg.getData()[2];
		addMediaFile(new ImageFile(name, priority, rgbaValues));
		
	}
	
	private void addCountdown(Message msg) throws FileNotFoundException, IOException {
		addMediaFile((ServerMediaFile) msg.getData()[0]);
	}
	
	private void addThemeSlide(Message msg) throws FileNotFoundException, IOException {
		String name = (String) msg.getData()[0];
		UUID themeId = (UUID) msg.getData()[1];
		Priority priority = (Priority) msg.getData()[2];
		int[][] rgbaValues = (int[][]) msg.getData()[3];
		
		addMediaFile(new Themeslide(name, themeId, priority, new ImageFile(name, priority, rgbaValues)));
		
	}
	private void propertyUpdate(Message msg) {
		String key = (String) msg.getData()[0];
		String newValue = (String) msg.getData()[1];
		
		PreferencesModelServer.setProperty(key, newValue);
		broadcastMessage(new Message(OpCode.STC_PROPERTY_UPDATE_ACK, key, newValue));
	}
	
	private void hearbeatack(Channel channel) {
		findUserByChan(channel).setAlive(true);
	}
	
	private void serverShutdownRequested() {
		
		server.shutdownServer();
	}
	
	private void videoTransferReceiveData(Message msg) throws IOException, ToManyMessagesException {
		VideoFileData data = (VideoFileData) msg.getData()[0];
		
		videoFileReceiveHandler.receiveData(data.getVideoFileUUID(), data.getByteBuffer());
	}
	
	private void videoTransferFinished(Message msg) throws IOException {
		VideoFile file = (VideoFile) msg.getData()[0];
		videoFileReceiveHandler.finishingReceivingVideo(file.getId());
		
		file.setVideoFile(new File(ConstantsServer.SAVE_PATH + ConstantsServer.CACHE_PATH + ConstantsServer.CACHE_PATH_VIDEOS + file.getId()));
		
		mediaModel.addMediaFile(file);
		broadcastMessage(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, new ClientMediaFile(file)));
	}
	
	private void videoTransferStarted(Message msg) throws FileNotFoundException {
		UUID idOfVideoFile = (UUID) msg.getData()[0];
		int estMsgCount = (Integer) msg.getData()[1];
		videoFileReceiveHandler.startingReceivingVideo(idOfVideoFile, estMsgCount);
	}
	
	private void resetShowCount(Message msg) throws MediaDoesNotExsistException {
		UUID toReset = (UUID) msg.getData()[0];
		mediaModel.resetShowCount(toReset);
		broadcastMessage(new Message(OpCode.STC_RESET_SHOW_COUNT_ACK, toReset));
	}
	
	private void editLiveTickerElement(Message msg) throws MediaDoesNotExsistException {
		ClientTickerElement edited = (ClientTickerElement) msg.getData()[0];
		ServerTickerElement correlatedServerFile = tickerModel.getElementByID(edited.getId());
		correlatedServerFile.setShow(edited.isShow());
		correlatedServerFile.setText(edited.getText());
		broadcastMessage(new Message(OpCode.STC_EDIT_LIVE_TICKER_ELEMENT_ACK, new ClientTickerElement(correlatedServerFile)));
	}
	
	private void editMediaFile(Message msg) throws MediaDoesNotExsistException, FileNotFoundException, IOException {
		ClientMediaFile editedFile = (ClientMediaFile) msg.getData()[0];
		ServerMediaFile correlatedServerFile = mediaModel.getMediaFileById(editedFile.getId());		
		correlatedServerFile.setName(editedFile.getName());
		correlatedServerFile.setPriority(editedFile.getPriority()); //TODO check if this is working with objects, else change to id based prios
		broadcastMessage(new Message(OpCode.STC_EDIT_MEDIA_FILE_ACK, new ClientMediaFile(correlatedServerFile)));
	}
	
	public void makeGUIVisible() {
		this.display.setVisible(true);
	}
	
	public ChannelGroupFuture broadcastMessage(Message msg) {
		log.debug("Broadcasting message: " + msg);
		return allClients.write(msg);
	}
	
	public void clientConnected(Channel chan, String alias) {
		log.info("Client connected.");
		allClients.add(chan);
		connectedUsers.add(new ConnectedUser(chan, alias));
	}
	
	public void clientDisconnected(Channel chan, String alias) {
		
		allClients.remove(chan);
		ConnectedUser user = findUserByChan(chan);
		connectedUsers.remove(user);
		log.info("Client disconnected. Alias: " + user.getAlias());
	}
	
	public void clientTimedOut(Channel chan) {
		allClients.remove(chan);
		ConnectedUser user = findUserByChan(chan);
		connectedUsers.remove(user);
		log.info("Client timed out. Alias: " + user.getAlias());
	}
	
	public ConnectedUser findUserByChan(Channel chan) {
		for(ConnectedUser user : connectedUsers) {
			if(user.getChannel().equals(chan)) {
				return user;
			}
		}
		return null;
	}
	
	public ConnectedUser findUserByAlias(String alias) {
		for(ConnectedUser user : connectedUsers) {
			if(user.getAlias().equals(alias)) {
				return user;
			}
		}
		return null;
	}
	
	private void dequeueMediaFile(Message msg) throws MediaDoesNotExsistException, OutOfSyncException {
		DequeueData mediaToDequeue = (DequeueData) msg.getData()[0];
		mediaModel.dequeue(mediaToDequeue.getId(), mediaToDequeue.getRow());
		
		broadcastMessage(new Message(OpCode.STC_DEQUEUE_MEDIAFILE_ACK, mediaToDequeue));

	}
	
	
	//TODO last worked here: made changing server props work (only live ticker sep atm)
	/*
	 * 1 Add proper serialization (properties are good, but serialize the media and ticker elts and so on too)
	 * 2 Check all TODOS
	 * 3 next exception handling
	 * 4 Test with notebook as server (esp. video things and fullscreen switches (preferred using tv as monitor like the beamer))
	 * 
	 * add handling for further prefs like changing fonts and colors of live ticker
	 * pimp the live ticker a little slightly transparent overlay or something like that 
 	 * next pimp server gui (Effects and so on)
	 *
	 * check for already running server instances, and esp. get a port from the os not fixed (scanning the network for the server is required then)
	 * 
	 * pimp the clinet gui -> resizable areas, event log, loading dialogs during network operations
	 * 
	 * next check all the image to imageicon conversions and choose best for performance and RAM usage
	 * next new themeslide creator using templates or something like that
	 * next proper login handling (also to see which user did what and when) and maybe the possibility to send messages or notes or something like that
	 */
	private void fullSyncRequested(Message msg, Channel channel) throws FileNotFoundException, IOException {
		
		HashMap<UUID, ServerMediaFile> allMedia = mediaModel.getAllMediaFiles();
		LinkedList<UUID> customQueue = mediaModel.getMediaPrivateQueue();
		HashMap<UUID, ServerTickerElement> tickerElements = tickerModel.getElements();
		HashMap<UUID, Theme> themes = prefsModel.getThemes();
		HashMap<UUID, Priority> priorities = prefsModel.getPrios();
		
		channel.write(new Message(OpCode.STC_FULL_SYNC_START));
		
		channel.write(new Message(OpCode.STC_INIT_PROPERTIES, PreferencesModelServer.getProps()));
		
		for(UUID id : allMedia.keySet()) {
			channel.write(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, new ClientMediaFile(allMedia.get(id))));
		}
		
		for(UUID id : customQueue) {
			channel.write(new Message(OpCode.STC_QUEUE_MEDIA_FILE_ACK, id));
		}
		
		for(UUID id : tickerElements.keySet()) {
			channel.write(new Message(OpCode.STC_ADD_LIVE_TICKER_ELEMENT_ACK, new ClientTickerElement(tickerElements.get(id))));
		}
		
		for(UUID id : themes.keySet()) {
			channel.write(new Message(OpCode.STC_ADD_THEME_ACK, themes.get(id)));
		}
			
		for(UUID id : priorities.keySet()) {
			channel.write(new Message(OpCode.STC_ADD_PRIORITY_ACK, priorities.get(id)));
		}
		
		if(automodeEnabled) {
			channel.write(new Message(OpCode.STC_ENABLE_AUTO_MODE_ACK));
		}
		
		if(liveTickerEnabled) {
			channel.write(new Message(OpCode.STC_ENABLE_LIVE_TICKER_ACK));
		}
		
		if(fullscreenEnabled) {
			channel.write(new Message(OpCode.STC_ENABLE_FULLSCREEN_ACK));
		}
		
		channel.write(new Message(OpCode.STC_FULL_SYNC_STOP));
	}
	
	private void removePriority(Message msg) {
		UUID prioToRemove = (UUID) msg.getData()[0];
		prefsModel.removePriority(prioToRemove);
		broadcastMessage(new Message(OpCode.STC_REMOVE_PRIORITY_ACK, prioToRemove));
	}

	private void removeTheme(Message msg) {
		UUID themeToRemove = (UUID) msg.getData()[0];
		prefsModel.removeTheme(themeToRemove);
		broadcastMessage(new Message(OpCode.STC_REMOVE_THEME_ACK, themeToRemove));
	}

	private void addPriority(Message msg) {
		Priority prioToAdd = (Priority) msg.getData()[0];
		prefsModel.addPriority(prioToAdd);
		broadcastMessage(new Message(OpCode.STC_ADD_PRIORITY_ACK, prioToAdd));
	}

	private void addTheme(Message msg) {
		Theme themeToAdd = (Theme) msg.getData()[0];
		prefsModel.addTheme(themeToAdd);
		broadcastMessage(new Message(OpCode.STC_ADD_THEME_ACK, themeToAdd));
	}
	
	private void removeLiveTickerElement(Message msg) throws MediaDoesNotExsistException {
		UUID eltToRemove = (UUID) msg.getData()[0];
		tickerModel.removeTickerElement(eltToRemove);
		
		broadcastMessage(new Message(OpCode.STC_REMOVE_LIVE_TICKER_ELEMENT_ACK, eltToRemove));
	}

	private void addLiveTickerElement(Message msg) {
		ServerTickerElement eltToAdd = (ServerTickerElement) msg.getData()[0];
		tickerModel.addTickerElement(eltToAdd);
		display.updateLiveTicker();
		broadcastMessage(new Message(OpCode.STC_ADD_LIVE_TICKER_ELEMENT_ACK, new ClientTickerElement(eltToAdd)));
	}

	private void queueMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toQueue = (UUID) msg.getData()[0];
		mediaModel.queue(toQueue);		
		
		broadcastMessage(new Message(OpCode.STC_QUEUE_MEDIA_FILE_ACK, toQueue));
	}

	private void showNextMediaFile() throws MediaDoesNotExsistException, MediaListsEmptyException {
		boolean fileFromPrivateQueue = false;
		if(!mediaModel.getMediaPrivateQueue().isEmpty()) {
			fileFromPrivateQueue = true;
		}
		ServerMediaFile fileToShow = mediaModel.getNext();
		
		showMedia(fileToShow);
		
		if(fileFromPrivateQueue) {
			broadcastMessage(new Message(OpCode.STC_DEQUEUE_MEDIAFILE_ACK, new DequeueData(0, fileToShow.getId())));
		}		
	}

	private void showMediaFile(Message msg) throws MediaDoesNotExsistException, MediaListsEmptyException {
		UUID toShow = (UUID) msg.getData()[0];
		ServerMediaFile fileToShow = mediaModel.getMediaFileById(toShow);
		
		showMedia(fileToShow);

	}
	
	
	private void showMedia(ServerMediaFile toShow) throws MediaDoesNotExsistException, MediaListsEmptyException {
		if(currentFile != null) {
			currentFile.setCurrent(false);
		}
		currentFile = toShow;
		toShow.setCurrent(true).increaseShowCount();
		
		if(toShow instanceof VideoFile) {
			display.setVideoFinishedListener(new VideoFinishListener() {
				
				@Override
				public void videoFinished() throws MediaDoesNotExsistException, MediaListsEmptyException {
					if(automodeEnabled) {
						showNextMediaFile();
					}
				}
			});
		} else {
			updateAutoModeTimer();
		}
		
		try {
			display.showMediaFileInMainComponent(currentFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		broadcastMessage(new Message(OpCode.STC_SHOW_MEDIA_FILE_ACK, toShow.getId()));
	}

	private void unkownMessageReceived(Message msg) throws UnkownMessageException {
		throw new UnkownMessageException("A unkown message was received. Debug information: " + msg.toString());	
	}

	private void removeMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toRemove = (UUID) msg.getData()[0];
		mediaModel.removeMediaFile(toRemove);
		
		broadcastMessage(new Message(OpCode.STC_REMOVE_MEDIA_FILE_ACK, toRemove));
	}

	private void enableFullScreen() {
		fullscreenEnabled = true;
		
		//TODO update display
		
		broadcastMessage(new Message(OpCode.STC_ENABLE_FULLSCREEN_ACK));
	}
	
	private void disableFullScreen() {
		fullscreenEnabled = false;
		//TODO update display	
		
		broadcastMessage(new Message(OpCode.STC_DISABLE_FULLSCREEN_ACK));
		
	}

	private void enableAutoMode() throws MediaDoesNotExsistException, MediaListsEmptyException {
		this.automodeEnabled = true;
		broadcastMessage(new Message(OpCode.STC_ENABLE_AUTO_MODE_ACK));
		updateAutoModeTimer();
	}
	private void updateAutoModeTimer() throws MediaDoesNotExsistException, MediaListsEmptyException {
		if(automodeEnabled) {
			if(autoModusTimer != null) {
				autoModusTimer.cancel();
				autoModusTimer.purge();			
			}
			autoModusTimer = new Timer();
			if(currentFile == null) {
				showNextMediaFile();
			} else {
				if(!(currentFile instanceof Countdown)) {
					autoModusTimer.schedule(new AutomodeTimerTask(), currentFile.getPriority().getTimeToShowInMilliseconds());
				} else {
					autoModusTimer.schedule(new AutomodeTimerTask(), ((Countdown) currentFile).getDurationInSeconds() * 1000);
					broadcastMessage(new Message(OpCode.STC_TIMELEFT_SYNC, ((Countdown) currentFile).getDurationInSeconds()));
				}
				
			}
			
		}
		
	}
	
	private void disableAutoMode() {
		this.automodeEnabled = false;
		autoModusTimer.cancel();
		autoModusTimer.purge();
		autoModusTimer = null;
		broadcastMessage(new Message(OpCode.STC_DISABLE_AUTO_MODE_ACK));
	}

	private void enableLiveTicker() {
		liveTickerEnabled = true;
		
		display.startLiveTicker();
		
		broadcastMessage(new Message(OpCode.STC_ENABLE_LIVE_TICKER_ACK));
	}
	
	private void disableLiveTicker() {
		liveTickerEnabled = false;
		
		display.stopLiveTicker();
		
		broadcastMessage(new Message(OpCode.STC_DISABLE_LIVE_TICKER_ACK));
	}
	
	public boolean isAutomodeEnabled() {
		return automodeEnabled;
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
		return display;
	}

	public ServerMediaFile getCurrentFile() {
		return currentFile;
	}

	public PreferencesModelServer getPrefsModel() {
		return prefsModel;
	}
	public boolean isFullscreenEnabled() {
		return fullscreenEnabled;
	}
	public boolean isLiveTickerEnabled() {
		return liveTickerEnabled;
	}
	public ArrayList<ConnectedUser> getConnectedUsers() {
		return connectedUsers;
	}

}
