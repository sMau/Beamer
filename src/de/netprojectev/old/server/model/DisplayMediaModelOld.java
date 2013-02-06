package de.netprojectev.old.server.model;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.netprojectev.old.server.datastructures.media.Countdown;
import de.netprojectev.old.server.datastructures.media.ImageFile;
import de.netprojectev.old.server.datastructures.media.ServerMediaFile;
import de.netprojectev.old.server.datastructures.media.Themeslide;
import de.netprojectev.old.server.datastructures.media.VideoFile;
import de.netprojectev.server.gui.DisplayFrame;

/**
 * The display handler contains the same files as the mediahandler. The order of the files can differ concerning the shuffle mode.
 * Furthermore auto mode and shuffling is realized within this class. Methods like next and previous act on the files of display handler.
 * @author samu
 */
public class DisplayMediaModelOld {

	private static final Logger log = Logger.getLogger(DisplayMediaModelOld.class.getName());
	
	private MediaHandlerOld mediaHandler;
	
	private LinkedList<ServerMediaFile> playingFiles;
	private Boolean isAutomodeEnabled;
	private Boolean isShufflingEnabled;
	private ServerMediaFile currentMediaFile; // null wenn kein Medium gezeigt wird.
	private ServerMediaFile historyFile; //unused siehe showFileAt Methode
	private Timer automodusTimer;
	private boolean noFileShowed;
	
	private int timeleft;
	
	private Timer refreshTimeLeftTimer;

	/**
	 * Inner class to handle auto mode timer
	 * @author samu
	 *
	 */
	class AutomodusTimer extends TimerTask {
		public void run() {
			showNext();    
		}
	}
	
	/**
	 * inner class to handle the refreshing of the timeleft lable in the main frame
	 * @author samu
	 *
	 */
	class RefreshTimeleftTimer extends TimerTask {
		
		public void run() {
			
			mediaHandler.getManagerFrame().refreshTimeleftLbl(timeleft);
			timeleft--;

		}
	}
		
	/**
	 * inner class to handle showing a file to a specific time.
	 * @author samu
	 *
	 */
	class ShowFileAtTimer extends TimerTask {
		
		private ServerMediaFile fileToShow;
		
		protected ShowFileAtTimer(ServerMediaFile fileToShow) {
			this.fileToShow = fileToShow;
		}
		
		public void run() {
			setHistoryFile(currentMediaFile);
			show(fileToShow);
			automodusHasChanged();
		}
	}
	
	public DisplayMediaModelOld() {

		this.playingFiles = new LinkedList<ServerMediaFile>();
		this.isAutomodeEnabled = false;
		this.isShufflingEnabled = false;
		this.noFileShowed = true;
		this.currentMediaFile = null;
		this.setHistoryFile(null);
		this.timeleft = 0;
		this.automodusTimer = new Timer();
		this.refreshTimeLeftTimer = new Timer();
		
	}

	/**
	 * Adds given files to the filelist. Used for syncing with media handler
	 * @param files files to add
	 */
	public void add(ServerMediaFile[] files) {
		
		if(isShufflingEnabled) {
			LinkedList<ServerMediaFile> tmpListToAdd = new LinkedList<ServerMediaFile>();
			LinkedList<ServerMediaFile> tmpListToRemove = new LinkedList<ServerMediaFile>();
			for (int i = 0; i < files.length; i++) {
				if (!playingFiles.contains(files[i])) {
					tmpListToAdd.add(files[i]);
				}
			}
			for (int i = 0; i < playingFiles.size(); i++) {
				if(!playingFiles.get(i).getStatus().getWasShowed()) {
					tmpListToAdd.add(playingFiles.get(i));
					tmpListToRemove.add(playingFiles.get(i));
				}
			}
			
			for(int i = 0; i < tmpListToRemove.size(); i++) {
				playingFiles.remove(tmpListToRemove.get(i));
			}
			
			Collections.shuffle(tmpListToAdd);
			for(int i = 0; i < tmpListToAdd.size(); i++) {
				playingFiles.addFirst((tmpListToAdd.get(i)));
			}
		}	
		for (int i = 0; i < files.length; i++) {
			if (!playingFiles.contains(files[i])) {
				playingFiles.add(files[i]);
			}

		}
		
	}
	
	/**
	 * Removes given files to the filelist. Used for synching with media handler
	 * @param files files to remove
	 */
	public void remove(ServerMediaFile[] files) {
		
		for (int i = 0; i < files.length; i++) {
			playingFiles.remove(files[i]);
		}
		
	}
	
	/**
	 * Moves given files down. Used for synching with media handler, only invoked when shuffling disabled
	 * @param files to move down
	 */
	public void down(ServerMediaFile[] files) {
		
		if(!isShufflingEnabled) {
			playingFiles = mediaHandler.getMediaFiles();
		}
		
	}

	/**
	 * Moves given files up. Used for synching with media handler, only invoked when shuffling disabled
	 * @param files files to move up
	 */
	public void up(ServerMediaFile[] files) {
		
		if(!isShufflingEnabled) {
			playingFiles = mediaHandler.getMediaFiles();
		}
				
	}
	
	/**
	 * invokes the show method of the given file and updates the auto mode timer if necessary and the status of involved files
	 * @param file
	 */
	public void show(ServerMediaFile file) {
		
		if(currentMediaFile != null && currentMediaFile != file) {
			currentMediaFile.getStatus().setIsCurrent(false);
		}
		if(!(currentMediaFile == file) || noFileShowed) {
			currentMediaFile = file;
			currentMediaFile.getStatus().setIsCurrent(true);
			currentMediaFile.getStatus().setWasShowed(true);
			
			/*
			 * 
			 * TODO
			 * 
			 * after refactoring the implementation of showing a file is corrupted
			 */
			
			//file.show();
			
			
			
			noFileShowed = false;
		}
		
		mediaHandler.refreshDataModel();
		automodusHasChanged();
		
	}
	

	/**
	 * invokes the show method of next file from list
	 * method is cyclic implemented
	 */
	public void showNext() {
		
		int indexOfOldCurrent = playingFiles.indexOf(currentMediaFile);
		
		if(indexOfOldCurrent >= 0) {
			ListIterator<ServerMediaFile> iterator = playingFiles.listIterator(indexOfOldCurrent + 1);
			if(iterator.hasNext()) {
				ServerMediaFile nextFile = iterator.next();
				show(nextFile);
				currentMediaFile = nextFile;
				
			} else if(currentMediaFile != playingFiles.getFirst()) {
				ServerMediaFile nextFile = playingFiles.getFirst();
				show(nextFile);
				currentMediaFile = nextFile;
			
			}
			automodusHasChanged();
		}
	}


	/**
	 * invokes the show method of previous file from list
	 * method is cyclic implemented
	 */
	public void showPrevious() {
		
		int indexOfOldCurrent = playingFiles.indexOf(currentMediaFile);
		
		if(indexOfOldCurrent >= 0) {
			ListIterator<ServerMediaFile> iterator = playingFiles.listIterator(indexOfOldCurrent);
			if(iterator.hasPrevious()) {
				ServerMediaFile previousFile = iterator.previous();
				show(previousFile);
				currentMediaFile = previousFile;
			
			} else if(currentMediaFile != playingFiles.getLast()) {
				ServerMediaFile previousFile = playingFiles.getLast();
				show(previousFile);
				currentMediaFile = previousFile;
				
			}
			automodusHasChanged();
		}

	}

	/**
	 * sets the shuffling mode to enabled and invokes the shuffling method
	 */
	public void startShuffle() {
		log.log(Level.INFO, "starting shuffle mode");
		isShufflingEnabled = true;
		shuffleList();
	}
	
	/**
	 * sets shuffling mode to disabled and synchs files back with media handler file order
	 */
	@SuppressWarnings("unchecked")
	public void stopShuffle() {
		log.log(Level.INFO, "stoping shuffle mode");
		isShufflingEnabled = false;
		playingFiles = (LinkedList<ServerMediaFile>) MediaHandlerOld.getInstance().getMediaFiles().clone();
		
	}
	
	/**
	 * shows a media file to given date
	 * @param fileToShow file to show
	 * @param date show at
	 */
	public void showFileAt(ServerMediaFile fileToShow, Date date) {
		
		fileToShow.getStatus().setShowAt(date);
		Timer tmpTimer = new Timer();
		tmpTimer.schedule(new ShowFileAtTimer(fileToShow), date);
		
	}

	/**
	 * sets auto mode to enabled
	 * initalizes timers for next current file change and to update the time left label 
	 * furthermore invoked after every current file change to update the timer
	 */
	public synchronized void startAutomodus() {
				
		log.log(Level.INFO, "starting new automodus timer");
	
		if(isAutomodeEnabled) {
			automodusTimer.cancel();
			automodusTimer.purge();
			
			refreshTimeLeftTimer.cancel();
			refreshTimeLeftTimer.purge();

		} 
		
		automodusTimer = new Timer();
		refreshTimeLeftTimer = new Timer();
		
		isAutomodeEnabled = true;
		
		if(currentMediaFile != null) {
			
			if(currentMediaFile instanceof ImageFile) {
				
				ImageFile currentImageFile = (ImageFile) currentMediaFile;
				automodusTimer.schedule(new AutomodusTimer(), currentImageFile.getPriority().getMinutesToShow()*1000*60);
				timeleft = currentImageFile.getPriority().getMinutesToShow()*60;
				refreshTimeLeftTimer.schedule(new RefreshTimeleftTimer(), 0, 1000);
				
			} else if(currentMediaFile instanceof Themeslide) {
				
				Themeslide currentThemeslide = (Themeslide) currentMediaFile;
				automodusTimer.schedule(new AutomodusTimer(), currentThemeslide.getPriority().getMinutesToShow()*1000*60);
				timeleft = currentThemeslide.getPriority().getMinutesToShow()*60;
				refreshTimeLeftTimer.schedule(new RefreshTimeleftTimer(), 0, 1000);
				
			} else if(currentMediaFile instanceof VideoFile){
				
				VideoFile currentVideoFile = (VideoFile) currentMediaFile;
				automodusTimer.schedule(new AutomodusTimer(), currentVideoFile.getLength());
				timeleft = currentVideoFile.getLength();
				refreshTimeLeftTimer.schedule(new RefreshTimeleftTimer(), 0, 1000);
				
			} else if(currentMediaFile instanceof Countdown) {
				
				Countdown countdown = (Countdown) currentMediaFile;
				automodusTimer.schedule(new AutomodusTimer(), ((int) countdown.getSecondsToZero())*1000);
				timeleft = (int) countdown.getSecondsToZero();
				refreshTimeLeftTimer.schedule(new RefreshTimeleftTimer(), 0, 1000);

			}
			
		}
	
	}

	/**
	 * sets auto mode to disabled and tidies up the timers
	 */
	public synchronized void stopAutomodus() {
		log.log(Level.INFO, "stoping automodus");
		isAutomodeEnabled = false;
		automodusTimer.cancel();
		automodusTimer.purge();
		refreshTimeLeftTimer.cancel();
		refreshTimeLeftTimer.purge();
		
	}
	
	/**
	 * invoked after every update of current file to take new priority into account
	 */
	private void automodusHasChanged() {
		if(isAutomodeEnabled) {
			startAutomodus();		
		}
	}
	
	/**
	 * shuffles the list of the display handler
	 */
	private void shuffleList() {
		Collections.shuffle(playingFiles);
	}

	public Boolean getIsAutomodeEnabled() {
		return isAutomodeEnabled;
	}

	public void setIsAutomodeEnabled(Boolean isAutomodeEnabled) {
		this.isAutomodeEnabled = isAutomodeEnabled;
	}

	public Boolean getIsShufflingEnabled() {
		return isShufflingEnabled;
	}

	public void setIsShufflingEnabled(Boolean isShufflingEnabled) {
		this.isShufflingEnabled = isShufflingEnabled;
	}
	
	public LinkedList<ServerMediaFile> getPlayingFiles() {
		return this.playingFiles;
	}

	public void setPlayingFiles(LinkedList<ServerMediaFile> playingFiles) {
		this.playingFiles = playingFiles;
	}

	public ServerMediaFile getCurrentMediaFile() {
		return currentMediaFile;
	}

	public void setCurrentMediaFile(ServerMediaFile currentMediaFile) {
		this.currentMediaFile = currentMediaFile;
	}

	public ServerMediaFile getHistoryFile() {
		return historyFile;
	}

	public void setHistoryFile(ServerMediaFile historyFile) {
		this.historyFile = historyFile;
	}

	public MediaHandlerOld getMediaHandler() {
		return mediaHandler;
	}

	public void setMediaHandler(MediaHandlerOld mediaHandler) {
		this.mediaHandler = mediaHandler;
	}

}
