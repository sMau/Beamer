package de.netprojectev.GUI.Themeslide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextPane;

public class TextPaneThemeslide extends JTextPane {
	/**
	 * 
	 */
	
	//TODO Performance improve on typing, its a little like a sort of delay after pressing a key
	
	private static final long serialVersionUID = 6811486190608541934L;
	
	private Image image;
	
	
	public TextPaneThemeslide() {
        super();
        setOpaque(false);
        // this is needed if using Nimbus L&F - see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6687960
        setBackground(new Color(0,0,0,0));
        setContentType("text/html");
        setMargin(new Insets(3, 3, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
    	g.clearRect(0, 0, getWidth(), getHeight());
    	
        if (image != null) {
			g.drawImage(image, 0, 0, null);		
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
