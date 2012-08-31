package de.netprojectev.GUI.Display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * GUI component to draw the live ticker string.
 * @author samu
 *
 */
public class TickerComponent extends JComponent {

	private static final long serialVersionUID = 4472552567124740434L;
	private String tickerString;

	private int posOfFirstChar = getWidth();

	/**
	 * Set the ticker string to draw on the component.
	 * A GUI update is invoked automatically after setting the new string.
	 * @param tickerString
	 */
	public void setTickerString(String tickerString) {
		this.tickerString = tickerString;
		repaint();
		
	}

	@Override
	protected void paintComponent(Graphics g) {

		if (tickerString != null) {
			posOfFirstChar--;
			if(posOfFirstChar < 0 ) {
				posOfFirstChar = getWidth();
			}
			Graphics2D tmpG2D = (Graphics2D) g.create();
	        tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	        tmpG2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			
	        tmpG2D.drawString(tickerString, posOfFirstChar, 55);
		}
	}
}
