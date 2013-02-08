package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.UUID;

import de.netprojectev.old.server.gui.display.DisplayController;
import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.networking.MessageHandlerServer;
import de.netprojectev.server.networking.MessageProxyServer;

public class TickerModelServer {
	
	private final MessageProxyServer proxy;
	private HashMap<UUID, TickerElement> elements;
	private String completeTickerText;
	
	public TickerModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		completeTickerText = "";
		
	}
	
	public String generateCompleteTickerText() {
		
		//TODO
		
		return "";
	}

}
