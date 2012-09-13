package de.netprojectev.Media;

import java.io.Serializable;
import java.util.Date;

import de.netprojectev.GUI.Display.DisplayMainFrame;
import de.netprojectev.MediaHandler.DisplayDispatcher;

/**
 * abstract datastructure to deal, to deal with different media in an extension of this class
 * 
 * @author samu
 */
public abstract class MediaFile implements Serializable {

	private static final long serialVersionUID = -5495642573722802422L;
	
	protected String name;
	protected transient Status status;
	protected Priority priority;
	protected Boolean corrupted;
	
	protected transient DisplayMainFrame display;
	
	/**
	 * 
	 * @param name name in the manager
	 * @param priority initial priority
	 */
	protected MediaFile(String name, Priority priority) {

		this.name = name;
		this.status = new Status();
		this.priority = priority;
		this.corrupted = false;
		this.display = DisplayDispatcher.getInstance().getDisplayFrame();

	}

	/**
	 * show the media in the {@link DisplayMainFrame}
	 */
	public abstract void show();
	
	/**
	 * Generates a formatted information string about the media file, about its attributes and current state.
	 * @return formatted info string
	 */
	public String generateInfoString() {
		
		String info;
		
		String type;
		String current;
		String wasShowed;
		String showAt;
		
		if(!corrupted) {
			if(this instanceof VideoFile) {
				type = "Video";
			} else if(this instanceof ImageFile) {
				type = "Image";
			} else if(this instanceof Themeslide) {
				
				type = "Themeslide / " + ((Themeslide) this).getTheme().getName();
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
			
		} else {
			info = "The file is corrupted or maybe was moved.";
		}
		return info;
	}

	public void resetPlayedState() {
		this.status.setWasShowed(false);
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

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public DisplayMainFrame getDisplay() {
		return display;
	}

	public void setDisplay(DisplayMainFrame display) {
		this.display = display;
	}

}
