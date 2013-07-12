package de.netprojectev.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.gui.tablemodels.AllMediaTableModel.UpdateAllMediaDataListener;
import de.netprojectev.client.gui.tablemodels.CustomQueueTableModel.UpdateCustomQueueDataListener;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.misc.LoggerBuilder;

public class MediaModelClient {


	private static final Logger log = LoggerBuilder.createLogger(MediaModelClient.class);
	
	private final ClientMessageProxy proxy;
	private final HashMap<UUID, ClientMediaFile> allMedia;
	private final ArrayList<UUID> allMediaList;
	private final LinkedList<UUID> customQueue;
	
	private UpdateAllMediaDataListener allMediaListener;
	private UpdateCustomQueueDataListener customQueueListener;
	
	public MediaModelClient(ClientMessageProxy proxy) {
		this.proxy = proxy;
		this.allMedia = new HashMap<>();
		this.allMediaList = new ArrayList<>();
		this.customQueue = new LinkedList<>();
	}
	
	
	public UUID addMediaFile(ClientMediaFile fileToAdd) {
		allMedia.put(fileToAdd.getId(), fileToAdd);
		
		if(!allMediaList.contains(fileToAdd.getId())) {
			allMediaList.add(fileToAdd.getId());
		}
		
		log.debug("Adding media file: " + fileToAdd);
		updateAllMediaTable();
		return fileToAdd.getId();
	}


	private void updateAllMediaTable() {
		if(allMediaListener != null) {
			log.debug("Update All media table invoked");
			allMediaListener.update();
		}
	}
	
	public UUID replaceMediaFile(ClientMediaFile replace) throws MediaDoesNotExsistException {
		if(allMedia.get(replace.getId()) == null) {
			throw new MediaDoesNotExsistException("The media file to replace has no mapping yet.");
		}
		return addMediaFile(replace);
	}
	
	public void removeMediaFile(UUID toRemove) throws MediaDoesNotExsistException {
		checkIfMediaExists(toRemove);
		log.debug("Removing media file: " + toRemove);
		
		while(customQueue.contains(toRemove)) {
			customQueue.remove(toRemove);
		}
		
		allMedia.remove(toRemove);
		allMediaList.remove(toRemove);
		
		updateAllMediaTable();
	}
	
	public ClientMediaFile getMediaFileById(UUID id) throws MediaDoesNotExsistException {
		checkIfMediaExists(id);
		log.debug("Getting media file: " + id);
		return allMedia.get(id);
	}
	
	public void queueMediaFile(UUID id) throws MediaDoesNotExsistException {
		checkIfMediaExists(id);
		log.debug("Queueing media file: " + id);
		customQueue.addLast(id);
		
		updateCustomQueueTable();
	}


	private void updateCustomQueueTable() {
		
		if(customQueueListener != null) {
			customQueueListener.update();
		}
		
	}
	
	public void dequeueFirstMediaFile() {
		if(!customQueue.isEmpty()) {
			log.debug("Dequeueing first.");
			customQueue.removeFirst();
			
			updateCustomQueueTable();
		}
	}
	
	private void checkIfMediaExists(UUID id) throws MediaDoesNotExsistException {
		if(allMedia.get(id) == null) {
			throw new MediaDoesNotExsistException("The requested media does not exist. ID: " + id);
		}
	}
	
	public ClientMediaFile getValueAt(int position) {
		return allMedia.get(allMediaList.get(position));
	}


	public HashMap<UUID, ClientMediaFile> getAllMedia() {
		return allMedia;
	}


	public LinkedList<UUID> getCustomQueue() {
		return customQueue;
	}


	public void setListener(UpdateAllMediaDataListener listener) {
		this.allMediaListener = listener;
	}


	public void setCustomQueueListener(UpdateCustomQueueDataListener customQueueListener) {
		this.customQueueListener = customQueueListener;
	}

}
 
