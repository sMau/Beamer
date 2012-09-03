package de.netprojectev.MediaHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.netprojectev.GUI.Manager.FileManagerTableModel;
import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.Media.Themeslide;
import de.netprojectev.Misc.Misc;

/**
 * Handles all media files currently loaded.
 * It synchs itself with the GUI and updates the display handler if necessary.
 * @author samu
 *
 */
public class MediaHandler {
	
	private static final Logger log = Logger.getLogger(MediaHandler.class.getName());

	private static MediaHandler instance = new MediaHandler();

	private DisplayHandler displayHandler;
	
	private LinkedList<MediaFile> mediaFiles;
	
	private ManagerFrame managerFrame;

	private MediaHandler() {

		try {
			log.addHandler(Misc.getLogFileHandlerAll());
			log.addHandler(Misc.getLogFileHandlerError());
		} catch (SecurityException e) {
		} catch (IOException e) {
		}
		
		mediaFiles = new LinkedList<MediaFile>();
		displayHandler = DisplayHandler.getInstance();
		displayHandler.setMediaHandler(this);
		
	}

	public static MediaHandler getInstance() {

		if (instance == null) {
			instance = new MediaHandler();
		}
		return instance;
	}

	public static void reset() {
		instance = new MediaHandler();
	}

	/**
	 * Adds the media files to list and if necessary invokes updates on the GUI and the display handler.
	 * @param files files to add
	 */
	public void add(MediaFile[] files) {

		for (int i = 0; i < files.length; i++) {

			if (!mediaFiles.contains(files[i])) {
				log.log(Level.INFO, "media file added - " + files[i].getName());
				mediaFiles.add(files[i]);
			} else {
				log.log(Level.INFO, "media file already in list - " + files[i].getName());
			}

		}
		
		refreshDataModel();
		displayHandler.add(files);

	}

	
	/**
	 * removes the media files from the list and if necessary invokes updates on the GUI and the display handler.	 
	 * Prevents current file from being removed from the list, to avoid problems with synching to the {@link DisplayHandler}.
	 * Furthermore it cleans up the themeslide cache. JPGs not needed anymore are deleted from the cache.
	 * @param files files to remove
	 * @param removeCurrent true if the current element should also be removed if it is in the selection, false if not
	 */
	public void remove(MediaFile[] files, boolean removeCurrent) {

		boolean containsCurrentElt = false;
		MediaFile[] filesToRemove = files; 
		
		log.log(Level.INFO, "removing files, removing current: " + removeCurrent);
		
		if(!removeCurrent) {
			for (int i = 0; i < files.length; i++) {
				if(files[i].getStatus().getIsCurrent()) {
					containsCurrentElt = true;
				}
			}
			
			if(containsCurrentElt) {
				int counter = 0;
				filesToRemove = new MediaFile[files.length - 1];
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
	public void down(MediaFile[] files) {
		log.log(Level.INFO, "moving selected files down");
		
		int firstIndex = mediaFiles.indexOf(files[files.length - 1]) + 1 - (files.length - 1);
		remove(files, true);
		if(firstIndex > mediaFiles.size() - 1) {
			for(int i = 0; i < files.length; i++) {
				mediaFiles.addLast(files[i]);
				log.log(Level.INFO, "moving file down: " + files[i].getName());
			}
			firstIndex = mediaFiles.size() - 1;
		} else {
			for (int i = files.length -1; i >= 0 ; i--) {
				mediaFiles.add(firstIndex, files[i]);
				log.log(Level.INFO, "moving file down: " + files[i].getName());
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
	public void up(MediaFile[] files) {
		log.log(Level.INFO, "moving selected files up");
		int firstIndex = mediaFiles.indexOf(files[0]) - 1;
		remove(files, true);
		
		if(firstIndex < 0) {
			firstIndex = 0;
		}
		
		for (int i = files.length -1; i >= 0 ; i--) {

			mediaFiles.add(firstIndex, files[i]);
			log.log(Level.INFO, "moving file up: " + files[i].getName());

		}
		
		refreshDataModel();
		displayHandler.up(files);
		
	}
	
	/**
	 * updates the model of the jtable to update the view
	 */
	public void refreshDataModel() {
		log.log(Level.INFO, "updating table model");
		if(managerFrame != null) {
			((FileManagerTableModel) managerFrame.getjTableFileManager().getModel()).updateModel();
		}

	}
	
	/**
	 * forces every {@link ImageFile} and every {@link Themeslide} to generate a new preview.
	 * Used if changes to the preview scaling property are made
	 */
	public void generateNewScaledPreviews() {

		log.log(Level.INFO, "generating new scaled previews");
		for(int i = 0; i < mediaFiles.size(); i++) {
			if(mediaFiles.get(i) instanceof ImageFile) {
				((ImageFile) mediaFiles.get(i)).forceRealoadPreview();
			}
			if(mediaFiles.get(i) instanceof Themeslide) {
				((Themeslide) mediaFiles.get(i)).getImageFileRepresentation().forceRealoadPreview();
			}
		}
	}


	public LinkedList<MediaFile> getMediaFiles() {
		return this.mediaFiles;
	}
	public void setMediaFiles(LinkedList<MediaFile> mediaFiles) {
		this.mediaFiles = mediaFiles;
	}

	public ManagerFrame getManagerFrame() {
		return managerFrame;
	}

	public void setManagerFrame(ManagerFrame managerFrame) {
		this.managerFrame = managerFrame;
	}

	public DisplayHandler getDisplayHandler() {
		return displayHandler;
	}

	public void setDisplayHandler(DisplayHandler displayHandler) {
		this.displayHandler = displayHandler;
	}
	

}
