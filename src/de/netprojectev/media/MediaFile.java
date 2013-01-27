package de.netprojectev.media;

import java.util.Date;
import java.util.UUID;

import de.netprojectev.media.server.ImageFile;
import de.netprojectev.media.server.Priority;
import de.netprojectev.media.server.Status;
import de.netprojectev.media.server.Themeslide;
import de.netprojectev.media.server.VideoFile;

public abstract class MediaFile {
	
	private final UUID id;
	protected String name;
	protected transient Status status;
	protected Priority priority;
	protected Boolean corrupted;
		
	/**
	 * 
	 * @param name name in the manager
	 * @param priority initial priority
	 */
	protected MediaFile(String name, Priority priority, UUID id) {
		this.id = id;
		this.name = name;
		this.status = new Status();
		this.priority = priority;
		this.corrupted = false;
	}
	
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
	
	@Override
	public boolean equals(Object other){
	    if (other == null || this == null) {
	    	return false;
	    } else if(other == this){
	    	return true;
	    } else if(other instanceof MediaFile) {
	    	return ((MediaFile) other).getId().equals(this.getId());
	    } else {
	    	return false;
	    }
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

	public UUID getId() {
		return id;
	}
	
}
