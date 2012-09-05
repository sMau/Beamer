package de.netprojectev.Media;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import de.netprojectev.GUI.Display.DisplayMainComponent;
import de.netprojectev.Misc.Misc;

/**
 * Datastructure to hold the necessary information of a countdown.
 * 
 * @author samu
 *
 */
public class Countdown {
	
	private DisplayMainComponent displayMainComp;
	
	private String timeString;
	private Timer countdownTimer;
	private int secondsToZero;

	
	public Countdown(int secondsToZero) {
		
		timeString = "";
		this.secondsToZero = secondsToZero;
	}
	
	public Countdown(Date date) {
		
		timeString = "";
		//TODO conversion from the date choosen to seconds (maybe for gui)
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
	
	
	


