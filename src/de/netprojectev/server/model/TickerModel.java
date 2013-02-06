package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.UUID;

import de.netprojectev.old.server.gui.display.DisplayController;
import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.networking.MessageHandler;
import de.netprojectev.server.networking.MessageProxy;

public class TickerModel {
	
	private final MessageProxy proxy;
	private HashMap<UUID, TickerElement> elements;
	private String completeTickerText;
	
	public TickerModel(MessageProxy proxy) {
		this.proxy = proxy;
		completeTickerText = "";
		
	}
	
	public String generateCompleteTickerText() {
		
		//TODO
		
		return "";
	}

}
