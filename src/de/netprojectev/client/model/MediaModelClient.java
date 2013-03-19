package de.netprojectev.client.model;

import java.util.HashMap;
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
	private final LinkedList<UUID> customQueue;
	
	public MediaModelClient(ClientMessageProxy proxy) {
		this.proxy = proxy;
		this.allMedia = new HashMap<>();
		this.customQueue = new LinkedList<>();
	}
	
	
	public UUID addMediaFile(ClientMediaFile fileToAdd) {
		allMedia.put(fileToAdd.getId(), fileToAdd);
		log.debug("Adding media file: " + fileToAdd);
		return fileToAdd.getId();
	}
	
	public void removeMediaFile(UUID toRemove) throws MediaDoesNotExsistException {
		checkIfMediaExists(toRemove);
		log.debug("Removing media file: " + toRemove);
		
		while(customQueue.contains(toRemove)) {
			customQueue.remove(toRemove);
		}
		
		allMedia.remove(toRemove);
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


	public HashMap<UUID, ClientMediaFile> getAllMedia() {
		return allMedia;
	}


	public LinkedList<UUID> getCustomQueue() {
		return customQueue;
	}
	
	
}
