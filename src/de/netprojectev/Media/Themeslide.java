package de.netprojectev.Media;

import java.awt.Point;
import java.util.LinkedList;

import javax.swing.ImageIcon;

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
	private LinkedList<String> textElements;
	private Point textPosition;

	public Themeslide(String name,Priority priority, Theme theme, LinkedList<String> textElements,
			Point textPosition) {
		super(name, priority);
		
		this.theme = theme;
		this.textElements = textElements;
		this.textPosition = textPosition;

	}

	@Override
	public ImageIcon generatePreview() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("Show file: " + name);

	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public LinkedList<String> getTextElements() {
		return textElements;
	}

	public void setTextElements(LinkedList<String> textElements) {
		this.textElements = textElements;
	}

	public Point getTextPosition() {
		return textPosition;
	}

	public void setTextPosition(Point textPosition) {
		this.textPosition = textPosition;
	}

}
