package de.netprojectev.server.model;

import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.ServerTickerElement;
import de.netprojectev.server.networking.MessageProxyServer;

public class TickerModelServer {

	private static final Logger log = LoggerBuilder.createLogger(TickerModelServer.class);

	private final MessageProxyServer proxy;
	private HashMap<UUID, ServerTickerElement> elements;
	private String completeTickerText;
	
	public TickerModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		completeTickerText = "";
		elements = new HashMap<>();
	}
	
	public UUID addTickerElement(ServerTickerElement e) {
		log.debug("Adding ticker element: " + e);
		elements.put(e.getId(), e);
		return e.getId();
	}
	
	public void removeTickerElement(UUID id) throws MediaDoesNotExsistException {
		log.debug("Removing ticker element: " + id);
		checkIfElementExists(id);
		elements.remove(id);
	}
	
	public ServerTickerElement getElementByID(UUID id) throws MediaDoesNotExsistException {
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

}
