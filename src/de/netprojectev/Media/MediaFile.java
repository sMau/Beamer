package de.netprojectev.Media;

import java.io.Serializable;
import java.util.Date;

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
	
	public String generateInfoString() {
		
		String info;
		
		String type;
		String current;
		String wasShowed;
		String showAt;
		
		if(this instanceof VideoFile) {
			type = "Video";
		} else if(this instanceof ImageFile) {
			type = "Image";
		} else if(this instanceof Themeslide) {
			type = "Themeslide";
		} else {
			type = "undefined";
		}
		
		if(this.getStatus().getIsCurrent()) {
			current = "yes";
		} else {
			current = "no";
		}
		
		if(this.getStatus().getWasShowed()) {
			wasShowed = "yes";
		} else {
			wasShowed = "no";
		}
		
		if(this.getStatus().getShowAt() != null) {
			if(this.getStatus().getShowAt().after(new Date())) {
				showAt = this.getStatus().getShowAt().toString();
			} else {
				showAt = "-";
			}
		} else {
			showAt = "-";
		}
		
		
		info =	"Name: " + name + "\n" +
				"Type: " + type + "\n" +
				"Priority: " + this.priority.getName() + " / " + this.priority.getMinutesToShow() + " min" + "\n" +
				"Already showed: " + wasShowed + "\n" +
				"Current: " + current + "\n" + 
				"Show at: " + showAt;
		
		return info;
	}

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
