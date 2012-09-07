package de.netprojectev.GUI.Display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

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

	private String toDraw1;
	private String toDraw2;
	
	//TODO fix the behavior when removing the last element from the list. it proceeds slower in this case instead of showing no string

	//TODO font changing (at least color) when changing color of the countdown -> propably bug
	
	public TickerComponent() {
		super();
		posOfString1 = getWidth();
		posOfString2 = getWidth();
	}
	
	/**
	 * Set the ticker string to draw on the component.
	 * A GUI update is invoked automatically after setting the new string.
	 * @param tickerString
	 */
	public void setTickerString(String tickerString) {
		this.tickerString = tickerString;
		if(tickerString != null && tickerString != "") {
			generateDrawingStrings();
		} else if(tickerString != null) {
			toDraw1 = " ";
			toDraw2 = " ";
		}
	}
	
	/**
	 * needs to be invoked after resizing the tickerComponent, e.g. after entering the fullscreen
	 */
	public void refreshStringGeneration() {
		generateDrawingStrings();
	}
	
	/**
	 * generates the two strings needed for a gapless ticker.
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

		if (toDraw1 != null && toDraw2 != null && toDraw1 != "" && toDraw2 != "") {
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
	        tmpG2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			
	        tmpG2D.drawString(toDraw1, posOfString1, 55);
	        tmpG2D.drawString(toDraw2, posOfString2, 55);
	        tmpG2D.dispose();
		}
	}
}
