package de.netprojectev.server.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.exceptions.MediaNotInQueueException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.networking.MessageProxyServer;

public class MediaModelServer {
		
	private static final Logger log = LoggerBuilder.createLogger(MediaModelServer.class);
	
	private final MessageProxyServer proxy;
	private final HashMap<UUID, ServerMediaFile> allMediaFiles;
	private final LinkedList<UUID> mediaStandardList;
	private final LinkedList<UUID> mediaPrivateQueue;
	private final Random rand;
	
	public MediaModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		allMediaFiles = new HashMap<>();
		mediaStandardList = new LinkedList<>();
		mediaPrivateQueue = new LinkedList<>();
		rand = new Random();
		log.debug("Media model successfully created");
	}
	
	public UUID addMediaFile(ServerMediaFile file) {
		allMediaFiles.put(file.getId(), file);
		addMediaFileAtRandomPosition(file);
		log.debug("Media file added: " + file);
		return file.getId();
	}

	private void addMediaFileAtRandomPosition(ServerMediaFile file) {
		if(!mediaStandardList.contains(file.getId())) {
			int insertionIndex = rand.nextInt(mediaStandardList.size() + 1);
			if(insertionIndex == mediaStandardList.size()) {
				mediaStandardList.addLast(file.getId());
			} else {
				mediaStandardList.add(insertionIndex, file.getId());
			}
		}
	}
	
	public ServerMediaFile getMediaFileById(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		log.debug("Getting media file: " + id);
		return allMediaFiles.get(id);
	}
	
	public void removeMediaFile(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		log.debug("Removing media file: " + id);
		ServerMediaFile removedFile = allMediaFiles.remove(id);
		
		while(mediaPrivateQueue.contains(id)) {
			mediaPrivateQueue.remove(id);
		}
		while(mediaStandardList.contains(id)) {
			mediaStandardList.remove(id);
		}
		
		cleanUpAfterRemove(removedFile);

	}
	
	private void cleanUpAfterRemove(ServerMediaFile removedFile) {
		if(removedFile instanceof VideoFile) {
			if(((VideoFile) removedFile).getVideoFile().exists()) {
				if(removedFile.isCurrent()) {
					((VideoFile) removedFile).getVideoFile().deleteOnExit();
				} else {
					((VideoFile) removedFile).getVideoFile().delete();
				}
			}
		}
	}

	public void queue(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		log.debug("Queuing media file: " + id);
		mediaPrivateQueue.addLast(id);
	}
	
	public void dequeue(UUID id, int row) throws MediaDoesNotExsistException, OutOfSyncException {
		testIfMediaFileExists(id);
		if(!mediaPrivateQueue.contains(id)) {
			throw new MediaNotInQueueException("Media not in private queue.");
		}
		
		if(mediaPrivateQueue.get(row).equals(id)) {
			mediaPrivateQueue.remove(row);
		} else {
			throw new OutOfSyncException("The given row doesnt match the UUID of media file, Out of Sync propably");
		}
		
	}
	
	public ServerMediaFile getNext() throws MediaDoesNotExsistException, MediaListsEmptyException {
		if(allMediaFiles.isEmpty()) {
			throw new MediaListsEmptyException("No media files present.");
		}
		log.debug("Getting next media file");
		if(!mediaPrivateQueue.isEmpty()) {
			return getMediaFileById(mediaPrivateQueue.poll());
		} else {
			if(mediaStandardList.isEmpty()) {
				addAllMediaAndShuffle();
			}
			return getMediaFileById(mediaStandardList.poll());
		}
	}

	private void addAllMediaAndShuffle() {
		mediaStandardList.addAll(allMediaFiles.keySet());
		Collections.shuffle(mediaStandardList);
	}
	
	private void testIfMediaFileExists(UUID id) throws MediaDoesNotExsistException {
		if(allMediaFiles.get(id) == null) {
			throw new MediaDoesNotExsistException("The requested media file does not exist. Query id: " + id);
		}
	}

	public HashMap<UUID, ServerMediaFile> getAllMediaFiles() {
		return allMediaFiles;
	}

	public LinkedList<UUID> getMediaPrivateQueue() {
		return mediaPrivateQueue;
	}

	public void resetShowCount(UUID toReset) throws MediaDoesNotExsistException {
		getMediaFileById(toReset).resetShowCount();
	}
	
}
