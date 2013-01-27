package de.netprojectev.gui.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;

import de.netprojectev.media.server.Countdown;
import de.netprojectev.misc.Constants;
import de.netprojectev.misc.Misc;
import de.netprojectev.preferences.PreferencesHandler;
/**
 * 
 * GUI Component to draw images and themeslide background images
 * @author samu
 *
 */
public class DisplayMainComponent extends JComponent {
	
	private static final Logger log = Misc.getLoggerAll(DisplayMainComponent.class.getName());

	private static final long serialVersionUID = 3915763660057625809L;
	private Image image;
	
	private boolean countdownShowing = false;
	
	private Countdown countdown;
	private Font countdownFont;
	private Color countdownColor;
		
	//TODO set a background for this component ( e.g. many 4s logos) that there isnt any grey space when showing a 4:3 resolution image

	public DisplayMainComponent() {
		super();

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					log.log(Level.SEVERE, "interupt exception", e);
				}
				updateCountdownFont();
				
			}
		}).start();
		
	}

	/**
	 * updates the countdown font family, size and color via reading it from the properties
	 */
	public void updateCountdownFont() {

		Properties props = PreferencesHandler.getInstance().getProperties();
		countdownFont = new Font(props.getProperty(Constants.PROP_COUNTDOWN_FONTTYPE), Font.PLAIN, Integer.parseInt(props.getProperty(Constants.PROP_COUNTDOWN_FONTSIZE)));
		countdownColor = new Color(Integer.parseInt(props.getProperty(Constants.PROP_COUNTDOWN_FONTCOLOR)));
		log.log(Level.INFO, "countdown font attributes updated");

	}
	
	/**
	 * Tell this to draw the given image.
	 * @param file the image file to draw on component
	 */
	public void drawImage(final Image compImage) {
		
		countdownShowing = false;
		this.image = compImage;
		repaint(0,0,getWidth(), getHeight());

	}
	
	/**
	 * Setting the countdown object which should be drawn by the component
	 * 
	 * @param countdown the countdown object to draw
	 */
	public void drawCountdown(Countdown countdown) {
		countdownShowing = true;
		this.countdown = countdown;
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
	        tmpG2D.setColor(countdownColor);
			
			tmpG2D.drawString(countdown.getTimeString(), getWidth()/2 - tmpG2D.getFontMetrics(countdownFont).stringWidth(countdown.getTimeString())/2 , getHeight()/2);
			tmpG2D.dispose();
			
		} else {
			if (image != null) {
				g.drawImage(image, (getWidth() - image.getWidth(null))/2,(getHeight() - image.getHeight(null))/2, this);
			}
		}
		
	}

}
