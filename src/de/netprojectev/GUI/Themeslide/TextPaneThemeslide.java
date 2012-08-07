package de.netprojectev.GUI.Themeslide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextPane;

public class TextPaneThemeslide extends JTextPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6811486190608541934L;
	
	private Image image;
	
	public TextPaneThemeslide() {
        super();
        setOpaque(false);
        // this is needed if using Nimbus L&F - see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6687960
        setBackground(new Color(0,0,0,0));
    }

    @Override
    protected void paintComponent(Graphics g) {
    	g.clearRect(0, 0, getWidth(), getHeight());
    	
        if (image != null) {
			g.drawImage(image, 0, 0, this);
		}
        
        super.paintComponent(g);
    }
    
    public void setThemeBackground(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resizeToImageSize();
		
	}
    
    private void resizeToImageSize() {

    	int width = image.getWidth(null);
		int height = image.getHeight(null);
    	
    	System.out.println("resizeToImageSize invoked");
    	
    	
    	/*
    	 * 
    	Dimension newSize = new Dimension(width, height);
    	
    	getParent().setSize(newSize);
    	getParent().setPreferredSize(newSize);
    	getParent().setMaximumSize(newSize);
    	getParent().setMinimumSize(newSize);
    	
    	setSize(newSize);
    	setPreferredSize(newSize);
    	setMaximumSize(newSize);
    	setMinimumSize(newSize);
    	
    	*/
    	
    	//TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	/*
    	 * 
    	 * Here is last edited
    	 * My Txtpane is now nested in a further jPanel now scrolling bar policy seems to be nice,
    	 * but ssadly the 1024x768 image seems not to be drawn completely
    	 * (its still possible to get out of the picture furthermore, but in impress its also possible so not such a problem)
		 * Have to test much more if anythin works correct, maybe some sizes are still wrong, espcially the min size of the scrollpane seems to be to small
		 * Furthermore make the jpanel drawing without border or something like that, and remove the margin of the jpanel
		 * setting the "anchor" will be best to be set via margin in the TextPane
    	 * 
    	 * 
    	 */
    	
    	getParent().setBounds(0, 0, width, height);
    	setBounds(0, 0, width, height);
    	
    	System.out.println("New parents size: " + getParent().getBounds());
    	System.out.println("New textpane size: " + getBounds());
    	
		repaint();
    }

}
