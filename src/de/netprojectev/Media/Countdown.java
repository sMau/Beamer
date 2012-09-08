package de.netprojectev.Media;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import de.netprojectev.GUI.Display.DisplayMainComponent;
import de.netprojectev.MediaHandler.DisplayDispatcher;
import de.netprojectev.MediaHandler.DisplayHandler;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;

/**
 * Datastructure to hold the necessary information of a countdown.
 * 
 * @author samu
 *
 */
public class Countdown extends MediaFile {
	//TODO very buggy file removing of non countdowns after serial and deserial a countdown

	/**
	 * 
	 */
	private static final long serialVersionUID = 8156954767550365161L;

	private transient DisplayMainComponent displayMainComp;
	
	
	private boolean finished = false;
	private String timeString;
	private Timer countdownTimer;
	private long secondsToZero;
	private Date finishDate;
	
	public Countdown(String name, int minutesToZero) {
		super(name, Constants.NO_PRIORITY);
		timeString = "";
		finishDate = null;
		this.secondsToZero = minutesToZero * 60;
		this.displayMainComp = DisplayDispatcher.getInstance().getDisplayFrame().getDisplayMainComponent();
	}
	
	public Countdown(String name, Date date) {
		super(name, Constants.NO_PRIORITY);
		timeString = "";
		this.finishDate = date;
		this.displayMainComp = DisplayDispatcher.getInstance().getDisplayFrame().getDisplayMainComponent();
		
	}
	

	@Override
	public void show() {
		if(!finished) {
			convertDateToSeconds();
			displayMainComp.setCountdownToDraw(this);
			startCountdown();
		}
		
	}
	
	private void convertDateToSeconds() {
		
		if(finishDate != null) {
			Date now = new Date();
			this.secondsToZero = ((finishDate.getTime() - now.getTime()) / 1000);
		}
		
	}
	
	private void startCountdown() {

		countdownTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				secondsToZero--;
				timeString = Misc.convertFromSecondsToTimeString((int) secondsToZero);
				displayMainComp.repaint();
				if(secondsToZero <= 0) {
					finished = true;
					countdownTimer.stop();
				}
				
			}
		});
		countdownTimer.start(); 
			
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

}
	
	
	


