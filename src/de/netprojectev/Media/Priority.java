package de.netprojectev.Media;

import java.io.Serializable;

/**
 * 
 * Datenstruktur zur Verwaltung der Priorität einer Datei
 * Priorität bedeutet Anzeigedauer.
 *
 */
public class Priority implements Serializable {

	private static final long serialVersionUID = 4619160913893672095L;
	private String name;
	private int minutesToShow;

	public Priority(String name, int minutesToShow) {
		this.name = name;
		this.minutesToShow = minutesToShow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinutesToShow() {
		return minutesToShow;
	}

	public void setMinutesToShow(int minutesToShow) {
		this.minutesToShow = minutesToShow;
	}

}
