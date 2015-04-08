package de.netprojectev.client.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.common.datastructures.TickerElement;
import de.netprojectev.common.exceptions.MediaDoesNotExsistException;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.common.utils.LoggerBuilder;

public class TickerModelClient {

	public interface UpdateTickerDataListener {
		public void update();
	}

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(TickerModelClient.class);

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
		log.fine("Adding ticker element: " + e);
		this.elements.put(e.getId(), e);
		if (!this.allElementsList.contains(e.getId())) {
			this.allElementsList.add(e.getId());
		}
		updateTickerTable();
		return e.getId();
	}

	private void checkIfElementExists(UUID id) throws MediaDoesNotExsistException {
		if (this.elements.get(id) == null) {
			throw new MediaDoesNotExsistException("Ticker element does not exist. Query id: " + id);
		}
	}

	public String completeTickerText() throws MediaDoesNotExsistException {
		String text = "";

		for (int i = 0; i < this.allElementsList.size(); i++) {
			if (getElementByID(this.allElementsList.get(i)).isShow()) {
				text += getElementByID(this.allElementsList.get(i)).getText() + this.proxy.getPrefs().getServerPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR);
			}
		}

		if (text.length() > this.proxy.getPrefs().getServerPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR).length()) {
			text = text.substring(0, text.length() - this.proxy.getPrefs().getServerPropertyByKey(ConstantsServer.PROP_TICKER_SEPERATOR).length());
		}

		return text;
	}

	public ArrayList<UUID> getAllElementsList() {
		return this.allElementsList;
	}

	public TickerElement getElementByID(UUID id) throws MediaDoesNotExsistException {
		checkIfElementExists(id);
		return this.elements.get(id);
	}

	public HashMap<UUID, TickerElement> getElements() {
		return this.elements;
	}

	public TickerElement getValueAt(int row) {
		return this.elements.get(this.allElementsList.get(row));
	}

	public void removeTickerElement(final UUID id) throws MediaDoesNotExsistException {

		/*
		 * SwingUtilities.invokeLater(new Runnable() {
		 *
		 * @Override public void run() {
		 *
		 * } });
		 */

		log.fine("Removing ticker element: " + id);
		try {
			checkIfElementExists(id);
		} catch (MediaDoesNotExsistException e) {
			this.proxy.errorRequestFullSync(e);
		}
		this.elements.remove(id);
		this.allElementsList.remove(id);
		updateTickerTable();
	}

	public UUID replaceTickerElement(TickerElement e) throws MediaDoesNotExsistException {
		if (this.elements.get(e.getId()) == null) {
			throw new MediaDoesNotExsistException("The media file to replace has no mapping yet.");
		}
		return addTickerElement(e);
	}

	public void setTickerDateListener(UpdateTickerDataListener tickerDateListener) {
		this.tickerDataListener = tickerDateListener;
	}

	private void updateTickerTable() {

		if (this.tickerDataListener != null) {
			this.tickerDataListener.update();
		}
	}
}
