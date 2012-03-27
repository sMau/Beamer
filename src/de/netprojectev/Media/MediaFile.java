package de.netprojectev.Media;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * 
 * Abstrakte Datenstruktur einer Medien Datei.
 * Unterklassen bilden konkrete Medien Dateien wie Bilder, Themenfolien oder Videos.
 *
 */
public abstract class MediaFile implements Serializable {

	private static final long serialVersionUID = -5495642573722802422L;
	protected String name;
	protected transient Status status;
	protected Priority priority;
	protected ImageIcon preview;

	protected MediaFile(String name, Priority priority) {

		
		this.name = name;
		this.status = new Status();
		this.priority = priority;
		this.preview = generatePreview();

	}

	protected abstract ImageIcon generatePreview();

	public abstract void show();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public void setPreview(ImageIcon preview) {
		this.preview = preview;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
