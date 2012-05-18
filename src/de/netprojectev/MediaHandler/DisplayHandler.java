package de.netprojectev.MediaHandler;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import de.netprojectev.GUI.Display.DisplayMainFrame;
import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.Media.Themeslide;
import de.netprojectev.Media.VideoFile;

/**
 * DisplayHandler ist im Singleton Pattern geschrieben 
 * DisplayHandler arbeitet auf der gleichen
 * Menge Dateien wie der MediaHandler, allerdings werden diese was die Reihenfolge betrifft
 * nicht zwingend synchronisiert (Shufflemodus)
 */
public class DisplayHandler {

	private static DisplayHandler instance = null;

	private MediaHandler mediaHandler;
	private DisplayDispatcher displayDispatcher;
	
	private LinkedList<MediaFile> playingFiles;
	private Boolean isAutomodeEnabled;
	private Boolean isShufflingEnabled;
	private MediaFile currentMediaFile; // null wenn kein Medium gezeigt wird.
	private MediaFile historyFile; //unused siehe showFileAt Methode
	private Timer automodusTimer;
	private Boolean noFileShowed;
	
	private int timeleft;
	
	private Timer refreshTimeLeftTimer;
	
	
	
	
	
	/**
	 * 
	 * Innere Klasse um Attribute des DisplayHandlers verwenden zu können.
	 * Automodus ist eine Timer Klasse um die Zeit abhängigen Methoden zu steuern.
	 * 
	 */
	class AutomodusTimer extends TimerTask {
		public void run() {
			showNext();    
		}
	}
	
	class RefreshTimeleftTimer extends TimerTask {
		
		public void run() {
			
			mediaHandler.getManagerFrame().refreshTimeleftLbl(timeleft);
			timeleft--;

		}
	}
	
	/**
	 * 
	 * Innere Klasse die konkurriernde Timer zu AutomodusTimer verwaltet, um das zeigen einer Datei zu einem bestimmten
	 * Zeitpunkt zu ermöglichen.
	 *
	 */
	class ShowFileAtTimer extends TimerTask {
		
		private MediaFile fileToShow;
		
		protected ShowFileAtTimer(MediaFile fileToShow) {
			this.fileToShow = fileToShow;
		}
		
		public void run() {
			setHistoryFile(currentMediaFile);
			show(fileToShow);
			automodusHasChanged();
		}
	}
	
	
	/**
	 * Konstruktor initalisierung mit vernünftigen Standardwerten.
	 */
	private DisplayHandler() {

		this.playingFiles = new LinkedList<MediaFile>();
		this.isAutomodeEnabled = false;
		this.isShufflingEnabled = false;
		this.noFileShowed = true;
		this.currentMediaFile = null;
		this.setHistoryFile(null);
		this.timeleft = 0;
		this.automodusTimer = new Timer();
		this.refreshTimeLeftTimer = new Timer();
		this.displayDispatcher = DisplayDispatcher.getInstance();
		this.displayDispatcher.setDisplayFrame(new DisplayMainFrame());
		
	}

	public static DisplayHandler getInstance() {
		if (instance == null) {
			instance = new DisplayHandler();
		}
		return instance;
	}

	public static void reset() {
		instance = new DisplayHandler();
	}
	
	/**
	 * Fügt die im übergebenen Array enthalten MediaFiles der "Playlist" (playingFiles) hinzu.
	 * Synchronisation zwischen MediaHandler und DisplayHandler
	 * @param files
	 */
	public void add(MediaFile[] files) {

		//TODO if shuffling is enabled, new files also have to be shuffled in an adequat way
		
		for (int i = 0; i < files.length; i++) {
			if (!playingFiles.contains(files[i])) {
				playingFiles.add(files[i]);
			}

		}
		
	}
	
	/**
	 * Entfernt die übergebenen MediaFiles aus der "Playlist" (playingFiles)
	 * Synchronisation zwischen MediaHandler und DisplayHandler
	 * @param files
	 */
	public void remove(MediaFile[] files) {
		
		for (int i = 0; i < files.length; i++) {
			playingFiles.remove(files[i]);
		}
		
	}
	
	/**
	 * Die übergebenen Dateien werden zum kleinsten Index der übergebenen Dateien gestaucht und der Index jeder Datei um eins erhöht
	 * Wird nur aufgerufen wenn der shuffle Modus nicht aktiviert ist.
	 * @param files
	 */
	public void down(MediaFile[] files) {
		
		if(!isShufflingEnabled) {
			playingFiles = mediaHandler.getMediaFiles();
		}
		
	}

	/**
	 * Die übergebenen Dateien werden zum kleinsten Index der übergebenen Dateien gestaucht und der Index jeder Datei um eins gesenkt
	 * Wird nur aufgerufen wenn der shuffle Modus nicht aktiviert ist.
	 * @param files
	 *  
	 */
	public void up(MediaFile[] files) {
		
		if(!isShufflingEnabled) {
			playingFiles = mediaHandler.getMediaFiles();
		}
				
	}
	
	/**
	 * Das übergebene MediaFile wird angewiesen seine show Methode aufzurufen. Es wird auf dem DisplayFrame gezeigt.
	 * Es werden vor zeigen der Datei verschiedene Bedingungen geprüft sowie die Stati der involvierten Dateien angepasst.
	 * @param file
	 */
	public void show(MediaFile file) {
		
		
		if(currentMediaFile != null) {
			currentMediaFile.getStatus().setIsCurrent(false);
		}
		if(!(currentMediaFile == file) || noFileShowed) {
			currentMediaFile = file;
			currentMediaFile.getStatus().setIsCurrent(true);
			currentMediaFile.getStatus().setWasShowed(true);
			file.show();
			noFileShowed = false;
		}
		
		if(mediaHandler != null) {
			mediaHandler.refreshDataModel();
		}
		automodusHasChanged();
		
	}
	

	/**
	 * Zeigt die nächste Datei in playingFiles
	 * Falls die letzte Datei erreicht wurde springt die Methode zurück zur ersten Datei der Liste.
	 */
	public void showNext() {
		
		int indexOfOldCurrent = playingFiles.indexOf(currentMediaFile);
		
		if(indexOfOldCurrent >= 0) {
			ListIterator<MediaFile> iterator = playingFiles.listIterator(indexOfOldCurrent + 1);
			if(iterator.hasNext()) {
				MediaFile nextFile = iterator.next();
				show(nextFile);
				currentMediaFile = nextFile;
				
			} else if(currentMediaFile != playingFiles.getFirst()) {
				MediaFile nextFile = playingFiles.getFirst();
				show(nextFile);
				currentMediaFile = nextFile;
			
			}
			automodusHasChanged();
		}
	}


	/**
	 * Zeigt die vorherige Datei aus der Playlist.
	 * Falls die Erste Datei erreicht wurde springt die Methode zur letzten Datei der Liste.
	 */
	public void showPrevious() {
		
		int indexOfOldCurrent = playingFiles.indexOf(currentMediaFile);
		
		if(indexOfOldCurrent >= 0) {
			ListIterator<MediaFile> iterator = playingFiles.listIterator(indexOfOldCurrent);
			if(iterator.hasPrevious()) {
				MediaFile previousFile = iterator.previous();
				show(previousFile);
				currentMediaFile = previousFile;
			
			} else if(currentMediaFile != playingFiles.getLast()) {
				MediaFile previousFile = playingFiles.getLast();
				show(previousFile);
				currentMediaFile = previousFile;
				
			}
			automodusHasChanged();
		}

	}

	/**
	 * Startet den Shuffle Modus
	 */
	public void startShuffle() {
		isShufflingEnabled = true;
		shuffleList();
		
		//TODO evtl. currentFile als erstes Elt. der Liste playingfiles setzen
	}
	
	/**
	 * Stoppt den Shuffle Modus
	 */
	@SuppressWarnings("unchecked")
	public void stopShuffle() {
		isShufflingEnabled = false;
		playingFiles = (LinkedList<MediaFile>) MediaHandler.getInstance().getMediaFiles().clone();
		
	}
	
	/**
	 * Die übergebene Datei wird zum übergebenen Zeiptpunkt gezeigt.
	 * @param fileToShow
	 * @param date
	 */
	public void showFileAt(MediaFile fileToShow, Date date) {
		
		
		//TODO Gedächtnis einbauen dass er dahin zurückspringt im automodus wo er vorher war
		fileToShow.getStatus().setShowAt(date);
		Timer tmpTimer = new Timer();
		tmpTimer.schedule(new ShowFileAtTimer(fileToShow), date);
		
	}

	/**
	 * Diese Methode startet den Automodus.
	 * Außerdem wird sie jedes mal nach einem Dateiwechsel aufgerufen um den Timer anzupassen (bzgl der Priority der nächsten Date).
	 */
	public synchronized void startAutomodus() {
		
		if(isAutomodeEnabled) {
			automodusTimer.cancel();
			automodusTimer.purge();
			
			refreshTimeLeftTimer.cancel();
			refreshTimeLeftTimer.purge();
			
			automodusTimer = new Timer();
			refreshTimeLeftTimer = new Timer();
		} 
		
		isAutomodeEnabled = true;
		
		if(currentMediaFile != null) {
			
			if(currentMediaFile instanceof ImageFile) {
				
				ImageFile currentImageFile = (ImageFile) currentMediaFile;
				//TODO throwed once a illegal state exc. cause timer were cancelled already before
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
				
			}
			
		}
	
	}

	/**
	 * Stoppt den Automodus und räumt den Timer auf.
	 */
	public synchronized void stopAutomodus() {
		isAutomodeEnabled = false;
		automodusTimer.cancel();
		automodusTimer.purge();
		refreshTimeLeftTimer.cancel();
		refreshTimeLeftTimer.purge();
		
	}
	
	/**
	 * Jedes mal nach einem Übergang wird diese Methode aufgerufen um die neue Priority mit einzubeziehen.
	 */
	private void automodusHasChanged() {
		if(isAutomodeEnabled) {
			startAutomodus();		
		}
	}
	
	/**
	 * Eine Hilfsmethode welche die Elemente der "Playlist" neu und zufällig anordnet.
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
	
	public LinkedList<MediaFile> getPlayingFiles() {
		return this.playingFiles;
	}

	public void setPlayingFiles(LinkedList<MediaFile> playingFiles) {
		this.playingFiles = playingFiles;
	}

	public MediaFile getCurrentMediaFile() {
		return currentMediaFile;
	}

	public void setCurrentMediaFile(MediaFile currentMediaFile) {
		this.currentMediaFile = currentMediaFile;
	}

	public MediaFile getHistoryFile() {
		return historyFile;
	}

	public void setHistoryFile(MediaFile historyFile) {
		this.historyFile = historyFile;
	}

	public MediaHandler getMediaHandler() {
		return mediaHandler;
	}

	public void setMediaHandler(MediaHandler mediaHandler) {
		this.mediaHandler = mediaHandler;
	}

}
