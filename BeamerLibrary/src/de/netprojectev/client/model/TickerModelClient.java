package de.netprojectev.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.datastructures.TickerElement;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.utils.LoggerBuilder;

public class TickerModelClient {
	
	public interface UpdateTickerDataListener {
		public void update();
	}
	
	
	private static final Logger log = LoggerBuilder.createLogger(TickerModelClient.class);

	private final MessageProxyClient proxy;
	private final HashMap<UUID, TickerElement> elements;
	private final ArrayList<UUID> allElementsList;

	private UpdateTickerDataListener tickerDataListener = new UpdateTickerDataListener() {
		@Override
		public void update() {
		}
	};

	public TickerModelClient(MessageProxyClient proxy) {

		this.proxy = proxy;
		this.elements = new HashMap<UUID, TickerElement>();
		this.allElementsList = new ArrayList<UUID>();
	}

	public UUID addTickerElement(TickerElement e) {
		log.debug("Adding ticker element: " + e);
		elements.put(e.getId(), e);
		if (!allElementsList.contains(e.getId())) {
			allElementsList.add(e.getId());
		}
		updateTickerTable();
		return e.getId();
	}

	public UUID replaceTickerElement(TickerElement e) throws MediaDoesNotExsistException {
		if (elements.get(e.getId()) == null) {
			throw new MediaDoesNotExsistException("The media file to replace has no mapping yet.");
		}
		return addTickerElement(e);
	}

	public void removeTickerElement(final UUID id) throws MediaDoesNotExsistException {

	/*	SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
			}
		});*/

		log.debug("Removing ticker element: " + id);
		try {
			checkIfElementExists(id);
		} catch (MediaDoesNotExsistException e) {
			proxy.errorRequestFullSync(e);
		}
		elements.remove(id);
		allElementsList.remove(id);
		updateTickerTable();
	}

	private void updateTickerTable() {

		if (tickerDataListener != null) {
			tickerDataListener.update();
		}
	}

	public TickerElement getElementByID(UUID id) throws MediaDoesNotExsistException {
		checkIfElementExists(id);
		return elements.get(id);
	}

	public TickerElement getValueAt(int row) {
		return elements.get(allElementsList.get(row));
	}

	private void checkIfElementExists(UUID id) throws MediaDoesNotExsistException {
		if (elements.get(id) == null) {
			throw new MediaDoesNotExsistException("Ticker element does not exist. Query id: " + id);
		}
	}

	public String completeTickerText() throws MediaDoesNotExsistException {
		String text = "";

		for (int i = 0; i < allElementsList.size(); i++) {
			if (getElementByID(allElementsList.get(i)).isShow()) {
				text += getElementByID(allElementsList.get(i)).getText() + proxy.getPrefs().getServerPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR);
			}
		}

		if (text.length() > proxy.getPrefs().getServerPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR).length()) {
			text = text.substring(0, text.length() - proxy.getPrefs().getServerPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR).length());
		}

		return text;
	}

	public HashMap<UUID, TickerElement> getElements() {
		return elements;
	}

	public ArrayList<UUID> getAllElementsList() {
		return allElementsList;
	}

	public void setTickerDateListener(UpdateTickerDataListener tickerDateListener) {
		this.tickerDataListener = tickerDateListener;
	}
}
