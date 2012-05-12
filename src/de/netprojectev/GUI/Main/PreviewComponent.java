package de.netprojectev.GUI.Main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JComponent;

/**
 * 
 * @author samu
 *
 * Class is not used at the moment, was thought for previewing images but didnt worked yet.
 *
 */

public class PreviewComponent extends JComponent {
	private Image image;

	public void setImage(File file) {
		image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
		if (image != null)
			repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, this);
	}
}
