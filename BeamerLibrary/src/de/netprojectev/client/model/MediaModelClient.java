package de.netprojectev.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaNotInQueueException;
import de.netprojectev.exceptions.OutOfSyncException;
import de.netprojectev.utils.LoggerBuilder;

public class MediaModelClient {

	public interface UpdateCurrentFileListener {
		public void update();
	}
	
	public interface UpdateAllMediaDataListener {
		public void update();
	}
	
	public interface UpdateCustomQueueDataListener {
		public void update();
	}

	private static final Logger log = LoggerBuilder.createLogger(MediaModelClient.class);
	
	private final ClientMessageProxy proxy;
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
	
	public MediaModelClient(ClientMessageProxy proxy) {
		this.proxy = proxy;
		this.allMedia = new HashMap<UUID, ClientMediaFile>();
		this.allMediaList = new ArrayList<UUID>();
		this.customQueue = new LinkedList<UUID>();
	}
	
	
	public UUID addMediaFile(ClientMediaFile fileToAdd) {
		allMedia.put(fileToAdd.getId(), fileToAdd);

		if(!allMediaList.contains(fileToAdd.getId())) {
			allMediaList.add(fileToAdd.getId());
		}
		
		log.debug("Adding media file: " + fileToAdd);
		updateAllMediaTable();
		updateCustomQueueTable();
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
	
	public void removeMediaFile(final UUID toRemove) throws MediaDoesNotExsistException {
		
		/*SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
			
				
			}
		});*/

		try {
			checkIfMediaExists(toRemove);
		} catch (MediaDoesNotExsistException e) {
			proxy.errorRequestFullSync(e);
		}
		log.debug("Removing media file: " + toRemove);
		
		while(customQueue.contains(toRemove)) {
			customQueue.remove(toRemove);
		}
		
		allMedia.remove(toRemove);
		allMediaList.remove(toRemove);
		
		updateAllMediaTable();
		updateCustomQueueTable();
	}
	
	public ClientMediaFile getMediaFileById(UUID id) throws MediaDoesNotExsistException {
		checkIfMediaExists(id);
		return allMedia.get(id);
	}
	
	public void queueMediaFile(UUID id) throws MediaDoesNotExsistException {
		checkIfMediaExists(id);
		log.debug("Queueing media file: " + id);
		customQueue.addLast(id);
		
		updateCustomQueueTable();
	}

	public void dequeueMediaFile(final int row, final UUID id) throws MediaDoesNotExsistException, OutOfSyncException {
		
	/*	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
		*/
		try {
			checkIfMediaExists(id);
		} catch (MediaDoesNotExsistException e1) {
			proxy.errorRequestFullSync(e1);
		}
		if(!customQueue.contains(id)) {
			try {
				throw new MediaNotInQueueException("Media not in private queue.");
			} catch (MediaNotInQueueException e) {
				proxy.errorRequestFullSync(e);
			}
		}
		if(customQueue.get(row).equals(id)) {
			customQueue.remove(row);
		} else {
			try {
				throw new OutOfSyncException("The given row doesnt match the UUID of media file, Out of Sync propably");
			} catch (OutOfSyncException e) {
				proxy.errorRequestFullSync(e);
			}
		}
		
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


	public void setAsCurrent(UUID fileShowing) throws MediaDoesNotExsistException {
		if(currentMediaFile != null) {
			currentMediaFile.setCurrent(false);
		}
		currentMediaFile = getMediaFileById(fileShowing).setCurrent(true).increaseShowCount();
		updateCurrentFileListener.update();
		updateAllMediaTable();
		updateCustomQueueTable();
	}


	public MediaFile getCurrentMediaFile() { 
		return currentMediaFile;
	}


	public void setUpdateCurrentFileListener(UpdateCurrentFileListener updateCurrentFileListener) {
		this.updateCurrentFileListener = updateCurrentFileListener;
	}


	public void resetShowCount(UUID toReset) throws MediaDoesNotExsistException {
		getMediaFileById(toReset).resetShowCount();
		updateAllMediaTable();
		updateCustomQueueTable();
	}

	public int timeUntilShow(int rowIndex) throws MediaDoesNotExsistException {
		//TODO take current file time into account
		int res = 0;
		for(int i = 0; i < rowIndex; i++) {
			res += getMediaFileById(customQueue.get(i)).getPriority().getMinutesToShow();
		}
		return res;
	}


	public ClientMessageProxy getProxy() {
		return proxy;
	}
	
}
 
