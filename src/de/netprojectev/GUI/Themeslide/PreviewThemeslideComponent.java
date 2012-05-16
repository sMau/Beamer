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
		System.out.println("setthembg reached: " + file.getAbsolutePath());
		image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
		System.out.println(image);
		if (image != null) {
			System.out.println("setthemebg if reached");
			iconRepre = new ImageIcon(image);
			//System.out.println(iconRepre.getIconWidth() + "   " + iconRepre.getIconHeight());
			setSize(iconRepre.getIconWidth(), iconRepre.getIconHeight());
			
			System.out.println(getSize());
			setMinimumSize(getSize());
			setMaximumSize(getSize());
			setPreferredSize(getSize());
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		System.out.println("paintcomp reached.");
		if (image != null)
			
			
			g.drawImage(image, 0, 0, this);
	}
}
