package de.netprojectev.server.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.exceptions.MediaNotInQueueException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.utils.LoggerBuilder;

public class MediaModelServer {

	private static final Logger log = LoggerBuilder.createLogger(MediaModelServer.class);

	private final MessageProxyServer proxy;
	private final HashMap<UUID, ServerMediaFile> allMediaFiles;
	private final LinkedList<UUID> mediaStandardList;
	private final LinkedList<UUID> mediaPrivateQueue;
	private final Random rand;

	public MediaModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		this.allMediaFiles = new HashMap<UUID, ServerMediaFile>();
		this.mediaStandardList = new LinkedList<UUID>();
		this.mediaPrivateQueue = new LinkedList<UUID>();
		this.rand = new Random();
		log.debug("Media model successfully created");
	}

	private void addAllMediaAndShuffle() {
		Set<UUID> cleanedSet = new HashSet<UUID>();
		
		for(UUID id : this.allMediaFiles.keySet()) {
			if(!(this.allMediaFiles.get(id) instanceof Countdown)) {
				cleanedSet.add(id);
			}
		}
		
		this.mediaStandardList.addAll(cleanedSet);
		Collections.shuffle(this.mediaStandardList);

	}

	public UUID addMediaFile(ServerMediaFile file) {
		this.allMediaFiles.put(file.getId(), file);
		addMediaFileAtRandomPosition(file);
		log.debug("Media file added: " + file);
		return file.getId();
	}

	private void addMediaFileAtRandomPosition(ServerMediaFile file) {
		if (!this.mediaStandardList.contains(file.getId())) {
			int insertionIndex = this.rand.nextInt(this.mediaStandardList.size() + 1);
			if (insertionIndex == this.mediaStandardList.size()) {
				this.mediaStandardList.addLast(file.getId());
			} else {
				this.mediaStandardList.add(insertionIndex, file.getId());
			}
		}
	}

	private void cleanUpAfterRemove(ServerMediaFile removedFile) {
		if (removedFile instanceof VideoFile) {
			if (((VideoFile) removedFile).getVideoFile().exists()) {
				if (removedFile.isCurrent()) {
					((VideoFile) removedFile).getVideoFile().deleteOnExit();
				} else {
					((VideoFile) removedFile).getVideoFile().delete();
				}
			}
		}
	}

	public void dequeue(UUID id, int row) throws MediaDoesNotExsistException, OutOfSyncException {
		testIfMediaFileExists(id);
		if (!this.mediaPrivateQueue.contains(id)) {
			throw new MediaNotInQueueException("Media not in private queue.");
		}

		if (this.mediaPrivateQueue.get(row).equals(id)) {
			this.mediaPrivateQueue.remove(row);
		} else {
			throw new OutOfSyncException("The given row doesnt match the UUID of media file, Out of Sync proably");
		}

	}

	public HashMap<UUID, ServerMediaFile> getAllMediaFiles() {
		return this.allMediaFiles;
	}

	public ServerMediaFile getMediaFileById(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		log.debug("Getting media file: " + id);
		return this.allMediaFiles.get(id);
	}

	public LinkedList<UUID> getMediaPrivateQueue() {
		return this.mediaPrivateQueue;
	}

	public ServerMediaFile getNext() throws MediaDoesNotExsistException, MediaListsEmptyException {
		if (this.allMediaFiles.isEmpty()) {
			throw new MediaListsEmptyException("No media files present.");
		}
		log.debug("Getting next media file");
		if (!this.mediaPrivateQueue.isEmpty()) {
			return getMediaFileById(this.mediaPrivateQueue.poll());
		} else {
			if (this.mediaStandardList.isEmpty()) {
				addAllMediaAndShuffle();
			}
			return getMediaFileById(this.mediaStandardList.poll());
		}
	}

	public void queue(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		log.debug("Queuing media file: " + id);
		this.mediaPrivateQueue.addLast(id);
	}

	public void removeMediaFile(UUID id) throws MediaDoesNotExsistException {
		testIfMediaFileExists(id);
		log.debug("Removing media file: " + id);
		ServerMediaFile removedFile = this.allMediaFiles.remove(id);

		while (this.mediaPrivateQueue.contains(id)) {
			this.mediaPrivateQueue.remove(id);
		}
		while (this.mediaStandardList.contains(id)) {
			this.mediaStandardList.remove(id);
		}

		cleanUpAfterRemove(removedFile);

	}

	public void resetShowCount(UUID toReset) throws MediaDoesNotExsistException {
		getMediaFileById(toReset).resetShowCount();
	}

	private void testIfMediaFileExists(UUID id) throws MediaDoesNotExsistException {
		if (this.allMediaFiles.get(id) == null) {
			throw new MediaDoesNotExsistException("The requested media file does not exist. Query id: " + id);
		}
	}

}
