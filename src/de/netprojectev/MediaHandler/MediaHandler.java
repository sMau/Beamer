package de.netprojectev.MediaHandler;

import java.util.LinkedList;

import de.netprojectev.GUI.Manager.FileManagerTableModel;
import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.Media.MediaFile;

/**
 * Handles all media files currently loaded.
 * It synchs itself with the GUI and updates the display handler if necessary.
 * @author samu
 *
 */
public class MediaHandler {


	private static MediaHandler instance = null;

	private DisplayHandler displayHandler;
	
	private LinkedList<MediaFile> mediaFiles;
	
	private ManagerFrame managerFrame;

	private MediaHandler() {

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
				mediaFiles.add(files[i]);
			}

		}
		
		refreshDataModel();
		displayHandler.add(files);

	}

	
	/**
	 * removes the media files from the list and if necessary invokes updates on the GUI and the display handler.	 * @param files files to remove
	 * @param files files to remove
	 */
	public void remove(MediaFile[] files) {
		
		//TODO prevent curFile from removing
		//oder ne andere loesung fuer das problem einfallen lassen dass wenn das aktuelle file entfernt wird next und prev nicht funktionieren
		
		for (int i = 0; i < files.length; i++) {
			mediaFiles.remove(files[i]);
		}
		displayHandler.remove(files);
		
		refreshDataModel();
	}
	
	/**
	 * The files are shrinked to the one with the smalles index and then moved down one step
	 * If necessary a update on the GUI and the display handler is called
	 * @param files files to move down
	 */
	public void down(MediaFile[] files) {

		int firstIndex = mediaFiles.indexOf(files[0]) + 1;
		if(firstIndex > mediaFiles.size() - 1) {
			firstIndex = mediaFiles.size() - 1;
		}
		
		remove(files);
		
		for (int i = files.length -1; i >= 0 ; i--) {
			mediaFiles.add(firstIndex, files[i]);

		}
		
		refreshDataModel();
		displayHandler.down(files);
		
	}
	
	/**
	 * The files are shrinked to the one with the smalles index and then moved up one step
	 * If necessary a update on the GUI and the display handler is called
	 * @param files files to move up
	 */
	public void up(MediaFile[] files) {
		
		int firstIndex = mediaFiles.indexOf(files[0]) - 1;
		remove(files);
		
		if(firstIndex < 0) {
			firstIndex = 0;
		}
		
		for (int i = files.length -1; i >= 0 ; i--) {

			mediaFiles.add(firstIndex, files[i]);

		}
		
		refreshDataModel();
		displayHandler.up(files);
		
	}
	
	/**
	 * updates the model of the jtable to update the view
	 */
	public void refreshDataModel() {
		
		if(managerFrame != null) {
			((FileManagerTableModel) managerFrame.getjTableFileManager().getModel()).updateModel();
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
