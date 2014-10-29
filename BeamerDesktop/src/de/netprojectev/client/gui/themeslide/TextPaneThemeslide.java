package de.netprojectev.client.gui.themeslide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;

import de.netprojectev.client.gui.main.Misc;

public class TextPaneThemeslide extends JTextPane {
	/**
	 *
	 */

	private static final long serialVersionUID = 6811486190608541934L;

	private transient BufferedImage image;

	public TextPaneThemeslide() {
		super();
		setOpaque(false);
		// this is needed if using Nimbus L&F - see
		// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6687960
		setBackground(new Color(0, 0, 0, 0));
		setContentType("text/html");
		setMargin(new Insets(3, 3, 0, 0));

		setDoubleBuffered(true);

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());

		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		super.paintComponent(g2d);
	}

	public void setThemeBackground(ImageIcon imageIcon) {
		image = Misc.createCompatibleTranslucentImage(imageIcon.getIconWidth(), imageIcon.getIconHeight());
		Graphics g = image.createGraphics();
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();

		resizeToImageSize();

	}

	private void resizeToImageSize() {

		int width = image.getWidth(null);
		int height = image.getHeight(null);

		setBounds(0, 0, width, height);

		Dimension newSize = new Dimension(width, height);

		setSize(newSize);
		setPreferredSize(newSize);
		setMaximumSize(newSize);
		setMinimumSize(newSize);

		((TextPanePanel) getParent()).updateTextPane(this, width, height);

		repaint();

	}

}
