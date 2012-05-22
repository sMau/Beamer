package de.netprojectev.GUI.Display;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * GUI component to draw the live ticker string.
 * @author samu
 *
 */
public class TickerComponent extends JComponent {

	private static final long serialVersionUID = 4472552567124740434L;
	private String tickerString;

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
			g.drawString(tickerString, 55, 55);
		}
	}
}
