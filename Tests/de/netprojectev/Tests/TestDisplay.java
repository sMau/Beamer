package de.netprojectev.Tests;

import de.netprojectev.GUI.Display.DisplayMainFrame;

public class TestDisplay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DisplayMainFrame disp = new DisplayMainFrame();
		disp.setVisible(true);
		disp.getTickerComponent().setTickerString("TEEEEEEEEEEEEEEEEEEEEEST +++ TEEEEEEEEEEEEEEEEEST2");
		

	}

}
