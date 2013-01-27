package de.netprojectev.media.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;


import de.netprojectev.gui.display.DisplayMainComponent;
import de.netprojectev.mediahandler.DisplayDispatcher;
import de.netprojectev.misc.Constants;
import de.netprojectev.misc.Misc;

/**
 * Datastructure to hold the necessary information of a countdown.
 * 
 * @author samu
 *
 */
public class Countdown extends ServerMediaFile {

	private static final long serialVersionUID = 8156954767550365161L;

	private static final Logger log = Misc.getLoggerAll(Countdown.class.getName());
	
	private transient DisplayMainComponent displayMainComp;
	
	private boolean started = false;
	private boolean finished = false;
	
	private String timeString;
	private Timer countdownTimer;
	private long secondsToZero;
	private Date finishDate;
		
	/**
	 * 
	 * @param name name shown in the gui
	 * @param minutesToZero time in minutes the countdown will run
	 */
	public Countdown(String name, int minutesToZero) {
		super(name, Constants.NO_PRIORITY);
		timeString = "";
		finishDate = null;
		this.secondsToZero = minutesToZero * 60;
		this.displayMainComp = DisplayDispatcher.getInstance().getDisplayFrame().getDisplayMainComponent();
	}
	
	/**
	 * 
	 * @param name name shown in the gui
	 * @param date the date when the countdown shall reach the value zero
	 */
	public Countdown(String name, Date date) {
		super(name, Constants.NO_PRIORITY);
		timeString = "";
		this.finishDate = date;
		this.displayMainComp = DisplayDispatcher.getInstance().getDisplayFrame().getDisplayMainComponent();
		
	}
	

	@Override
	public void show() {
		if(!finished) {
			log.log(Level.INFO, "preparing to start countdown " + name);
			started = true;
			convertDateToSeconds();
			if(this.secondsToZero > 0) {
				displayMainComp.drawCountdown(this);
				startCountdown();
			} else {				
				status.setIsCurrent(false);
				finished = true;
				started = true;
			}
			
		}
		
	}
	
	/**
	 * in case of using a finishing date, this method converts the date in a runtime in seocnds
	 */
	private void convertDateToSeconds() {
		
		if(finishDate != null) {
			Date now = new Date();
			this.secondsToZero = ((finishDate.getTime() - now.getTime()) / 1000);
		}
		
	}
	
	/**
	 * starts the countdown.
	 * It creates a new SwingTimer to repeatedly execute the update of the countdown string in 1 second intervals
	 */
	private void startCountdown() {

		
		countdownTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				secondsToZero--;
				timeString = Misc.convertFromSecondsToTimeString((int) secondsToZero, true);
				displayMainComp.repaint();
				if(secondsToZero <= 0) {
					finished = true;
					status.setIsCurrent(false);
					countdownTimer.stop();
					log.log(Level.INFO, "countdown " + name + " stoped");
				}
				
			}
		});
		countdownTimer.start(); 
		log.log(Level.INFO, "countdown " + name + " started");
	}
	
	@Override
	public void setPriority(Priority priority) {
		this.priority = Constants.NO_PRIORITY;
	}

	public DisplayMainComponent getDisplayMainComp() {
		return displayMainComp;
	}

	public void setDisplayMainComp(DisplayMainComponent displayMainComp) {
		this.displayMainComp = displayMainComp;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public long getSecondsToZero() {
		return secondsToZero;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isFinished() {
		return finished;
	}

}
	
	
	


