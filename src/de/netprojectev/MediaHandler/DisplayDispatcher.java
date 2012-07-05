package de.netprojectev.MediaHandler;

import de.netprojectev.GUI.Display.DisplayMainFrame;

/**
 * Singleton class to grant global access to the displayFrame
 * @author samu
 *
 */
public class DisplayDispatcher {

	private static DisplayDispatcher instance = new DisplayDispatcher();
	
	private DisplayMainFrame displayFrame;
	
	private DisplayDispatcher() {
				
	}
	
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
