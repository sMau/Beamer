package de.netprojectev.GUI.Display;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JComponent;

public class DisplayMainComponent extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3915763660057625809L;
	private Image image;
	
	//TODO Scaling after resizing?
	
	public void setImageToDraw(File file) {
		image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath());
		if (image != null) {
			
			int imW = image.getWidth(null);
			int imH = image.getHeight(null);
			
			if(getWidth()/getHeight() <= imW/imH) {
				image = image.getScaledInstance(-1, (int) this.getBounds().getHeight(), Image.SCALE_SMOOTH);
			} else {
				image = image.getScaledInstance((int) this.getBounds().getWidth(), -1, Image.SCALE_SMOOTH);
			}
			repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, (getWidth() - image.getWidth(null))/2,(getHeight() - image.getHeight(null))/2, this);
		}
	}
}
