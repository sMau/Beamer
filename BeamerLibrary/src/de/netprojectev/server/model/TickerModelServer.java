package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.datastructures.TickerElement;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.utils.LoggerBuilder;

public class TickerModelServer {

	private static final Logger log = LoggerBuilder.createLogger(TickerModelServer.class);

	private final MessageProxyServer proxy;
	private final HashMap<UUID, TickerElement> elements;
	private String completeTickerText;
	
	public TickerModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		completeTickerText = "";
		elements = new HashMap<UUID, TickerElement>();
	}
	
	public UUID addTickerElement(TickerElement e) {
		log.debug("Adding ticker element: " + e);
		elements.put(e.getId(), e);
		return e.getId();
	}
	
	public void removeTickerElement(UUID id) throws MediaDoesNotExsistException {
		log.debug("Removing ticker element: " + id);
		checkIfElementExists(id);
		elements.remove(id);
	}
	
	public TickerElement getElementByID(UUID id) throws MediaDoesNotExsistException {
		checkIfElementExists(id);
		log.debug("Getting ticker element: " + id);
		return elements.get(id);
	}
	
	public String generateCompleteTickerText() {
		
		String completeString = "";
		
		for(UUID id : elements.keySet()) {
			if(elements.get(id).isShow()) {
				completeString += elements.get(id).getText() + PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR);
			}
		}
		log.debug("Generating complete ticker string: " + completeString);
		return completeString;
	}
	
	private void checkIfElementExists(UUID id) throws MediaDoesNotExsistException {
		if(elements.get(id) == null) {
			throw new MediaDoesNotExsistException("Ticker element does not exist. Query id: " + id);
		}
	}

	public HashMap<UUID, TickerElement> getElements() {
		return elements;
	}

}
