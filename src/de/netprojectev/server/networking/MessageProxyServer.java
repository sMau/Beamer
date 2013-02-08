package de.netprojectev.server.networking;

import java.util.HashMap;
import java.util.Timer;
import java.util.UUID;

import org.jboss.netty.channel.Channel;

import de.netprojectev.networking.Message;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.model.MediaModelServer;
import de.netprojectev.server.model.TickerModelServer;

public class MessageProxyServer {
	private final HashMap<String, Channel> clients;
	private final MediaModelServer mediaModel;
	private final TickerModelServer tickerModel;
	private final DisplayFrame frame;
	private boolean automodeEnabled;
	private boolean shufflemodeEnabled;
	private Timer autoModusTimer;
	
	public MessageProxyServer() {
		this.clients = new HashMap<>();
		this.mediaModel = new MediaModelServer(this);
		this.tickerModel = new TickerModelServer(this);
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

	private void showNext() {
		// TODO Auto-generated method stub
	}
	private void showPrevious() {
		// TODO Auto-generated method stub
		}
	private void showMediaFile(UUID id) {
		// TODO Auto-generated method stub
	}
	private void queue(UUID id) {
		// TODO Auto-generated method stub
	}
	private void enableAutoModus() {
		// TODO Auto-generated method stub
	}
	
	private void enableShuffleModus() {
		// TODO Auto-generated method stub
	}
	private void startLiveTicker() {
		// TODO Auto-generated method stub
	}
	private void stopLiveTicker() {
		// TODO Auto-generated method stub
	}

}
