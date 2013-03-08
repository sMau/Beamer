package de.netprojectev.server.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import de.netprojectev.server.datastructures.media.ServerMediaFile;
import de.netprojectev.server.networking.MessageProxyServer;

public class MediaModelServer {
	
	private final MessageProxyServer proxy;
	private HashMap<UUID, ServerMediaFile> allMediaFiles;
	private LinkedList<UUID> mediaStandardList;
	private LinkedList<UUID> mediaPrivateQueue;
	private ServerMediaFile currentFile;
	private Random rand;
	
	public MediaModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		allMediaFiles = new HashMap<>();
		mediaStandardList = new LinkedList<>();
		mediaPrivateQueue = new LinkedList<>();
		rand = new Random();
		
	}
	
	public UUID addMediaFile(ServerMediaFile file) {
		allMediaFiles.put(file.getId(), file);
		addMediaFileAtRandomPosition(file);
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
		return allMediaFiles.get(id);
	}
	
	public void removeMediaFile(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		allMediaFiles.remove(id);
		mediaPrivateQueue.remove(id);
		mediaStandardList.remove(id);
	}
	
	public void queue(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		mediaPrivateQueue.addLast(id);
	}
	
	public ServerMediaFile getNext() throws MediaDoesNotExsistException, MediaListsEmptyException {
		if(allMediaFiles.isEmpty()) {
			throw new MediaListsEmptyException("No media files present.");
		}
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
	
}
