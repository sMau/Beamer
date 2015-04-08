package de.netprojectev.guiserver;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datamodel.PreferencesModelServer;
import de.netprojectev.common.utils.LoggerBuilder;

/**
 * GUI component to draw the live ticker string.
 *
 * @author samu
 *
 */
public class TickerComponent extends JComponent {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(TickerComponent.class);

	private static final long serialVersionUID = 4472552567124740434L;

	private final JComponent parent;

	private final int paddingBottom = 16;
	private final int paddingTop = 0;
	private final int marginBottom = 32;
	private final float brightnessMultiplicator = 0.5f;

	private String tickerString;
	private int posOfString1;
	private int posOfString2;

	private int toDrawStringWidth;

	private int tickerSpeed;

	private Font tickerFont;
	int tickerStringHeight;
	private Color tickerFontColor;
	private int stringY;

	private Color tickerBackgroundColor;
	private Color tickerBackgroundColorSecondary;
	private float tickerBackgroundAlpha;

	private String toDraw1;
	private String toDraw2;

	private Timer liveTickerTimer;

	class TickerTimerTask extends TimerTask {
		@Override
		public void run() {
			runLiveTicker();
		}
	}

	public TickerComponent(JComponent parent) {
		super();
		this.parent = parent;
		toDraw1 = " ";
		toDraw2 = " ";
		tickerString = " ";
		posOfString1 = getWidth();
		posOfString2 = getWidth();
		initLiveTickerAndStart();
	}

	/**
	 * encapsulates the start method of the Live Ticker to guarentee the
	 * attributes of the font to be initialized.
	 */
	protected void initLiveTickerAndStart() {
		updateFont();
		updateFontColor();
		updateSpeed();
		updateBackgroundColor();
		updateBackgroundAlpha();
		generateDrawingStrings();
		startOrRestart();
	}

	/**
	 * manages the actual calculations needed to get the positions of the two
	 * strings. The positions are calculated in each update cycle before drawing
	 * the strings to the new position.
	 */
	private void runLiveTicker() {
		if (toDraw1 != null && toDraw2 != null && !toDraw1.equals("") && !toDraw2.equals("")) {
			// http://www.java-forum.org/awt-swing-swt/141303-lauftext-performance-probleme-groesserer-anwendung.html
			// posOfString = (v * ticks) % breite;

			posOfString1--;
			posOfString2--;
			// if last char of the string is not visible anymore
			if (posOfString1 + toDrawStringWidth < 0) {
				posOfString1 = posOfString2 + toDrawStringWidth;
			}
			// if last char of the string is not visible anymore
			if (posOfString2 + toDrawStringWidth < 0) {
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
	 * if a current timer is running it will be stopped first. creates a new
	 * Swing Timer with the given update interval and starts the timer. The
	 * timer refreshes the ticker component every x milliseconds.
	 */
	private void startOrRestart() {

		if (liveTickerTimer != null) {
			liveTickerTimer.cancel();
			liveTickerTimer.purge();
		}
		generateDrawingStrings();
		liveTickerTimer = new Timer();
		liveTickerTimer.schedule(new TickerTimerTask(), 0, tickerSpeed);
		log.fine("Live Ticker started");
	}

	/**
	 * Clears the ticker string and stops the live ticker timer
	 */
	protected void stop() {
		setTickerString("");
		if (liveTickerTimer != null) {

			// log.log(Level.INFO, "Live Ticker stoped");
			liveTickerTimer.cancel();
			liveTickerTimer.purge();
		}
		repaint();
	}

	/**
	 * updates the live ticker font. This means size and family. This causes the
	 * ticker to restart, as the font calculations like string width has
	 * changed.
	 */
	protected void updateFontByClient() {
		updateFont();
		startOrRestart();
	}

	protected void updateSpeedByClient() {
		updateSpeed();
		startOrRestart();
	}

	protected void updateFontColor() {
		log.fine("Updating ticker font color.");
		tickerFontColor = new Color(Integer.parseInt(PreferencesModelServer
				.getPropertyByKey(ConstantsServer.PROP_TICKER_FONTCOLOR)));
	}

	protected void updateBackgroundColor() {
		log.fine("Updating ticker background color.");
		tickerBackgroundColor = new Color(Integer.parseInt(PreferencesModelServer
				.getPropertyByKey(ConstantsServer.PROP_TICKER_BACKGROUND_COLOR)));

		float hsbVals[] = Color.RGBtoHSB(tickerBackgroundColor.getRed(), tickerBackgroundColor.getGreen(),
				tickerBackgroundColor.getBlue(), null);
		tickerBackgroundColorSecondary = Color.getHSBColor(hsbVals[0], hsbVals[1], brightnessMultiplicator * hsbVals[2]);
	}

	protected void updateBackgroundAlpha() {
		log.fine("updating ticker background alpha");
		tickerBackgroundAlpha = Float.parseFloat(PreferencesModelServer
				.getPropertyByKey(ConstantsServer.PROP_TICKER_BACKGROUND_ALPHA));
	}

	private void updateFont() {
		Font oldFont = tickerFont;
		try {
			log.fine("Updating ticker font family and size.");
			tickerFont = new Font(
					PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_TICKER_FONTTYPE),
					Font.PLAIN, Integer.parseInt(PreferencesModelServer
							.getPropertyByKey(ConstantsServer.PROP_TICKER_FONTSIZE)));
		} catch (NumberFormatException e) {
			tickerFont = oldFont;
			log.log(Level.WARNING, "Number format exeception", e);
		}
		tickerStringHeight = getFontMetrics(tickerFont).getHeight();

		int height = tickerStringHeight + paddingTop + paddingBottom;
		int y = parent.getHeight() - marginBottom - height - paddingBottom;
		this.setBounds(0, y, parent.getWidth(), height);

		stringY = tickerStringHeight + paddingTop;

	}

	private void updateSpeed() {
		log.fine("Updating ticker speed.");
		int oldspeed = tickerSpeed;
		try {
			tickerSpeed = Integer.parseInt(PreferencesModelServer
					.getPropertyByKey(ConstantsServer.PROP_TICKER_SPEED));
		} catch (NumberFormatException e) {
			log.log(Level.WARNING, "Tickerspeed is a non numeric value.", e);
			tickerSpeed = oldspeed;
		}
	}

	/**
	 * Set the ticker string to draw on the component. A GUI update is invoked
	 * automatically after setting the new string.
	 *
	 * @param tickerString
	 */
	protected void setTickerString(String tickerString) {
		if (tickerString == null) {
			throw new IllegalArgumentException("tickerString cannot be null");
		}
		this.tickerString = tickerString;
		if (tickerString != null && !tickerString.equals("")) {
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
		// log.log(Level.INFO, "generating new drawing strings");
		if (tickerFont == null) {
			log.log(Level.WARNING, "TICKER FONT IS NULL!!!!!");
		}
		if (tickerString == null) {
			log.log(Level.WARNING, "TICKER STRING IS NULL!!!!!");
		}

		int tickerStringWidth = getFontMetrics(tickerFont).stringWidth(tickerString);

		toDraw1 = tickerString;
		toDrawStringWidth = tickerStringWidth;

		while (toDrawStringWidth < getWidth()) {
			toDrawStringWidth = toDrawStringWidth + tickerStringWidth;
			toDraw1 = toDraw1 + tickerString;
		}

		toDraw2 = toDraw1;
		posOfString2 = posOfString1 + toDrawStringWidth;

	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D tmpG2D = (Graphics2D) g.create();
		tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		tmpG2D.setFont(tickerFont);

		Composite oldComposite = tmpG2D.getComposite();
		Paint oldPaint = tmpG2D.getPaint();

		GradientPaint p = new GradientPaint(0, 0, tickerBackgroundColor, 0,
				getHeight(), tickerBackgroundColorSecondary);
		tmpG2D.setPaint(p);

		tmpG2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				tickerBackgroundAlpha));

		tmpG2D.fillRect(tmpG2D.getClip().getBounds().x, tmpG2D.getClip().getBounds().y, tmpG2D
				.getClip().getBounds().width, tmpG2D.getClip().getBounds().height);

		tmpG2D.setComposite(oldComposite);
		tmpG2D.setPaint(oldPaint);

		tmpG2D.setColor(tickerFontColor);
		tmpG2D.drawString(toDraw1, posOfString1, stringY);
		tmpG2D.drawString(toDraw2, posOfString2, stringY);
		tmpG2D.dispose();
	}

}
