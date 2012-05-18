package de.netprojectev.Media;

import java.awt.Point;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;

/**
 * Konkrete implementierung der Datenstruktur MediaFile
 * Themenfolien sind keine "echten" Dateien die auf dem Dateisystem gespeichert werden.
 * Sie werden intrinsisch vom Programm verwaltet.
 * Siehe Entwurfsordner für Erklärungen was eine Themenfolie darstellt.
 * 
 */
public class Themeslide extends MediaFile {

	private static final long serialVersionUID = -1132804586378123305L;
	
	private Theme theme;
	private Point anchor;
	private JTextPane text;

	public Themeslide(String name,Priority priority, Theme theme, JTextPane text, Point textPosition) {
		super(name, priority);
		
		this.theme = theme;
		this.anchor = textPosition;
		this.text = text;
		this.text.setEditable(false);

	}

	@Override
	public ImageIcon generatePreview() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("Show file: " + name + "   " + new Date());

	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Point getTextPosition() {
		return anchor;
	}

	public void setTextPosition(Point textPosition) {
		this.anchor = textPosition;
	}

	public JTextPane getText() {
		return text;
	}

	public void setText(JTextPane text) {
		this.text = text;
	}

}
