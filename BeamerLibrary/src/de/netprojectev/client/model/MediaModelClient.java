package de.netprojectev.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.networking.MessageProxyClient;
import de.netprojectev.datastructures.MediaFile;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaNotInQueueException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.utils.LoggerBuilder;

public class MediaModelClient {

	public interface UpdateAllMediaDataListener {
		public void update();
	}

	public interface UpdateCurrentFileListener {
		public void update() throws PriorityDoesNotExistException;
	}

	public interface UpdateCustomQueueDataListener {
		public void update();
	}

	private static final Logger log = LoggerBuilder.createLogger(MediaModelClient.class);

	private final MessageProxyClient proxy;
	private final HashMap<UUID, ClientMediaFile> allMedia;
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
		this.allMedia = new HashMap<UUID, ClientMediaFile>();
		this.allMediaList = new ArrayList<UUID>();
		this.customQueue = new LinkedList<UUID>();
	}

	public UUID addMediaFile(ClientMediaFile fileToAdd) {
		this.allMedia.put(fileToAdd.getId(), fileToAdd);

		if (!this.allMediaList.contains(fileToAdd.getId())) {
			this.allMediaList.add(fileToAdd.getId());
		}

		log.debug("Adding media file: " + fileToAdd);
		updateAllMediaTable();
		updateCustomQueueTable();
		return fileToAdd.getId();
	}

	private void checkIfMediaExists(UUID id) throws MediaDoesNotExsistException {
		if (this.allMedia.get(id) == null) {
			throw new MediaDoesNotExsistException("The requested media does not exist. ID: " + id);
		}
	}

	public void dequeueFirstMediaFile() {
		if (!this.customQueue.isEmpty()) {
			log.debug("Dequeueing first.");
			this.customQueue.removeFirst();

			updateCustomQueueTable();
		}
	}

	public void dequeueMediaFile(final int row, final UUID id) throws MediaDoesNotExsistException, OutOfSyncException {

		/*
		 * SwingUtilities.invokeLater(new Runnable() {
		 *
		 * @Override public void run() {
		 *
		 * } });
		 */
		try {
			checkIfMediaExists(id);
		} catch (MediaDoesNotExsistException e1) {
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
				throw new OutOfSyncException("The given row doesnt match the UUID of media file, Out of Sync propably");
			} catch (OutOfSyncException e) {
				this.proxy.errorRequestFullSync(e);
			}
		}

		updateCustomQueueTable();
	}

	public HashMap<UUID, ClientMediaFile> getAllMedia() {
		return this.allMedia;
	}

	public MediaFile getCurrentMediaFile() {
		return this.currentMediaFile;
	}

	public LinkedList<UUID> getCustomQueue() {
		return this.customQueue;
	}

	public ClientMediaFile getMediaFileById(UUID id) throws MediaDoesNotExsistException {
		checkIfMediaExists(id);
		return this.allMedia.get(id);
	}

	public MessageProxyClient getProxy() {
		return this.proxy;
	}

	public ClientMediaFile getValueAt(int position) {
		return this.allMedia.get(this.allMediaList.get(position));
	}

	public void queueMediaFile(UUID id) throws MediaDoesNotExsistException {
		checkIfMediaExists(id);
		log.debug("Queueing media file: " + id);
		this.customQueue.addLast(id);

		updateCustomQueueTable();
	}

	public void removeMediaFile(final UUID toRemove) throws MediaDoesNotExsistException {

		try {
			checkIfMediaExists(toRemove);
		} catch (MediaDoesNotExsistException e) {
			this.proxy.errorRequestFullSync(e);
		}
		log.debug("Removing media file: " + toRemove);

		while (this.customQueue.contains(toRemove)) {
			this.customQueue.remove(toRemove);
		}

		this.allMedia.remove(toRemove);
		this.allMediaList.remove(toRemove);

		updateAllMediaTable();
		updateCustomQueueTable();
	}

	public UUID replaceMediaFile(ClientMediaFile replace) throws MediaDoesNotExsistException {
		if (this.allMedia.get(replace.getId()) == null) {
			throw new MediaDoesNotExsistException("The media file to replace has no mapping yet.");
		}
		return addMediaFile(replace);
	}

	public void resetShowCount(UUID toReset) throws MediaDoesNotExsistException {
		getMediaFileById(toReset).resetShowCount();
		updateAllMediaTable();
		updateCustomQueueTable();
	}

	public void setAsCurrent(UUID fileShowing) throws MediaDoesNotExsistException, PriorityDoesNotExistException {
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

	public int timeUntilShow(int rowIndex) throws MediaDoesNotExsistException, PriorityDoesNotExistException {
		// XXX take current file time into account
		int res = 0;
		for (int i = 0; i < rowIndex; i++) {
			res += this.proxy.getPrefs().getPriorityByID(getMediaFileById(this.customQueue.get(i)).getPriorityID()).getMinutesToShow();
		}
		return res;
	}

	private void updateAllMediaTable() {
		if (this.allMediaListener != null) {
			log.debug("Update All media table invoked");
			this.allMediaListener.update();
		}
	}

	private void updateCustomQueueTable() {

		if (this.customQueueListener != null) {
			this.customQueueListener.update();
		}

	}

}
