package de.netprojectev.Tests;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class DualScreenTest {
	
	
	public static void main(String[] args) {
		
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] myDevices = ge.getScreenDevices();
		
		for(int i = 0; i < myDevices.length; i++) {

			System.out.println(myDevices[i].getDefaultConfiguration());
		}
		
		/*dispose();
		this.setUndecorated(true);
		setVisible(true);
		// this.setAlwaysOnTop(true);
		myDevice.setFullScreenWindow(this);*/
		
		
		
	}

}
