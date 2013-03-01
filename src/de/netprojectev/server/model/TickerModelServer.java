package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.UUID;

import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.networking.MessageProxyServer;

public class TickerModelServer {
	
	private final MessageProxyServer proxy;
	private HashMap<UUID, TickerElement> elements;
	private String completeTickerText;
	
	public TickerModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		completeTickerText = "";
		
	}
	
	public UUID addTickerElement(TickerElement e) {
		
		return null;
	}
	
	public boolean removeTickerElement(UUID id) {
		
		return true;
	}
	
	public String generateCompleteTickerText() {
		
		//TODO
		
		return "";
	}

}
