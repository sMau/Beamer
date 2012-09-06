package de.netprojectev.Media;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import de.netprojectev.GUI.Display.DisplayMainComponent;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;

/**
 * Datastructure to hold the necessary information of a countdown.
 * 
 * @author samu
 *
 */
public class Countdown extends MediaFile{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 8156954767550365161L;

	private DisplayMainComponent displayMainComp;
	
	private String timeString;
	private Timer countdownTimer;
	private int secondsToZero;

	
	public Countdown(String name, int minutesToZero) {
		super(name, Constants.NO_PRIORITY);
		timeString = "";
		this.secondsToZero = minutesToZero * 60;
	}
	
	public Countdown(String name, Date date) {
		super(name, Constants.NO_PRIORITY);
		timeString = "";
		//TODO conversion from the date choosen to seconds (maybe for gui)
	}
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		//TODO showing the countdown
		
	}
	
	
	public void startCountdown() {

		countdownTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				secondsToZero--;
				timeString = Misc.convertFromSecondsToTimeString(secondsToZero);
				displayMainComp.repaint();
				if(secondsToZero == 0) {
					countdownTimer.stop();
				}
				
			}
		});
		countdownTimer.start(); 
			
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

}
	
	
	


