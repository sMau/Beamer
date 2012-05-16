package de.netprojectev.GUI.Themeslide;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class PreviewThemeslideComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4411192008897145428L;
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
