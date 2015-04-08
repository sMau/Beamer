package de.netprojectev.server.datamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import de.netprojectev.common.exceptions.MediaDoesNotExistException;
import de.netprojectev.common.exceptions.MediaListsEmptyException;
import de.netprojectev.common.exceptions.MediaNotInQueueException;
import de.netprojectev.common.exceptions.OutOfSyncException;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.MediaFileServer;
import de.netprojectev.server.datastructures.VideoFileServer;
import de.netprojectev.server.networking.MessageProxyServer;
import de.netprojectev.common.utils.LoggerBuilder;

public class MediaModelServer {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(MediaModelServer.class);

	private final MessageProxyServer proxy;
	private final HashMap<UUID, MediaFileServer> allMediaFiles;
	private final LinkedList<UUID> mediaStandardList;
	private final LinkedList<UUID> mediaPrivateQueue;
	private final Random rand;

	public MediaModelServer(MessageProxyServer proxy) {
		this.proxy = proxy;
		this.allMediaFiles = new HashMap<UUID, MediaFileServer>();
		this.mediaStandardList = new LinkedList<UUID>();
		this.mediaPrivateQueue = new LinkedList<UUID>();
		this.rand = new Random();
		log.fine("Media datamodel successfully created");
	}

	private void addAllMediaAndShuffle() {
		Set<UUID> cleanedSet = new HashSet<UUID>();

		for (UUID id : this.allMediaFiles.keySet()) {
			if (!(this.allMediaFiles.get(id) instanceof Countdown)) {
				cleanedSet.add(id);
			}
		}

		this.mediaStandardList.addAll(cleanedSet);
		Collections.shuffle(this.mediaStandardList);

	}

	public UUID addMediaFile(MediaFileServer file) {
		this.allMediaFiles.put(file.getId(), file);
		addMediaFileAtRandomPosition(file);
		log.fine("Media file added: " + file);
		return file.getId();
	}

	private void addMediaFileAtRandomPosition(MediaFileServer file) {
		if (!this.mediaStandardList.contains(file.getId())) {
			int insertionIndex = this.rand.nextInt(this.mediaStandardList.size() + 1);
			if (insertionIndex == this.mediaStandardList.size()) {
				this.mediaStandardList.addLast(file.getId());
			} else {
				this.mediaStandardList.add(insertionIndex, file.getId());
			}
		}
	}

	private void cleanUpAfterRemove(MediaFileServer removedFile) {
		if (removedFile instanceof VideoFileServer) {
			if (((VideoFileServer) removedFile).getVideoFile().exists()) {
				if (removedFile.isCurrent()) {
					((VideoFileServer) removedFile).getVideoFile().deleteOnExit();
				} else {
					((VideoFileServer) removedFile).getVideoFile().delete();
				}
			}
		}
	}

	public void dequeue(UUID id, int row) throws MediaDoesNotExistException, OutOfSyncException {
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

	public HashMap<UUID, MediaFileServer> getAllMediaFiles() {
		return this.allMediaFiles;
	}

	public MediaFileServer getMediaFileById(UUID id) throws MediaDoesNotExistException {
		testIfMediaFileExists(id);
		log.fine("Getting media file: " + id);
		return this.allMediaFiles.get(id);
	}

	public LinkedList<UUID> getMediaPrivateQueue() {
		return this.mediaPrivateQueue;
	}

	public MediaFileServer getNext() throws MediaDoesNotExistException, MediaListsEmptyException {
		if (this.allMediaFiles.isEmpty()) {
			throw new MediaListsEmptyException("No media files present.");
		}
		log.fine("Getting next media file");
		if (!this.mediaPrivateQueue.isEmpty()) {
			return getMediaFileById(this.mediaPrivateQueue.poll());
		} else {
			if (this.mediaStandardList.isEmpty()) {
				addAllMediaAndShuffle();
			}
			return getMediaFileById(this.mediaStandardList.poll());
		}
	}

	public void queue(UUID id) throws MediaDoesNotExistException {
		testIfMediaFileExists(id);
		log.fine("Queuing media file: " + id);
		this.mediaPrivateQueue.addLast(id);
	}

	public void removeMediaFile(UUID id) throws MediaDoesNotExistException {
		testIfMediaFileExists(id);
		log.fine("Removing media file: " + id);
		MediaFileServer removedFile = this.allMediaFiles.remove(id);

		while (this.mediaPrivateQueue.contains(id)) {
			this.mediaPrivateQueue.remove(id);
		}
		while (this.mediaStandardList.contains(id)) {
			this.mediaStandardList.remove(id);
		}

		cleanUpAfterRemove(removedFile);

	}

	public void resetShowCount(UUID toReset) throws MediaDoesNotExistException {
		getMediaFileById(toReset).resetShowCount();
	}

	private void testIfMediaFileExists(UUID id) throws MediaDoesNotExistException {
		if (this.allMediaFiles.get(id) == null) {
			throw new MediaDoesNotExistException("The requested media file does not exist. Query id: " + id);
		}
	}

}
