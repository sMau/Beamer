package de.netprojectev.media.server;

import java.io.Serializable;
import java.util.UUID;

import de.netprojectev.gui.display.DisplayMainFrame;
import de.netprojectev.media.MediaFile;
import de.netprojectev.mediahandler.DisplayDispatcher;

/**
 * abstract datastructure to deal, to deal with different media in an extension of this class
 * 
 * @author samu
 */
public abstract class ServerMediaFile extends MediaFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1218179453523031285L;

	protected transient DisplayMainFrame display;
	
	protected ServerMediaFile(String name, Priority priority) {
		super(name, priority, UUID.randomUUID());
		this.display = DisplayDispatcher.getInstance().getDisplayFrame();
		
	}
	
	/**
	 * show the media in the {@link DisplayMainFrame}
	 */
	public abstract void show();

	public DisplayMainFrame getDisplay() {
		return display;
	}

	public void setDisplay(DisplayMainFrame display) {
		this.display = display;
	}

}
