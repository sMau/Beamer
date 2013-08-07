package de.netprojectev.client.gui.themeslide;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;


public class TextPaneThemeslide extends JTextPane {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 6811486190608541934L;
	
	private transient BufferedImage image;
		
	public TextPaneThemeslide() {
        super();
        setOpaque(false);
        // this is needed if using Nimbus L&F - see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6687960
        setBackground(new Color(0,0,0,0));
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
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paintComponent(g2d);
    }
    
    //TODO better use a file here, in all themslides to avoid high ram usage
    public void setThemeBackground(ImageIcon imageIcon) {
		image = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		
		resizeToImageSize();
		
	}
    
    //TODO check this for efficency better use the algo. in misc prob.
    
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
