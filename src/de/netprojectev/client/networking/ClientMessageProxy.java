package de.netprojectev.client.networking;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;

public class ClientMessageProxy {
	
	private static final Logger log = LoggerBuilder.createLogger(ClientMessageProxy.class);
	
	private final MediaModelClient mediaModel;
	private final TickerModelClient tickerModel;
	private final PreferencesModelClient prefs;
	
	private Channel channelToServer;

	public ClientMessageProxy() {
		mediaModel = new MediaModelClient(this);
		tickerModel = new TickerModelClient(this);
		prefs = new PreferencesModelClient(this);
	}
	
	
	public ChannelFuture sendMessageToServer(Message msgToSend) {
		log.debug("Sending message to server: " + msgToSend);
		return channelToServer.write(msgToSend);
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
