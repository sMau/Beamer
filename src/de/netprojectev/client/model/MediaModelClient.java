package de.netprojectev.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.misc.LoggerBuilder;

public class MediaModelClient {

	private static final Logger log = LoggerBuilder.createLogger(MediaModelClient.class);
	
	private final ClientMessageProxy proxy;
	private final HashMap<UUID, ClientMediaFile> allMedia;
	private final ArrayList<UUID> allMediaList;
	private final LinkedList<UUID> customQueue;
	
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
		return fileToAdd.getId();
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
	}
	
	public void dequeueFirstMediaFile() {
		if(!customQueue.isEmpty()) {
			log.debug("Dequeueing first.");
			customQueue.removeFirst();
		}
	}
	
	private void checkIfMediaExists(UUID id) throws MediaDoesNotExsistException {
		if(allMedia.get(id) == null) {
			throw new MediaDoesNotExsistException("The requested media does not exist. ID: " + id);
		}
	}
	
	public ClientMediaFile getValueAt(int position) {
		
		//TODO last worked here, last made the tablemodel for all media of the client work basically (see the first test to the gui)
		/*
		 * next to do: do the same for the liveticker and the customqueue
		 * maybe add already some buttons to test the ability of adding new files via the server
		 * 
		 */
		
		return allMedia.get(allMediaList.get(position));
	}


	public HashMap<UUID, ClientMediaFile> getAllMedia() {
		return allMedia;
	}


	public LinkedList<UUID> getCustomQueue() {
		return customQueue;
	}
	
	
}
