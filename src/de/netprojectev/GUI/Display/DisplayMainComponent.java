package de.netprojectev.GUI.Display;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class DisplayMainComponent extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3915763660057625809L;
	private Image image;
	private ImageIcon iconRepre;

	public void setThemeBackground(File file) {
		image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
		if (image != null) {
			iconRepre = new ImageIcon(image);
			setSize(iconRepre.getIconWidth(), iconRepre.getIconHeight());
			setMinimumSize(getSize());
			setMaximumSize(getSize());
			setPreferredSize(getSize());
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}
	}
}
