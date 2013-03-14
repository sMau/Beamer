package de.netprojectev.old;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.netprojectev.client.gui.manager.FileManagerTableModel;
import de.netprojectev.client.gui.manager.ManagerFrame;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.gui.DisplayMainComponent;

/**
 * Handles all media files currently loaded.
 * It synchs itself with the GUI and updates the display handler if necessary.
 * @author samu
 *
 */
public class MediaHandlerOld {
	
	private static final Logger log = Misc.getLoggerAll(MediaHandlerOld.class.getName());
	
	private final DisplayMediaModelOld displayHandler;	
	private LinkedList<ServerMediaFile> mediaFiles;
	private ManagerFrame managerFrame;
	private int countdownCounter;

	
	private MediaHandlerOld(DisplayMediaModelOld displayMediaModel) {
		
		countdownCounter = 0;
		mediaFiles = new LinkedList<ServerMediaFile>();
		this.displayHandler = displayMediaModel;
		displayHandler.setMediaHandler(this);

	}


	/**
	 * Adds the media files to list and if necessary invokes updates on the GUI and the display handler.
	 * @param files files to add
	 */
	public void add(ServerMediaFile[] files) {

		for (int i = 0; i < files.length; i++) {

			if (!mediaFiles.contains(files[i])) {
				log.log(Level.INFO, "media file added " + files[i].getName());
				mediaFiles.add(files[i]);
				if(files[i] instanceof Countdown) {
					countdownCounter++;
				}
			} else {
				log.log(Level.INFO, "media file already in list " + files[i].getName());
			}

		}
		
		refreshDataModel();
		displayHandler.add(files);

	}

	
	/**
	 * removes the media files from the list and if necessary invokes updates on the GUI and the display handler.	 
	 * Prevents current file from being removed from the list, to avoid problems with synching to the {@link DisplayMediaModelOld}.
	 * Furthermore it cleans up the themeslide cache. JPGs not needed anymore are deleted from the cache.
	 * @param files files to remove
	 * @param removeCurrent true if the current element should also be removed if it is in the selection, false if not
	 */
	public void remove(ServerMediaFile[] files, boolean removeCurrent) {

		boolean containsCurrentElt = false;
		ServerMediaFile[] filesToRemove = files; 
		
		log.log(Level.INFO, "removing files, removing current: " + removeCurrent);
		
		if(!removeCurrent) {
			for (int i = 0; i < files.length; i++) {
				if(files[i].getStatus().getIsCurrent()) {
					containsCurrentElt = true;
				}
			}
			
			if(containsCurrentElt) {
				int counter = 0;
				filesToRemove = new ServerMediaFile[files.length - 1];
				for(int i = 0; i < files.length; i++) {
					if(!files[i].getStatus().getIsCurrent()) {
						filesToRemove[counter] = files[i];
						counter++;
					}
				}
			}
		}

		if(filesToRemove.length > 0) {
			for (int i = 0; i < filesToRemove.length; i++) {

				mediaFiles.remove(filesToRemove[i]);
				log.log(Level.INFO, "media file removed - " + filesToRemove[i].getName());
				if(filesToRemove[i] instanceof Themeslide) {
					((Themeslide) filesToRemove[i]).removeCacheFile();
				}
			}
			displayHandler.remove(filesToRemove);
			
			refreshDataModel();
		}
		
	}
	
	/**
	 * The files are shrinked to the one with the smalles index and then moved down one step
	 * If necessary a update on the GUI and the display handler is called
	 * @param files files to move down
	 */
	public void down(ServerMediaFile[] files) {

		int firstIndex = mediaFiles.indexOf(files[files.length - 1]) + 1 - (files.length - 1);
		remove(files, true);
		if(firstIndex > mediaFiles.size() - 1) {
			for(int i = 0; i < files.length; i++) {
				mediaFiles.addLast(files[i]);
				log.log(Level.INFO, "moving file down " + files[i].getName());
			}
			firstIndex = mediaFiles.size() - 1;
		} else {
			for (int i = files.length -1; i >= 0 ; i--) {
				mediaFiles.add(firstIndex, files[i]);
				log.log(Level.INFO, "moving file down " + files[i].getName());
			}
		}

		refreshDataModel();
		displayHandler.down(files);
		
	}
	
	/**
	 * The files are shrinked to the one with the smallest index and then moved up one step
	 * If necessary a update on the GUI and the display handler is called
	 * @param files files to move up
	 */
	public void up(ServerMediaFile[] files) {
		int firstIndex = mediaFiles.indexOf(files[0]) - 1;
		remove(files, true);
		
		if(firstIndex < 0) {
			firstIndex = 0;
		}
		
		for (int i = files.length -1; i >= 0 ; i--) {

			mediaFiles.add(firstIndex, files[i]);
			log.log(Level.INFO, "moving file up " + files[i].getName());

		}
		
		refreshDataModel();
		displayHandler.up(files);
		
	}
	
	/**
	 * updates the model of the jtable to update the view
	 */
	public void refreshDataModel() {
		checkForUnuseableCountdowns();
		if(managerFrame != null) {
			((FileManagerTableModel) managerFrame.getjTableFileManager().getModel()).updateModel();
		}

	}

	/**
	 * forces every {@link ImageFile} and every {@link Themeslide} to generate a new preview.
	 * Used if changes to the preview scaling property are made
	 */
	public void generateNewScaledPreviews() {

		log.log(Level.INFO, "generating new scaled previews, because scaling attributes changed");
		for(int i = 0; i < mediaFiles.size(); i++) {
			if(mediaFiles.get(i) instanceof ImageFile) {
				((ImageFile) mediaFiles.get(i)).forceRealodDisplayImage();
			}
			if(mediaFiles.get(i) instanceof Themeslide) {
				((Themeslide) mediaFiles.get(i)).getImageFileRepresentation().forceRealodDisplayImage();
			}
		}
	}
	
	/**
	 * forces all {@link ImageFile} objects in the media handlers list to generate a new scaled instance for displaying.
	 * this is useful after resizing the {@link DisplayMainComponent}.
	 */
	public void generateNewDisplayImages() {
		
		log.log(Level.INFO, "generating new display image, because scaling attributes changed");
		for(int i = 0; i < mediaFiles.size(); i++) {
			if(mediaFiles.get(i) instanceof ImageFile) {
				((ImageFile) mediaFiles.get(i)).forceRealoadPreview();
			}
			if(mediaFiles.get(i) instanceof Themeslide) {
				((Themeslide) mediaFiles.get(i)).getImageFileRepresentation().forceRealoadPreview();
			}
		}
		
		
	}


	/**
	 * checks the media files for unuseable countdowns. That means for countdowns that were already started once or ran out.
	 */
	private void checkForUnuseableCountdowns() {
		log.log(Level.INFO, "removing unuseable countdowns");
		if(countdownCounter > 0) {
			ServerMediaFile[] files = new ServerMediaFile[1];
			for(int i = 0; i < mediaFiles.size(); i++) {
				if(mediaFiles.get(i) instanceof Countdown) {
					Countdown countdown = (Countdown) mediaFiles.get(i);
					if(countdown.getStatus() != null && countdown.getStatus().getIsCurrent() != null) {
						if(!countdown.getStatus().getIsCurrent()) {
							if(countdown.isStarted() || countdown.isFinished()) {
								files[0] = countdown;
								remove(files, false);
								countdownCounter--;
							}
							
						}
					}

				}
			}
			
		}

	}
	
	public void increaseCountdownCounter() {
		countdownCounter++;
	}
	
	
	public LinkedList<ServerMediaFile> getMediaFiles() {
		return this.mediaFiles;
	}
	public void setMediaFiles(LinkedList<ServerMediaFile> mediaFiles) {
		this.mediaFiles = mediaFiles;
	}

	public ManagerFrame getManagerFrame() {
		return managerFrame;
	}

	public void setManagerFrame(ManagerFrame managerFrame) {
		this.managerFrame = managerFrame;
	}

	public DisplayMediaModelOld getDisplayMediaModel() {
		return displayHandler;
	}


}
