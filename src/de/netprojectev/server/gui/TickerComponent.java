package de.netprojectev.server.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import de.netprojectev.misc.Constants;
import de.netprojectev.misc.Misc;
import de.netprojectev.old.server.model.PreferencesModelOld;

/**
 * GUI component to draw the live ticker string.
 * @author samu
 *
 */
public class TickerComponent extends JComponent {

	private static final Logger log = Misc.getLoggerAll(TickerComponent.class.getName());

	private static final long serialVersionUID = 4472552567124740434L;
	
	private String tickerString;
	private int posOfString1;
	private int posOfString2;
	
	private int toDrawStringWidth;
	
	private int tickerSpeed;
	
	private Font tickerFont;
	private Color tickerColor;
	
	private String toDraw1;
	private String toDraw2;
	
	private Timer liveTickerTimer;
	
	
	class TickerTimerTask extends TimerTask {
		public void run() {
			runLiveTicker();
		}
	}
	
	protected TickerComponent() {
		super();
		toDraw1 = " ";
		toDraw2 = " ";
		tickerString = " ";
		posOfString1 = getWidth();
		posOfString2 = getWidth();
		
	}
	
	
	/**
	 * encapsulates the starting updating of attributes and the new string generation.
	 * to guarantee the invocation in the appropriated order.
	 */
	protected void restartTicker() {
		updateLiveTickerAttributes();
		generateDrawingStrings();
		startLiveTicker();
	}
	
	/**
	 * encapsulates the start method of the Live Ticker to guarentee
	 * the attributes of the font to be initialized.
	 */
	protected void initLiveTickerAndStart() {
		updateLiveTickerAttributes();
		startLiveTicker();
	}
	
	/**
	 * manages the actual calculations needed to get the positions of the two strings.
	 * The positions are calculated in each update cycle before drawing the strings to the new position.
	 */
	private void runLiveTicker() {
		if (toDraw1 != null && toDraw2 != null && !toDraw1.equals("") && !toDraw2.equals("")) {
		//http://www.java-forum.org/awt-swing-swt/141303-lauftext-performance-probleme-groesserer-anwendung.html
		//	posOfString = (v * ticks) % breite;
			
			posOfString1--;
			posOfString2--;			
			//if last char of the string is not visible anymore
			if(posOfString1 + toDrawStringWidth  < 0 ) {
				posOfString1 = posOfString2 + toDrawStringWidth;
			}
			//if last char of the string is not visible anymore
			if(posOfString2 + toDrawStringWidth < 0 ) {
				posOfString2 = posOfString1 + toDrawStringWidth;
			}
		
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					repaint(0, 0, getWidth(), getHeight());
				}
			});
			
		}
		
	}
	
	
	/**
	 * if a current timer is running it will be stopped first.
	 * creates a new Swing Timer with the given update interval and starts the timer.
	 * The timer refreshes the ticker component every x milliseconds.
	 */
	private void startLiveTicker() {
		
		if(liveTickerTimer != null) {
			stopLiveTicker();
		}
		liveTickerTimer = new Timer();
		liveTickerTimer.schedule(new TickerTimerTask(), 0, tickerSpeed);
    	log.log(Level.INFO, "Live Ticker started");
	}
    

	
	/**
	 * stops the live ticker timer
	 */
    private void stopLiveTicker() {
    	if(liveTickerTimer != null) {
    		log.log(Level.INFO, "Live Ticker stoped");
    		liveTickerTimer.cancel();
        	liveTickerTimer.purge();
    	}
    }
	
    /**
     * updates the live ticker attributes via reading them from the properties object from the {@link PreferencesModelOld}
     * the speed, font size,type and color attributes are affected.
     */
    private void updateLiveTickerAttributes() {
		/*log.log(Level.INFO, "updateing live ticker attributes");
    	Properties props = PreferencesModelOld.getInstance().getProperties();
    	try {
			tickerSpeed = Integer.parseInt(props.getProperty(Constants.PROP_TICKER_SPEED));
			tickerFont = new Font(props.getProperty(Constants.PROP_TICKER_FONTTYPE), Font.PLAIN, Integer.parseInt(props.getProperty(Constants.PROP_TICKER_FONTSIZE)));
			tickerColor = new Color(Integer.parseInt(props.getProperty(Constants.PROP_TICKER_FONTCOLOR))); 
    	} catch (NumberFormatException e) {
			log.log(Level.INFO, "Number format exeception", e);
		}*/
    	//TODO
    }
    
	/**
	 * Set the ticker string to draw on the component.
	 * A GUI update is invoked automatically after setting the new string.
	 * @param tickerString
	 */
    protected void setTickerString(String tickerString) {
		this.tickerString = tickerString;
		if(tickerString != null && !tickerString.equals("")) {
			generateDrawingStrings();
		} else {
			toDraw1 = " ";
			toDraw2 = " ";
		}
	}
	
	/**
	 * generates the two strings needed for a gapless ticker run.
	 */
	private void generateDrawingStrings() {
		log.log(Level.INFO, "generating new drawing strings");
		int tickerStringWidth = getFontMetrics(tickerFont).stringWidth(tickerString);
			
		toDraw1 = tickerString;
		toDrawStringWidth = tickerStringWidth;
			
		while(toDrawStringWidth < getWidth()) {
			toDrawStringWidth = toDrawStringWidth + tickerStringWidth;
			toDraw1 = toDraw1 + tickerString;
		}
			
		toDraw2 = toDraw1;
		posOfString2 = posOfString1 + toDrawStringWidth;
				
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D tmpG2D = (Graphics2D) g.create();
        tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		tmpG2D.setFont(tickerFont);
		tmpG2D.setColor(tickerColor);
        tmpG2D.drawString(toDraw1, posOfString1, 55);
        tmpG2D.drawString(toDraw2, posOfString2, 55);
        tmpG2D.dispose();
	}

}
