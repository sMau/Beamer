package de.netprojectev.GUI.Display;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

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
	
	private DisplayMainComponent instance;
	
	//TODO improve scaling with progressive bilinear scaling (see filthy rich clients)
	//TODO set a background for this component ( e.g. many 4s logos) that there isnt any grey space when showing a 4:3 resolution image
	
	//TODO performance improve to this component here, furthermore add swingworker as handler for this task
	
	public DisplayMainComponent() {
		super();
		this.instance = this;
		
	}
	/**
	 * 
	 * @param file the image file to draw on component
	 */
	public void setImageToDraw(final File file) {
	
		
		countdownShowing = false;
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
		
		//testing with swingworker painting
		
		/*new SwingWorker<Void, Void>() {
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
		    		System.out.println("image not null");
		    		instance.repaint();
		    	}
		    }
		};
		*/
		
		
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
			//TODO respect the preset values of the font
			setFont(new Font("Arial", this.getFont().getStyle(), 32));
			String toDraw =	countdown.getTimeString(); 
			int stringWidth = getFontMetrics(getFont()).stringWidth(toDraw);
			int stringHeight = getFontMetrics(getFont()).getHeight();
			g.drawString(toDraw, (getWidth() - stringWidth)/2,(getHeight() - stringHeight)/2);
			
		} else {
			if (image != null) {
				g.drawImage(image, (getWidth() - image.getWidth(null))/2,(getHeight() - image.getHeight(null))/2, this);
			}
		}
		
	}
}
