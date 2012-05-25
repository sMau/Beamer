package de.netprojectev.MediaHandler;

import de.netprojectev.GUI.Display.DisplayMainFrame;

/**
 * Singleton class to grant global access to the displayFrame
 * @author samu
 *
 */
public class DisplayDispatcher {

	private static volatile DisplayDispatcher instance = null;
	
	private DisplayMainFrame displayFrame;
	
	private DisplayDispatcher() {
				
	}
	
	public static synchronized DisplayDispatcher getInstance() {
		
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
