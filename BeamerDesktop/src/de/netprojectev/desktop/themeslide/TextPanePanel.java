package de.netprojectev.desktop.themeslide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TextPanePanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -6040310318920422773L;

	private TextPaneThemeslide textPane = new TextPaneThemeslide();
	private Insets marginOfTextPaneThemslide = textPane.getMargin();

	public void updateTextPane(TextPaneThemeslide textPane, int width, int height) {

		this.textPane = textPane;
		this.textPane.setMargin(marginOfTextPaneThemslide);

		Dimension newSize = new Dimension(width + 40, height + 40);

		setBounds(0, 0, width + 40, height + 40);

		setSize(newSize);
		setPreferredSize(newSize);
		setMaximumSize(newSize);
		setMinimumSize(newSize);

		setBackground(Color.GRAY.darker());

		add(this.textPane);
		textPane.setBounds(0, 0, textPane.getWidth(), textPane.getHeight());

	}

	@Override
	protected void paintComponent(Graphics g) {

		int x = 0;
		int y = 0;

		super.paintComponent(g);

		Graphics2D gTmp = (Graphics2D) g.create();

		gTmp.setColor(Color.GRAY.brighter());

		for (int i = 0; i < getWidth(); i++) {

			for (int j = 0; j < getHeight(); j++) {
				if (j % 2 == i % 2) {
					gTmp.fillRect(i * 10, j * 10, 10, 10);
				}

			}

		}

		gTmp.dispose();

		x = (getWidth() - textPane.getWidth()) / 2;
		y = (getHeight() - textPane.getHeight()) / 2;

		textPane.setBounds(x, y, textPane.getWidth(), textPane.getHeight());

	}

	public Insets getMarginOfTextPaneThemslide() {
		return marginOfTextPaneThemslide;
	}

	public void setMarginOfTextPaneThemslide(Insets marginOfTextPaneThemslide) {
		this.marginOfTextPaneThemslide = marginOfTextPaneThemslide;
		this.textPane.setMargin(marginOfTextPaneThemslide);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				textPane.repaint();
			}
		});

	}

}
