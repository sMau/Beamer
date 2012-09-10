package de.netprojectev.GUI.Display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import de.netprojectev.Misc.Constants;
import de.netprojectev.Preferences.PreferencesHandler;

/**
 * GUI component to draw the live ticker string.
 * @author samu
 *
 */
public class TickerComponent extends JComponent {

	private static final long serialVersionUID = 4472552567124740434L;
	
	private String tickerString;
	private int posOfString1;
	private int posOfString2;
	
	private int toDrawStringWidth;
	
	private int tickerSpeed;
	
	private String toDraw1;
	private String toDraw2;
	
	private Timer liveTickerTimer;
	
	//TODO fix the behavior when removing the last element from the list. it proceeds slower in this case instead of showing no string
	
	public TickerComponent() {
		super();
		posOfString1 = getWidth();
		posOfString2 = getWidth();
		
		try {
			tickerSpeed = Integer.parseInt(PreferencesHandler.getInstance().getProperties().getProperty(Constants.PROP_TICKER_SPEED));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        startLiveTicker();

		
	}
	
	/**
	 * creates a new Swing Timer with the given update interval and starts the timer.
	 * The timer refreshes the ticker component every x milliseconds.
	 */
	public void startLiveTicker() {

		liveTickerTimer = new Timer(tickerSpeed, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint(0, 0, getWidth(), getHeight()); //TODO testing repainting only the visible area
			}
		});
    	liveTickerTimer.start();
    	
	}
    
	/**
	 * stops the timer
	 */
    public void stopLiveTicker() {
    	liveTickerTimer.stop();
    }
	
    public void updateLiveTickerAttributes() {
    	
    	//TODO implement, reading attr from props object
    }
    
	/**
	 * Set the ticker string to draw on the component.
	 * A GUI update is invoked automatically after setting the new string.
	 * @param tickerString
	 */
	public void setTickerString(String tickerString) {
		this.tickerString = tickerString;
		if(tickerString != null && !tickerString.equals("")) {
			generateDrawingStrings();
		} else if(tickerString != null) {
			toDraw1 = " ";
			toDraw2 = " ";
			stopLiveTicker();
		} else {
			stopLiveTicker();
		}
	}
	
	/**
	 * needs to be invoked after resizing the tickerComponent, e.g. after entering the fullscreen
	 */
	public void refreshStringGeneration() {
		generateDrawingStrings();
	}
	
	/**
	 * generates the two strings needed for a gapless ticker run.
	 */
	private void generateDrawingStrings() {

		int tickerStringWidth = getFontMetrics(getFont()).stringWidth(tickerString);
		
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

		if (toDraw1 != null && toDraw2 != null && !toDraw1.equals("") && !toDraw2.equals("")) {
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
			Graphics2D tmpG2D = (Graphics2D) g.create();
	        tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
	        tmpG2D.drawString(toDraw1, posOfString1, 55);
	        tmpG2D.drawString(toDraw2, posOfString2, 55);
	        tmpG2D.dispose();
		} else {
			stopLiveTicker();
		}
	}

}
