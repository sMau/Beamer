package de.netprojectev.mediahandler;

import de.netprojectev.gui.display.DisplayMainFrame;

/**
 * Singleton class to grant global access to the displayFrame
 * @author samu
 *
 */
public class DisplayDispatcher {
	
	private volatile static DisplayDispatcher instance = new DisplayDispatcher();
	
	private DisplayMainFrame displayFrame;
	
	private DisplayDispatcher() {
				
	}
	
	/**
	 * This method ensures theres only one instance of {@link DisplayDispatcher}
	 * 
	 * @return the {@link DisplayDispatcher} instance
	 */
	public static DisplayDispatcher getInstance() {
		
		if(instance == null) {
			instance = new DisplayDispatcher();
		}
		return instance;
	}

	public DisplayMainFrame getDisplayFrame() {
		return displayFrame;
	}

	public void setDisplayFrame(DisplayMainFrame displayFrame) {
		this.displayFrame = displayFrame;
		this.displayFrame.setEnabled(true);
		this.displayFrame.setVisible(true);
	}
	
}
