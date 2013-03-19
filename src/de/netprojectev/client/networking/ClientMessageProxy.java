package de.netprojectev.client.networking;

import org.apache.logging.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.networking.Message;

public class ClientMessageProxy {
	
	private static final Logger log = LoggerBuilder.createLogger(ClientMessageProxy.class);
	
	private Channel channelToServer;
	
	
	
	
	
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
	
}
