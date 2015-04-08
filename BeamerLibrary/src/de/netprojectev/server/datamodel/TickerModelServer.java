package de.netprojectev.server.datamodel;

import java.util.HashMap;
import java.util.UUID;

import de.netprojectev.common.datastructures.TickerElement;
import de.netprojectev.common.exceptions.MediaDoesNotExsistException;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.common.utils.LoggerBuilder;

public class TickerModelServer {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(TickerModelServer.class);

	private final MessageProxyServer proxy;
	private final HashMap<UUID, TickerElement> elements;
	private String completeTickerText;

	public TickerModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		this.completeTickerText = "";
		this.elements = new HashMap<UUID, TickerElement>();
	}

	public UUID addTickerElement(TickerElement e) {
		log.fine("Adding ticker element: " + e);
		this.elements.put(e.getId(), e);
		return e.getId();
	}

	private void checkIfElementExists(UUID id) throws MediaDoesNotExsistException {
		if (this.elements.get(id) == null) {
			throw new MediaDoesNotExsistException("Ticker element does not exist. Query id: " + id);
		}
	}

	public String generateCompleteTickerText() {

		String completeString = "";

		for (UUID id : this.elements.keySet()) {
			if (this.elements.get(id).isShow()) {
				completeString += this.elements.get(id).getText() + PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR);
			}
		}
		log.fine("Generating complete ticker string: " + completeString);
		return completeString;
	}

	public TickerElement getElementByID(UUID id) throws MediaDoesNotExsistException {
		checkIfElementExists(id);
		log.fine("Getting ticker element: " + id);
		return this.elements.get(id);
	}

	public HashMap<UUID, TickerElement> getElements() {
		return this.elements;
	}

	public void removeTickerElement(UUID id) throws MediaDoesNotExsistException {
		log.fine("Removing ticker element: " + id);
		checkIfElementExists(id);
		this.elements.remove(id);
	}

}
