package de.netprojectev.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.client.gui.tablemodels.TickerTableModel.UpdateTickerDataListener;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.misc.LoggerBuilder;

public class TickerModelClient {
	private static final Logger log = LoggerBuilder.createLogger(TickerModelClient.class);

	private final ClientMessageProxy proxy;
	private final HashMap<UUID, ClientTickerElement> elements;
	private final ArrayList<UUID> allElementsList;
	
	private UpdateTickerDataListener tickerDataListener;
	
	public TickerModelClient(ClientMessageProxy proxy) {
		this.proxy = proxy;
		this.elements = new HashMap<>();
		this.allElementsList = new ArrayList<>();
	}
	
	public UUID addTickerElement(ClientTickerElement e) {
		log.debug("Adding ticker element: " + e);
		elements.put(e.getId(), e);
		if(!allElementsList.contains(e.getId())) {
			allElementsList.add(e.getId());
		}
		updateTickerTable();
		return e.getId();
	}
	
	public UUID replaceTickerElement(ClientTickerElement e) throws MediaDoesNotExsistException {
		if(elements.get(e.getId()) == null) {
			throw new MediaDoesNotExsistException("The media file to replace has no mapping yet.");
		}
		return addTickerElement(e);
	}
	
	public void removeTickerElement(UUID id) throws MediaDoesNotExsistException {
		log.debug("Removing ticker element: " + id);
		checkIfElementExists(id);
		elements.remove(id);
		allElementsList.remove(id);
		
		updateTickerTable();
	}

	private void updateTickerTable() {
		
		if(tickerDataListener != null) {
			tickerDataListener.update();
		}
	}
	
	public ClientTickerElement getElementByID(UUID id) throws MediaDoesNotExsistException {
		checkIfElementExists(id);
		log.debug("Getting ticker element: " + id);
		return elements.get(id);
	}
	
	public ClientTickerElement getValueAt(int row) {
		return elements.get(allElementsList.get(row));
	}
	
	private void checkIfElementExists(UUID id) throws MediaDoesNotExsistException {
		if(elements.get(id) == null) {
			throw new MediaDoesNotExsistException("Ticker element does not exist. Query id: " + id);
		}
	}

	public HashMap<UUID, ClientTickerElement> getElements() {
		return elements;
	}

	public ArrayList<UUID> getAllElementsList() {
		return allElementsList;
	}

	public void setTickerDateListener(UpdateTickerDataListener tickerDateListener) {
		this.tickerDataListener = tickerDateListener;
	}
}
