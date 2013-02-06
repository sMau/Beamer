package de.netprojectev.old.server.gui.display;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.netprojectev.misc.Misc;
import de.netprojectev.old.server.datastructures.media.Countdown;
import de.netprojectev.old.server.datastructures.media.ImageFile;
import de.netprojectev.old.server.datastructures.media.Themeslide;
import de.netprojectev.old.server.datastructures.media.VideoFile;
import de.netprojectev.old.server.model.DisplayTickerModelOld;
import de.netprojectev.server.gui.DisplayFrame;
import de.netprojectev.server.model.ServerMediaModel;
import de.netprojectev.server.model.TickerModel;

public class DisplayController {
	
	private static final Logger LOG = Misc.getLoggerAll(DisplayController.class.getName());
	
	private final DisplayFrame display;

	
	public DisplayController() {
		this.display = new DisplayFrame(null);


	}
	
	public void showCountdown(Countdown countdown) throws IllegalStateException {
		if(display == null) {
			throw new IllegalStateException("setDisplayMainFrame was not called correctly before showing media.");
		}
		if(!countdown.isFinished()) {
			LOG.log(Level.INFO, "preparing to start countdown " + countdown.getName());
			countdown.setStarted(true);
			countdown.convertDateToSeconds();
			if(countdown.getSecondsToZero() > 0) {
				display.getDisplayMainComponent().drawCountdown(countdown);
				countdown.startCountdown();
			} else {				
				countdown.getStatus().setIsCurrent(false);
				countdown.setFinished(true);
				countdown.setStarted(true);
			}
			
		}
		
	}
	
	public void showImageFile(ImageFile imageFile) throws IllegalStateException {
		if(display == null) {
			throw new IllegalStateException("DisplayMainFrame was not set correctly.");
		}
		LOG.log(Level.INFO, "showing image " + imageFile.getName());
		display.getDisplayMainComponent().drawImage(imageFile.getDisplayImage());
	}
	
	public void showThemeslide(Themeslide themeslide) throws IllegalStateException {
		if(display == null) {
			throw new IllegalStateException("DisplayMainFrame was not set correctly.");
		}
		LOG.log(Level.INFO, "showing themeslide " + themeslide.getName());
		showImageFile(themeslide.getImageFileRepresentation());
	}
	
	public void showVideoFile(VideoFile videoFile) throws IllegalStateException {
		if(display == null) {
			throw new IllegalStateException("DisplayMainFrame was not set correctly.");
		}
		//TODO not implemented yet
		
		throw new UnsupportedOperationException("DisplayMainFrame was not set correctly.");
	}
	
	public void updateTickerText(String text) {
		if(display == null) {
			throw new IllegalStateException("DisplayMainFrame was not set correctly.");
		}
		display.getTickerComponent().setTickerString(text);
	}
	
	public void startLiveTicker() {
		display.getTickerComponent().initLiveTickerAndStart();
	}

	
	/**
	 * entering the fullscreen
	 */
    public void exitFullscreen() {
		display.exitFullscreen();
	}
    
    /**
     * exiting the fullscreen
     */
    public void enterFullscreen(int screenNumber) {
    	display.enterFullscreen(screenNumber);
	}

	
}
