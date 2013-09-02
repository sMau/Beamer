package de.netprojectev.server.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.apache.logging.log4j.Logger;

import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.model.PreferencesModelServer;
/**
 * 
 * GUI Component to draw images and themeslide background images
 * @author samu
 *
 */
public class DisplayMainComponent extends JComponent {
	
	private static final Logger log = LoggerBuilder.createLogger(DisplayMainComponent.class);

	private static final long serialVersionUID = 3915763660057625809L;
	private BufferedImage image;
	
	private boolean countdownShowing = false;
	
	private Countdown countdown;
	private Font countdownFont;
	private Color countdownFontColor;
		
	//TODO set a background for this component ( e.g. many 4s logos) that there isnt any grey space when showing a 4:3 resolution image

	protected DisplayMainComponent() {
		super();
		updateCountdownFont();
	}

	/**
	 * updates the countdown font family, size and color via reading it from the properties
	 */
	protected void updateCountdownFont() {
		countdownFont = new Font(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTTYPE), Font.PLAIN, Integer.parseInt(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTSIZE)));
		log.debug("countdown font updated to font: " + countdownFont);
	}
	
	protected void updateCountdownFontColor() {
		countdownFontColor = new Color(Integer.parseInt(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTCOLOR)));
		log.debug("updating countdown font color");
	}
	
	/**
	 * Tell this to draw the given image.
	 * @param file the image file to draw on component
	 */
	protected void drawImage(final BufferedImage compImage) {

		countdownShowing = false;
				
		this.image = Misc.getScaledImageInstanceFast(compImage, (int) (getHeight() * compImage.getWidth()/compImage.getHeight()), (int) getHeight());
		repaint(0,0,getWidth(), getHeight());

	}
	
	/**
	 * Setting the countdown object which should be drawn by the component
	 * 
	 * @param countdown the countdown object to draw
	 */
	protected void drawCountdown(final Countdown countdown) {
		countdownShowing = true;
		this.countdown = countdown;
	}
	
	protected void countdownFinished() {
		countdownShowing = false;
	}

	/**
	 * drawing the image centered.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if(countdownShowing) {
			Graphics2D tmpG2D = (Graphics2D) g.create();
	        tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);	        
	        tmpG2D.setFont(countdownFont); 
	        tmpG2D.setColor(countdownFontColor);
			
			tmpG2D.drawString(countdown.getTimeString(), getWidth()/2 - tmpG2D.getFontMetrics(countdownFont).stringWidth(countdown.getTimeString())/2 , getHeight()/2);
			tmpG2D.dispose();
			
		} else {
			if (image != null) {
				g.drawImage(image, (getWidth() - image.getWidth(null))/2,(getHeight() - image.getHeight(null))/2, this);
			}
		}
		
	}

	protected void clear() {
		countdownShowing = false;
		image = null;
		repaint();
	}

}
