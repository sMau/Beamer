package de.netprojectev.client.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import de.netprojectev.client.datastructures.MediaFileClient;
import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.common.datastructures.MediaFile;
import de.netprojectev.common.exceptions.MediaDoesNotExistException;
import de.netprojectev.common.exceptions.MediaNotInQueueException;
import de.netprojectev.common.exceptions.OutOfSyncException;
import de.netprojectev.common.exceptions.PriorityDoesNotExistException;
import de.netprojectev.common.utils.LoggerBuilder;

public class MediaModelClient {

	public interface UpdateAllMediaDataListener {
		void update();
	}

	public interface UpdateCurrentFileListener {
		void update() throws PriorityDoesNotExistException;
	}

	public interface UpdateCustomQueueDataListener {
		void update();
	}

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(MediaModelClient.class);

	private final MessageProxyClient proxy;
	private final HashMap<UUID, MediaFileClient> allMedia;
	private final ArrayList<UUID> allMediaList;
	private final LinkedList<UUID> customQueue;

	private MediaFile currentMediaFile;

	private UpdateAllMediaDataListener allMediaListener = new UpdateAllMediaDataListener() {
		@Override
		public void update() {
		}
	};
	private UpdateCustomQueueDataListener customQueueListener = new UpdateCustomQueueDataListener() {
		@Override
		public void update() {
		}
	};
	private UpdateCurrentFileListener updateCurrentFileListener = new UpdateCurrentFileListener() {
		@Override
		public void update() {
		}
	};

	public MediaModelClient(MessageProxyClient proxy) {
		this.proxy = proxy;
		this.allMedia = new HashMap<>();
		this.allMediaList = new ArrayList<>();
		this.customQueue = new LinkedList<>();
	}

	public UUID addMediaFile(MediaFileClient fileToAdd) {
		this.allMedia.put(fileToAdd.getId(), fileToAdd);

		if (!this.allMediaList.contains(fileToAdd.getId())) {
			this.allMediaList.add(fileToAdd.getId());
		}

		log.fine("Adding media file: " + fileToAdd);
		updateAllMediaTable();
		updateCustomQueueTable();
		return fileToAdd.getId();
	}

	private void checkIfMediaExists(UUID id) throws MediaDoesNotExistException {
		if (this.allMedia.get(id) == null) {
			throw new MediaDoesNotExistException("The requested media does not exist. ID: " + id);
		}
	}

	public void dequeueMediaFile(final int row, final UUID id) throws MediaDoesNotExistException, OutOfSyncException {

		try {
			checkIfMediaExists(id);
		} catch (MediaDoesNotExistException e1) {
			this.proxy.errorRequestFullSync(e1);
		}
		if (!this.customQueue.contains(id)) {
			try {
				throw new MediaNotInQueueException("Media not in private queue.");
			} catch (MediaNotInQueueException e) {
				this.proxy.errorRequestFullSync(e);
			}
		}
		if (this.customQueue.get(row).equals(id)) {
			this.customQueue.remove(row);
		} else {
			try {
				throw new OutOfSyncException("The given row does not match the UUID of media file, Out of Sync probably");
			} catch (OutOfSyncException e) {
				this.proxy.errorRequestFullSync(e);
			}
		}

		updateCustomQueueTable();
	}

	public HashMap<UUID, MediaFileClient> getAllMedia() {
		return this.allMedia;
	}

	public MediaFile getCurrentMediaFile() {
		return this.currentMediaFile;
	}

	public LinkedList<UUID> getCustomQueue() {
		return this.customQueue;
	}

	public MediaFileClient getMediaFileById(UUID id) throws MediaDoesNotExistException {
		checkIfMediaExists(id);
		return this.allMedia.get(id);
	}

	public MessageProxyClient getProxy() {
		return this.proxy;
	}

	public MediaFileClient getValueAt(int position) {
		return this.allMedia.get(this.allMediaList.get(position));
	}

	public void queueMediaFile(UUID id) throws MediaDoesNotExistException {
		checkIfMediaExists(id);
		log.fine("Queueing media file: " + id);
		this.customQueue.addLast(id);

		updateCustomQueueTable();
	}

	public void removeMediaFile(final UUID toRemove) throws MediaDoesNotExistException {

		try {
			checkIfMediaExists(toRemove);
		} catch (MediaDoesNotExistException e) {
			this.proxy.errorRequestFullSync(e);
		}
		log.fine("Removing media file: " + toRemove);

		while (this.customQueue.contains(toRemove)) {
			this.customQueue.remove(toRemove);
		}

		this.allMedia.remove(toRemove);
		this.allMediaList.remove(toRemove);

		updateAllMediaTable();
		updateCustomQueueTable();
	}

	public UUID replaceMediaFile(MediaFileClient replace) throws MediaDoesNotExistException {
		if (this.allMedia.get(replace.getId()) == null) {
			throw new MediaDoesNotExistException("The media file to replace has no mapping yet.");
		}
		return addMediaFile(replace);
	}

	public void resetShowCount(UUID toReset) throws MediaDoesNotExistException {
		getMediaFileById(toReset).resetShowCount();
		updateAllMediaTable();
		updateCustomQueueTable();
	}

	public void setAsCurrent(UUID fileShowing) throws MediaDoesNotExistException, PriorityDoesNotExistException {
		if (this.currentMediaFile != null) {
			this.currentMediaFile.setCurrent(false);
		}
		this.currentMediaFile = getMediaFileById(fileShowing).setCurrent(true).increaseShowCount();
		this.updateCurrentFileListener.update();
		updateAllMediaTable();
		updateCustomQueueTable();
	}

	public void setCustomQueueListener(UpdateCustomQueueDataListener customQueueListener) {
		this.customQueueListener = customQueueListener;
	}

	public void setListener(UpdateAllMediaDataListener listener) {
		this.allMediaListener = listener;
	}

	public void setUpdateCurrentFileListener(UpdateCurrentFileListener updateCurrentFileListener) {
		this.updateCurrentFileListener = updateCurrentFileListener;
	}

	private void updateAllMediaTable() {
		if (this.allMediaListener != null) {
			log.fine("Update All media table invoked");
			this.allMediaListener.update();
		}
	}

	private void updateCustomQueueTable() {

		if (this.customQueueListener != null) {
			this.customQueueListener.update();
		}

	}

}
