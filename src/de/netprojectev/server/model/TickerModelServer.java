package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.UUID;

import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.liveticker.TickerElement;
import de.netprojectev.server.exceptions.MediaDoesNotExsistException;
import de.netprojectev.server.networking.MessageProxyServer;

public class TickerModelServer {
	
	private final MessageProxyServer proxy;
	private HashMap<UUID, TickerElement> elements;
	private String completeTickerText;
	
	public TickerModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		completeTickerText = "";
		elements = new HashMap<>();
	}
	
	public UUID addTickerElement(TickerElement e) {
		elements.put(e.getId(), e);
		return e.getId();
	}
	
	public void removeTickerElement(UUID id) throws MediaDoesNotExsistException {
		checkIfElementExists(id);
		elements.remove(id);
	}
	
	public TickerElement getElementByID(UUID id) throws MediaDoesNotExsistException {
		checkIfElementExists(id);
		return elements.get(id);
	}
	
	public String generateCompleteTickerText() {
		
		String completeString = "";
		
		for(UUID id : elements.keySet()) {
			if(elements.get(id).isShow()) {
				completeString += elements.get(id).getText() + PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR);
			}
		}
		
		return completeString;
	}
	
	private void checkIfElementExists(UUID id) throws MediaDoesNotExsistException {
		if(elements.get(id) == null) {
			throw new MediaDoesNotExsistException("Ticker element does not exist. Query id: " + id);
		}
	}

}
