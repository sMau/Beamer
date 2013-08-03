package de.netprojectev.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Logger;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import de.netprojectev.client.datastructures.ClientTickerElement;
import de.netprojectev.client.gui.tablemodels.TickerTableModel.UpdateTickerDataListener;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.misc.Constants;
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
	
	public void removeTickerElement(final UUID id) throws MediaDoesNotExsistException {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				log.debug("Removing ticker element: " + id);
				try {
					checkIfElementExists(id);
				} catch (MediaDoesNotExsistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				elements.remove(id);
				allElementsList.remove(id);
			}
		});

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
	
	public String completeTickerText() {
		String text = "";
		
		for(int i = 0; i < allElementsList.size(); i++) {
			if(i < allElementsList.size() - 1) {
				text += allElementsList.get(i) + Constants.DEFAULT_TICKER_SEPERATOR;
			} else {
				text += allElementsList.get(i);
			}
				
		}
		return text;
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
