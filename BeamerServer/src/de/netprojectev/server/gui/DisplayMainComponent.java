package de.netprojectev.server.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.utils.LoggerBuilder;
import de.netprojectev.utils.Misc;

/**
 *
 * GUI Component to draw images and themeslide background images
 *
 * @author samu
 *
 */
public class DisplayMainComponent extends JComponent {

	private static final java.util.logging.Logger log = LoggerBuilder.createLogger(DisplayMainComponent.class);

	private static final long serialVersionUID = 3915763660057625809L;
	private BufferedImage image;

	private boolean countdownShowing = false;

	private Countdown countdown;
	private Font countdownFont;
	private Color countdownFontColor;

	private Color generalBGColor;

	// XXX Make repeating possible for much to small images, like about the
	// factor of 2 to small

	public DisplayMainComponent() {
		super();
		updateCountdownFont();
		updateCountdownFontColor();
		updateBackgroundColor();
	}

	/**
	 * updates the countdown font family, size and color via reading it from the
	 * properties
	 */
	protected void updateCountdownFont() {
		countdownFont = new Font(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTTYPE), Font.PLAIN, Integer.parseInt(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTSIZE)));
		log.fine("countdown font updated to font: " + countdownFont);
	}

	protected void updateCountdownFontColor() {
		countdownFontColor = new Color(Integer.parseInt(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_COUNTDOWN_FONTCOLOR)));
		log.fine("updating countdown font color");
	}

	/**
	 * Tell this to draw the given image.
	 *
	 *            the image file to draw on component
	 * @throws IOException
	 */
	protected void drawImage(ImageFile image) throws IOException {

		InputStream in = new ByteArrayInputStream(image.get());
		final BufferedImage compImage = ImageIO.read(in);

		log.fine(Integer.toString(compImage.getHeight()));

		countdownShowing = false;

		int newWidth;
		int newHeight;

		double screenAspectRatio = ((double) getWidth()) / ((double) getHeight());
		double imageAspectRatio = ((double) compImage.getWidth()) / ((double) compImage.getHeight());

		if (screenAspectRatio == imageAspectRatio) {
			newWidth = getWidth();
			newHeight = getHeight();
		} else if (screenAspectRatio < imageAspectRatio) {
			newWidth = getWidth();
			newHeight = (int) (newWidth / imageAspectRatio);
		} else {
			newHeight = getHeight();
			newWidth = (int) (newHeight * imageAspectRatio);
		}

		log.fine("new size of the image: " + newWidth + "x" + newHeight);

		this.image = Misc.getScaledImageInstanceFast(compImage, newWidth, newHeight);
		repaint(0, 0, getWidth(), getHeight());

	}

	/**
	 * Setting the countdown object which should be drawn by the component
	 *
	 * @param countdown
	 *            the countdown object to draw
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

		Color oldColor = g.getColor();

		g.setColor(generalBGColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(oldColor);

		if (countdownShowing) {
			Graphics2D tmpG2D = (Graphics2D) g.create();
			tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			tmpG2D.setFont(countdownFont);
			tmpG2D.setColor(countdownFontColor);

			tmpG2D.drawString(countdown.getTimeString(), getWidth() / 2 - tmpG2D.getFontMetrics(countdownFont).stringWidth(countdown.getTimeString()) / 2, getHeight() / 2);
			tmpG2D.dispose();

		} else {
			if (image != null) {
				g.drawImage(image, (getWidth() - image.getWidth(null)) / 2, (getHeight() - image.getHeight(null)) / 2, this);
			}
		}

	}

	protected void clear() {
		countdownShowing = false;
		image = null;
		repaint();
	}

	protected void updateBackgroundColor() {
		generalBGColor = new Color(Integer.parseInt(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_GENERAL_BACKGROUND_COLOR)));
		log.fine("updating general background color");
		repaint(0, 0, getWidth(), getHeight());
	}

}
