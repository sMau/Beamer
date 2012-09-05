package de.netprojectev.GUI.Display;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JComponent;

import de.netprojectev.Media.Countdown;
/**
 * 
 * GUI Component to draw images and themeslide background images
 * @author samu
 *
 */
public class DisplayMainComponent extends JComponent {
	
	private static final long serialVersionUID = 3915763660057625809L;
	private Image image;
	
	private boolean countdownShowing = false;
	
	private Countdown countdown;
	
	//TODO improve scaling with progressive bilinear scaling (see filthy rich clients)
	//TODO set a background for this component ( e.g. many 4s logos) that there isnt any grey space when showing a 4:3 resolution image
	
	/**
	 * 
	 * @param file the image file to draw on component
	 */
	public void setImageToDraw(File file) {
		image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
		if (image != null) {
			
			int imW = image.getWidth(null);
			int imH = image.getHeight(null);
			
			if(getWidth()/getHeight() <= imW/imH) {
				image = image.getScaledInstance(-1, (int) this.getBounds().getHeight(), Image.SCALE_SMOOTH);
			} else {
				image = image.getScaledInstance((int) this.getBounds().getWidth(), -1, Image.SCALE_SMOOTH);
			}
			repaint();
		}
	}
	
	public void drawCountdown(Countdown countdown) {
		this.countdown = countdown;
		countdown.setDisplayMainComp(this);
		countdown.startCountdown();
		countdownShowing = true;
		repaint();
	}

	/**
	 * drawing the image centered.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if(countdownShowing) {
			 countdown.getTimeString();
			//TODO implement string centered drawing of countdown
		} else {
			if (image != null) {
				g.drawImage(image, (getWidth() - image.getWidth(null))/2,(getHeight() - image.getHeight(null))/2, this);
			}
		}
		
	}
}
