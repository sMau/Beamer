package de.netprojectev.server.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.datastructures.Priority;
import de.netprojectev.datastructures.Theme;
import de.netprojectev.datastructures.TickerElement;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.UnkownMessageException;
import de.netprojectev.networking.DequeueData;
import de.netprojectev.networking.LoginData;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.Server;
import de.netprojectev.server.ServerGUI;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.model.MediaModelServer;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.server.model.TickerModelServer;
import de.netprojectev.utils.LoggerBuilder;

//XXX maybe use sha1 checksums for the files to detect already "added" files and match them
// or at least clean the chache in the other case

@Sharable
public class MessageProxyServer extends MessageToMessageDecoder<Message> {

	private class AutomodeTimerTask extends TimerTask {

		@Override
		public void run() {
			try {
				showNextMediaFile();
			} catch (MediaDoesNotExsistException e) {
				log.warn("Media file could not be shown.", e);
			} catch (MediaListsEmptyException e) {
				log.warn("Media file could not be shown.", e);
			} catch (PriorityDoesNotExistException e) {
				log.warn("Media file could not be shown.", e);
			}
		}

	}

	public interface PropertyUpdateListener {
		public void propertyUpdate(String keyPropertyUpdated);
	}

	private class TimeoutTimerTask implements io.netty.util.TimerTask {
		// TODO timeout handling improvement. use nettys build in handlers which
		// also take sent traffic into account
		@Override
		public void run(Timeout timeout) throws Exception {

			ArrayList<ConnectedUser> timedOuts = new ArrayList<ConnectedUser>();

			MessageProxyServer.this.timeoutChecker.newTimeout(new TimeoutTimerTask(), MessageProxyServer.this.timeoutInSeconds, TimeUnit.SECONDS);
			for (ConnectedUser user : MessageProxyServer.this.connectedUsers) {
				if (!user.isAlive()) {
					timedOuts.add(user);
				}
				user.setAlive(false);
			}
			for (ConnectedUser user : timedOuts) {
				clientTimedOut(user.getChannel());
			}
			broadcastMessage(new Message(OpCode.STC_HEARTBEAT_REQUEST));
		}

	}

	public interface VideoFinishListener {
		public void videoFinished() throws MediaDoesNotExsistException, MediaListsEmptyException, PriorityDoesNotExistException;
	}

	private static final Logger log = LoggerBuilder.createLogger(MessageProxyServer.class);
	private final DefaultChannelGroup allClients;
	private final ArrayList<ConnectedUser> connectedUsers;
	private final MediaModelServer mediaModel;
	private final TickerModelServer tickerModel;
	private final PreferencesModelServer prefsModel;
	private final ServerGUI serverGUI;

	private final Server server;

	private final HashedWheelTimer timeoutChecker;
	private PropertyUpdateListener propertyUpdateListener;
	private int timeoutInSeconds;
	private boolean automodeEnabled;
	private boolean fullscreenEnabled;
	private boolean liveTickerEnabled;

	private ServerMediaFile currentFile;

	private Timer autoModusTimer;

	public MessageProxyServer(Server server, ServerGUI serverGUI) {

		this.allClients = new DefaultChannelGroup("beamer-clients", new NioEventLoopGroup().next());
		this.mediaModel = new MediaModelServer(this);
		this.tickerModel = new TickerModelServer(this);
		this.prefsModel = new PreferencesModelServer(this);
		this.prefsModel.deserializeAll();
		this.serverGUI = serverGUI;
		this.server = server;
		this.connectedUsers = new ArrayList<ConnectedUser>();
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

		// TODO hearbeat mechanism is diasbled, but is actually also bullshit
		// this.timeoutChecker.newTimeout(new TimeoutTimerTask(),
		// this.timeoutInSeconds, TimeUnit.SECONDS);

	}

	private void addCountdown(Message msg) throws FileNotFoundException, IOException {
		addMediaFile((ServerMediaFile) msg.getData().get(0));
	}

	private void addImageFile(Message msg) throws FileNotFoundException, IOException {
		ImageFile imageFile = (ImageFile) msg.getData().get(0);
		addMediaFile(imageFile);
	}

	private void addLiveTickerElement(Message msg) throws IOException {
		TickerElement eltToAdd = (TickerElement) msg.getData().get(0);
		this.tickerModel.addTickerElement(eltToAdd);

		if (this.liveTickerEnabled) {
			this.serverGUI.updateLiveTickerString();
		}

		broadcastMessage(new Message(OpCode.STC_ADD_LIVE_TICKER_ELEMENT_ACK, eltToAdd));

		this.prefsModel.serializeTickerDatabase();
	}

	private void addMediaFile(ServerMediaFile toAdd) throws FileNotFoundException, IOException {
		this.mediaModel.addMediaFile(toAdd);
		broadcastMessage(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, new ClientMediaFile(toAdd)));

		this.prefsModel.serializeMediaDatabase();
	}

	private void addPriority(Message msg) throws IOException {
		Priority prioToAdd = (Priority) msg.getData().get(0);
		this.prefsModel.addPriority(prioToAdd);
		broadcastMessage(new Message(OpCode.STC_ADD_PRIORITY_ACK, prioToAdd));

		this.prefsModel.serializePriorityDatabase();

	}

	private void addTheme(Message msg) throws IOException {
		Theme themeToAdd = (Theme) msg.getData().get(0);
		this.prefsModel.addTheme(themeToAdd);
		broadcastMessage(new Message(OpCode.STC_ADD_THEME_ACK, themeToAdd));

		this.prefsModel.serializeThemeDatabase();

	}

	private void addThemeSlide(Message msg) throws FileNotFoundException, IOException {
		Themeslide themeslide = (Themeslide) msg.getData().get(0);
		addMediaFile(themeslide);
	}

	private void addVideoFile(Message msg) throws IOException {
		VideoFile file = (VideoFile) msg.getData().get(0);
		addMediaFile(file);
	}

	public ChannelGroupFuture broadcastMessage(Message msg) {
		log.debug("Broadcasting message: " + msg);
		return this.allClients.writeAndFlush(msg);
	}

	public void clientConnected(Channel chan, String alias) {
		log.info("Client connected.");
		this.allClients.add(chan);
		this.connectedUsers.add(new ConnectedUser(chan, alias));
	}

	private void clientDisconnect(Message msg, ChannelHandlerContext ctx) {
		clientDisconnected(ctx.channel(), (String) msg.getData().get(0));
	}

	public void clientDisconnected(Channel chan, String alias) {

		this.allClients.remove(chan);
		ConnectedUser user = findUserByChan(chan);
		this.connectedUsers.remove(user);
		log.info("Client disconnected. Alias: " + user.getAlias());
	}

	public void clientTimedOut(Channel chan) {
		this.allClients.remove(chan);
		ConnectedUser user = findUserByChan(chan);
		log.info("Client timed out. Alias: " + user.getAlias());
		this.connectedUsers.remove(user);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
		receiveMessage(msg, ctx);
	}

	private void denyAccessToClient(String reason, ChannelHandlerContext ctx) throws InterruptedException {
		log.warn("Login request denied: " + reason);
		ctx.writeAndFlush(new Message(OpCode.STC_LOGIN_DENIED, reason)).awaitUninterruptibly();
		ctx.close().sync();
	}

	private void dequeueMediaFile(Message msg) throws MediaDoesNotExsistException, OutOfSyncException {
		DequeueData mediaToDequeue = (DequeueData) msg.getData().get(0);
		this.mediaModel.dequeue(mediaToDequeue.getId(), mediaToDequeue.getRow());

		broadcastMessage(new Message(OpCode.STC_DEQUEUE_MEDIAFILE_ACK, mediaToDequeue));

	}

	private void disableAutoMode() {
		this.automodeEnabled = false;
		this.autoModusTimer.cancel();
		this.autoModusTimer.purge();
		this.autoModusTimer = null;
		broadcastMessage(new Message(OpCode.STC_DISABLE_AUTO_MODE_ACK));
	}

	private void disableFullScreen() {
		this.fullscreenEnabled = false;

		this.serverGUI.exitFullscreen();

		broadcastMessage(new Message(OpCode.STC_DISABLE_FULLSCREEN_ACK));

	}

	private void disableLiveTicker() {
		this.liveTickerEnabled = false;

		this.serverGUI.stopLiveTicker();

		broadcastMessage(new Message(OpCode.STC_DISABLE_LIVE_TICKER_ACK));
	}

	private void editLiveTickerElement(Message msg) throws MediaDoesNotExsistException, IOException {
		TickerElement edited = (TickerElement) msg.getData().get(0);
		TickerElement correlatedServerFile = this.tickerModel.getElementByID(edited.getId());
		correlatedServerFile.setShow(edited.isShow());
		correlatedServerFile.setText(edited.getText());

		if (this.liveTickerEnabled) {
			this.serverGUI.updateLiveTickerString();
		}

		broadcastMessage(new Message(OpCode.STC_EDIT_LIVE_TICKER_ELEMENT_ACK, correlatedServerFile));
		this.prefsModel.serializeTickerDatabase();
	}

	private void editMediaFile(Message msg) throws MediaDoesNotExsistException, FileNotFoundException, IOException, URISyntaxException {
		ClientMediaFile editedFile = (ClientMediaFile) msg.getData().get(0);
		ServerMediaFile correlatedServerFile = this.mediaModel.getMediaFileById(editedFile.getId());
		correlatedServerFile.setName(editedFile.getName());
		correlatedServerFile.setPriority(editedFile.getPriorityID());
		broadcastMessage(new Message(OpCode.STC_EDIT_MEDIA_FILE_ACK, new ClientMediaFile(correlatedServerFile)));

		this.prefsModel.serializeMediaDatabase();
	}

	private void enableAutoMode() throws MediaDoesNotExsistException, MediaListsEmptyException, PriorityDoesNotExistException {
		this.automodeEnabled = true;
		broadcastMessage(new Message(OpCode.STC_ENABLE_AUTO_MODE_ACK));
		updateAutoModeTimer();
	}

	public void enableFullScreen() {
		this.fullscreenEnabled = true;

		this.serverGUI.enterFullscreen(0);

		broadcastMessage(new Message(OpCode.STC_ENABLE_FULLSCREEN_ACK));
	}

	private void enableLiveTicker() {
		this.liveTickerEnabled = true;

		this.serverGUI.startLiveTicker();

		broadcastMessage(new Message(OpCode.STC_ENABLE_LIVE_TICKER_ACK));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("Exception caught in channel handler " + getClass(), cause.getCause());
		ctx.channel().close(); // XXX check if proper handling possible
	}

	public ConnectedUser findUserByAlias(String alias) {
		for (ConnectedUser user : this.connectedUsers) {
			if (user.getAlias().equals(alias)) {
				return user;
			}
		}
		return null;
	}

	public ConnectedUser findUserByChan(Channel chan) {
		for (ConnectedUser user : this.connectedUsers) {
			InetSocketAddress argChanAddr = ((InetSocketAddress) chan.remoteAddress());
			InetSocketAddress searchChanAddr = ((InetSocketAddress) user.getChannel().remoteAddress());
			if (searchChanAddr.getAddress().equals(argChanAddr.getAddress())
					&& searchChanAddr.getPort() == argChanAddr.getPort()) {
				return user;
			}
		}
		return null;
	}

	/*
	 * !!!!!!!!!!!!!!! !!!!!! Test with notebook as server (esp. video things
	 * and fullscreen switches (preferred using tv as monitor like the beamer))
	 * !!!!!!!!!!!!!!!
	 *
	 * DONE 1 Add proper serialization (properties are good, but serialize the
	 * media and ticker elts and so on too) DONE 2 Check all TODOS DONE 3 next
	 * exception handling DONE 4 reconnecting with a timer -> see todo reconnect
	 * in clientmsghandler
	 *
	 * DONE add handling for further prefs like changing fonts and colors of
	 * live ticker
	 *
	 * DONE Use Command line lib like: http://jewelcli.lexicalscope.com
	 * http://pholser.github.io/jopt-simple/
	 *
	 * Use Installer lib like IzPack
	 *
	 * DONE pimp the live ticker a little slightly transparent overlay or
	 * something like that next pimp server gui (Effects and so on)
	 *
	 * Advanced/Alternative themeslide creator using templates or something like
	 * that
	 *
	 * check for already running server instances, and esp. get a port from the
	 * os not fixed (scanning the network for the server is required then)
	 *
	 * pimp the client gui -> resizable areas, event log, loading dialogs during
	 * network operations
	 *
	 * next check all the image to imageicon conversions and choose best for
	 * performance and RAM usage
	 *
	 * show once funktion z.b. für countdown oder videos
	 *
	 * next proper login handling (also to see which user did what and when) and
	 * maybe the possibility to send messages or notes or something like that
	 */
	private void fullSyncRequested(Message msg, ChannelHandlerContext ctx) throws FileNotFoundException, IOException {

		HashMap<UUID, ServerMediaFile> allMedia = this.mediaModel.getAllMediaFiles();
		LinkedList<UUID> customQueue = this.mediaModel.getMediaPrivateQueue();
		HashMap<UUID, TickerElement> tickerElements = this.tickerModel.getElements();
		HashMap<UUID, Theme> themes = this.prefsModel.getThemes();
		HashMap<UUID, Priority> priorities = this.prefsModel.getPrios();

		ctx.write(new Message(OpCode.STC_FULL_SYNC_START));

		for (String k : PreferencesModelServer.getProps().stringPropertyNames()) {
			ctx.write(new Message(OpCode.STC_PROPERTY_UPDATE_ACK, k, PreferencesModelServer.getProps().getProperty(k)));
		}

		ctx.flush();

		for (UUID id : priorities.keySet()) {
			ctx.write(new Message(OpCode.STC_ADD_PRIORITY_ACK, priorities.get(id)));
		}

		ctx.flush();

		for (UUID id : themes.keySet()) {
			ctx.write(new Message(OpCode.STC_ADD_THEME_ACK, themes.get(id)));
		}

		ctx.flush();

		for (UUID id : allMedia.keySet()) {
			ctx.write(new Message(OpCode.STC_ADD_MEDIA_FILE_ACK, new ClientMediaFile(allMedia.get(id))));
		}

		ctx.flush();

		for (UUID id : customQueue) {
			ctx.write(new Message(OpCode.STC_QUEUE_MEDIA_FILE_ACK, id));
		}

		for (UUID id : tickerElements.keySet()) {
			ctx.write(new Message(OpCode.STC_ADD_LIVE_TICKER_ELEMENT_ACK, tickerElements.get(id)));
		}

		if (this.automodeEnabled) {
			ctx.write(new Message(OpCode.STC_ENABLE_AUTO_MODE_ACK));
		}

		if (this.liveTickerEnabled) {
			ctx.write(new Message(OpCode.STC_ENABLE_LIVE_TICKER_ACK));
		}

		if (this.fullscreenEnabled) {
			ctx.write(new Message(OpCode.STC_ENABLE_FULLSCREEN_ACK));
		}

		ctx.writeAndFlush(new Message(OpCode.STC_ALL_FONTS, (Object) ConstantsServer.FONT_FAMILIES));

		ctx.writeAndFlush(new Message(OpCode.STC_FULL_SYNC_STOP));
	}

	public DefaultChannelGroup getAllClients() {
		return this.allClients;
	}

	public Timer getAutoModusTimer() {
		return this.autoModusTimer;
	}

	public ArrayList<ConnectedUser> getConnectedUsers() {
		return this.connectedUsers;
	}

	public ServerMediaFile getCurrentFile() {
		return this.currentFile;
	}

	public ServerGUI getFrame() {
		return this.serverGUI;
	}

	public MediaModelServer getMediaModel() {
		return this.mediaModel;
	}

	public PreferencesModelServer getPrefsModel() {
		return this.prefsModel;
	}

	public TickerModelServer getTickerModel() {
		return this.tickerModel;
	}

	private void hearbeatack(ChannelHandlerContext ctx) {
		findUserByChan(ctx.channel()).setAlive(true);
	}

	public boolean isAutomodeEnabled() {
		return this.automodeEnabled;
	}

	public boolean isFullscreenEnabled() {
		return this.fullscreenEnabled;
	}

	public boolean isLiveTickerEnabled() {
		return this.liveTickerEnabled;
	}

	private void loginRequest(Message msg, ChannelHandlerContext ctx) throws InterruptedException {
		LoginData login = (LoginData) msg.getData().get(0);

		if (login.getKey().equals(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_PW))) {

			if (findUserByAlias(login.getAlias()) == null) {
				clientConnected(ctx.channel(), login.getAlias());

				ctx.writeAndFlush(new Message(OpCode.STC_CONNECTION_ACK));

				log.info("Client connected successfully. Alias: " + login.getAlias());
			} else {
				denyAccessToClient("Alias already in use.", ctx);
			}

		} else {
			denyAccessToClient("No valid login.", ctx);
		}

	}

	public void makeGUIVisible() {
		this.serverGUI.setVisible(true);
	}

	private void propertyUpdate(Message msg) {
		String key = (String) msg.getData().get(0);
		String newValue = (String) msg.getData().get(1);

		PreferencesModelServer.setProperty(key, newValue);

		if (this.propertyUpdateListener != null) {
			this.propertyUpdateListener.propertyUpdate(key);
		}

		broadcastMessage(new Message(OpCode.STC_PROPERTY_UPDATE_ACK, key, newValue));
	}

	private void queueMediaFile(Message msg) throws MediaDoesNotExsistException {
		UUID toQueue = (UUID) msg.getData().get(0);
		this.mediaModel.queue(toQueue);

		broadcastMessage(new Message(OpCode.STC_QUEUE_MEDIA_FILE_ACK, toQueue));
	}

	private void receiveMessage(Message msg, ChannelHandlerContext ctx) throws MediaDoesNotExsistException, MediaListsEmptyException, UnkownMessageException, OutOfSyncException, FileNotFoundException, IOException, PriorityDoesNotExistException, InterruptedException, URISyntaxException {
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
			fullSyncRequested(msg, ctx);
			break;
		case CTS_RESET_SHOW_COUNT:
			resetShowCount(msg);
			break;
		case CTS_ADD_VIDEO_FILE:
			addVideoFile(msg);
			break;
		case CTS_REQUEST_SERVER_SHUTDOWN:
			serverShutdownRequested();
			break;
		case CTS_HEARTBEAT_ACK:
			hearbeatack(ctx);
			break;
		case CTS_PROPERTY_UPDATE:
			propertyUpdate(msg);
			break;
		case CTS_LOGIN_REQUEST:
			loginRequest(msg, ctx);
			break;
		case CTS_DISCONNECT:
			clientDisconnect(msg, ctx);
			break;
		default:
			unkownMessageReceived(msg);
			break;
		}
	}

	private void removeLiveTickerElement(Message msg) throws MediaDoesNotExsistException, IOException {
		UUID eltToRemove = (UUID) msg.getData().get(0);
		this.tickerModel.removeTickerElement(eltToRemove);

		if (this.liveTickerEnabled) {
			this.serverGUI.updateLiveTickerString();
		}

		broadcastMessage(new Message(OpCode.STC_REMOVE_LIVE_TICKER_ELEMENT_ACK, eltToRemove));

		this.prefsModel.serializeTickerDatabase();
	}

	private void removeMediaFile(Message msg) throws MediaDoesNotExsistException, IOException {
		UUID toRemove = (UUID) msg.getData().get(0);
		this.mediaModel.removeMediaFile(toRemove);

		broadcastMessage(new Message(OpCode.STC_REMOVE_MEDIA_FILE_ACK, toRemove));

		this.prefsModel.serializeMediaDatabase();
	}

	private void removePriority(Message msg) throws IOException {
		UUID prioToRemove = (UUID) msg.getData().get(0);
		this.prefsModel.removePriority(prioToRemove);
		broadcastMessage(new Message(OpCode.STC_REMOVE_PRIORITY_ACK, prioToRemove));

		this.prefsModel.serializePriorityDatabase();
	}

	private void removeTheme(Message msg) throws IOException {
		UUID themeToRemove = (UUID) msg.getData().get(0);
		this.prefsModel.removeTheme(themeToRemove);
		broadcastMessage(new Message(OpCode.STC_REMOVE_THEME_ACK, themeToRemove));

		this.prefsModel.serializeThemeDatabase();
	}

	private void resetShowCount(Message msg) throws MediaDoesNotExsistException, IOException {
		UUID toReset = (UUID) msg.getData().get(0);
		this.mediaModel.resetShowCount(toReset);
		broadcastMessage(new Message(OpCode.STC_RESET_SHOW_COUNT_ACK, toReset));

	}

	private void serverShutdownRequested() {

		this.server.shutdownServer();
	}

	public void setAutoModusTimer(Timer autoModusTimer) {
		this.autoModusTimer = autoModusTimer;
	}

	public void setPropertyUpdateListener(PropertyUpdateListener propertyUpdateListener) {
		this.propertyUpdateListener = propertyUpdateListener;
	}

	private void showMedia(ServerMediaFile toShow) throws MediaDoesNotExsistException, MediaListsEmptyException, PriorityDoesNotExistException {
		if (this.currentFile != null) {
			this.currentFile.setCurrent(false);
		}
		this.currentFile = toShow;
		toShow.setCurrent(true).increaseShowCount();

		if (toShow instanceof VideoFile) {
			this.serverGUI.setVideoFinishedListener(new VideoFinishListener() {

				@Override
				public void videoFinished() throws MediaDoesNotExsistException, MediaListsEmptyException, PriorityDoesNotExistException {
					if (MessageProxyServer.this.automodeEnabled) {
						showNextMediaFile();
					}
				}
			});
		} else {
			updateAutoModeTimer();
		}

		try {
			this.serverGUI.showMediaFileInMainComponent(this.currentFile);
		} catch (IOException e) {
			log.warn("Media file could not be shown.", e);
			return;
		}
		broadcastMessage(new Message(OpCode.STC_SHOW_MEDIA_FILE_ACK, toShow.getId()));
	}

	private void showMediaFile(Message msg) throws MediaDoesNotExsistException, MediaListsEmptyException, PriorityDoesNotExistException {
		UUID toShow = (UUID) msg.getData().get(0);
		ServerMediaFile fileToShow = this.mediaModel.getMediaFileById(toShow);

		showMedia(fileToShow);

	}

	private void showNextMediaFile() throws MediaDoesNotExsistException, MediaListsEmptyException, PriorityDoesNotExistException {
		boolean fileFromPrivateQueue = false;
		if (!this.mediaModel.getMediaPrivateQueue().isEmpty()) {
			fileFromPrivateQueue = true;
		}
		ServerMediaFile fileToShow = this.mediaModel.getNext();

		showMedia(fileToShow);

		if (fileFromPrivateQueue) {
			broadcastMessage(new Message(OpCode.STC_DEQUEUE_MEDIAFILE_ACK, new DequeueData(0, fileToShow.getId())));
		}
	}

	private void unkownMessageReceived(Message msg) throws UnkownMessageException {
		throw new UnkownMessageException("A unkown message was received. Debug information: " + msg.toString());
	}

	private void updateAutoModeTimer() throws MediaDoesNotExsistException, MediaListsEmptyException, PriorityDoesNotExistException {

		if (this.automodeEnabled) {

			if (this.mediaModel.getAllMediaFiles().size() < 2) {
				disableAutoMode();
				return;
			}

			if (this.autoModusTimer != null) {
				this.autoModusTimer.cancel();
				this.autoModusTimer.purge();
			}

			this.autoModusTimer = new Timer();
			if (this.currentFile == null) {
				showNextMediaFile();
			} else {
				if (!(this.currentFile instanceof Countdown)) {
					this.autoModusTimer.schedule(new AutomodeTimerTask(), this.prefsModel.getPriorityById(this.currentFile.getPriorityID()).getTimeToShowInMilliseconds());
				} else {
					this.autoModusTimer.schedule(new AutomodeTimerTask(), ((Countdown) this.currentFile).getDurationInSeconds() * 1000);
					broadcastMessage(new Message(OpCode.STC_TIMELEFT_SYNC, ((Countdown) this.currentFile).getDurationInSeconds()));
				}

			}

		}

	}

}
