package de.netprojectev.GUI.Display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.File;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

import de.netprojectev.Media.Countdown;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Preferences.PreferencesHandler;
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
	private Font countdownFont;
	private Color countdownColor;
		
	private DisplayMainComponent instance; 
	
	//TODO improve scaling with progressive bilinear scaling (see filthy rich clients)
	//TODO set a background for this component ( e.g. many 4s logos) that there isnt any grey space when showing a 4:3 resolution image
	
	//TODO performance improve to this component here, furthermore add swingworker as handler for this task
	
	public DisplayMainComponent() {
		super();
		this.instance = this;
		
		/*
		 * TODO
		 * at the moment simply waiting until properties are available, but this is only a workaround.
		 */
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateCountdownFont();
				
			}
		}).start();
		
	}

	public void updateCountdownFont() {

		Properties props = PreferencesHandler.getInstance().getProperties();
		countdownFont = new Font(props.getProperty(Constants.PROP_COUNTDOWN_FONTTYPE), Font.PLAIN, Integer.parseInt(props.getProperty(Constants.PROP_COUNTDOWN_FONTSIZE)));
		countdownColor = new Color(Integer.parseInt(props.getProperty(Constants.PROP_COUNTDOWN_FONTCOLOR)));

	}
	
	/**
	 * 
	 * @param file the image file to draw on component
	 */
	public void setImageToDraw(final File file) {
	
		
		/*countdownShowing = false;
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
		}*/
		
		//testing with swingworker painting
		
		new SwingWorker<Void, Void>() {
		    @Override
		    public Void doInBackground() {
		    	countdownShowing = false;
				image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
				if (image != null) {
					int imW = image.getWidth(null);
					int imH = image.getHeight(null);
					
					if(getWidth()/getHeight() <= imW/imH) {
						image = image.getScaledInstance(-1, (int) instance.getBounds().getHeight(), Image.SCALE_SMOOTH);
					} else {
						image = image.getScaledInstance((int) instance.getBounds().getWidth(), -1, Image.SCALE_SMOOTH);
					}
				
				}

				return null;
		    }

		    @Override
		    public void done() {
		    	if(image != null) {
		    		
		    		instance.repaint();
		    	}
		    }
		}.execute();

	}
	
	/**
	 * Setting the countdown object which should be drawn by the component
	 * 
	 * @param countdown the countdown object to draw
	 */
	public void setCountdownToDraw(Countdown countdown) {
		countdownShowing = true;
		this.countdown = countdown;
	}

	/**
	 * drawing the image centered.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if(countdownShowing) {
			//TODO centering isnt correct, cause of baseline point is interesting
			Graphics2D tmpG2D = (Graphics2D) g.create();
	        tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        

	        tmpG2D.setFont(countdownFont); 
	        tmpG2D.setColor(countdownColor);
	        
			String toDraw =	countdown.getTimeString(); 
			int stringWidth = getFontMetrics(getFont()).stringWidth(toDraw);
			int stringHeight = getFontMetrics(getFont()).getHeight();
			
			tmpG2D.drawString(toDraw, (getWidth() - stringWidth)/2,(getHeight() - stringHeight)/2);
			tmpG2D.dispose();
			
		} else {
			if (image != null) {
				g.drawImage(image, (getWidth() - image.getWidth(null))/2,(getHeight() - image.getHeight(null))/2, this);
			}
		}
		
	}

}
