package de.netprojectev.server.networking;

import java.util.HashMap;

import org.jboss.netty.channel.Channel;

import de.netprojectev.networking.Message;
import de.netprojectev.old.server.gui.display.DisplayController;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.model.ServerMediaModel;
import de.netprojectev.server.model.TickerModel;

public class MessageProxy {
	private final HashMap<String, Channel> clients;
	private final ServerMediaModel mediaModel;
	private final TickerModel tickerModel;
	private final DisplayFrame frame;
	
	public MessageProxy() {
		this.clients = new HashMap<>();
		this.mediaModel = new ServerMediaModel(this);
		this.tickerModel = new TickerModel(this);
		this.frame = new DisplayFrame(this); 
		
	}
	
	public void receiveMessage(Message msg) {
		// TODO Auto-generated method stub
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

}
