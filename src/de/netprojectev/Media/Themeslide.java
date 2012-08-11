package de.netprojectev.Media;

import java.awt.Point;
import java.util.Date;

import javax.swing.JTextPane;

/**
 * Datastructure for a themeslide. It holds a theme object and a formatted styled text as JTextPane.
 * @author samu
 */
public class Themeslide extends MediaFile {

	private static final long serialVersionUID = -1132804586378123305L;
	
	private int hashkey; // -1 means no jpg created yet
	
	private Theme theme;
	private Point margin;
	private JTextPane text;

	/**
	 * 
	 * @param name name in the manager
	 * @param priority priority initial priority
	 * @param theme theme object to specifiy background image
	 * @param text formatted styled text as JTextPane
	 * @param textPosition left and top margin for moving the textpane in the right position
	 */
	public Themeslide(String name,Priority priority, Theme theme, JTextPane text, Point textPosition) {
		super(name, priority);
		
		this.theme = theme;
		this.margin = textPosition;
		this.text = text;
		this.text.setEditable(false);
		
		this.hashkey = -1;

	}
	
	@Override
	public void show() {
				
		display.getDisplayMainComponent().setImageToDraw(theme.getBackgroundImage());
		display.getDisplayMainComponent().add(text);
		System.out.println("Show file: " + name + "   " + new Date());

	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Point getTextPosition() {
		return margin;
	}

	public void setTextPosition(Point textPosition) {
		this.margin = textPosition;
	}

	public JTextPane getText() {
		return text;
	}

	public void setText(JTextPane text) {
		this.text = text;
	}

	public int getHashkey() {
		return hashkey;
	}

	public void setHashkey(int hashkey) {
		this.hashkey = hashkey;
	}

}
