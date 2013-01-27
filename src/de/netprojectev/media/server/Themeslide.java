package de.netprojectev.media.server;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.netprojectev.misc.Constants;
import de.netprojectev.misc.Misc;

/**
 * Datastructure for a themeslide. It holds a theme object and a formatted styled text as JTextPane.
 * @author samu
 */
public class Themeslide extends ServerMediaFile {

	private static final long serialVersionUID = -1132804586378123305L;
	private static final Logger log = Misc.getLoggerAll(Themeslide.class.getName());

	private ImageFile imageFileRepresentation;	
	private long hashkey; // -1 means no png created yet
	private Theme theme;

	/**
	 * 
	 * @param name name in the manager
	 * @param priority priority initial priority
	 * @param theme theme object to specifiy background image
	 * @param text formatted styled text as JTextPane
	 * @param textPosition left and top margin for moving the textpane in the right position
	 */
	public Themeslide(final String name,final Priority priority, Theme theme,long hashKey) {
		super(name, priority);
		this.theme = theme;
		this.hashkey = hashKey;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000); //Wait until png is written to disk before reading image
				} catch (InterruptedException e) {
					log.log(Level.INFO, "interrupt exception", e);
				} 
				Themeslide.this.imageFileRepresentation = new ImageFile(name, Constants.SAVE_PATH + Constants.FOLDER_THEMESLIDE_CACHE + hashkey + ".png", priority);
			}
		}).start();
	}
	
	@Override
	public void show() {
		log.log(Level.INFO, "showing themeslide " + name);
		imageFileRepresentation.show();

	}
	
	/**
	 * creates a new {@link ImageFile} which holds the data necessary to access the cached image file version of the themeslide
	 */
	public void createNewImageFileRepresentation() {
		log.log(Level.INFO, "creating new image file object for themeslide " + name);
		this.imageFileRepresentation = new ImageFile(name, Constants.SAVE_PATH + Constants.FOLDER_THEMESLIDE_CACHE + hashkey + ".png", priority);
		
	}
	
	/**
	 * removes the cached image file from hard disk
	 */
	public void removeCacheFile() {
		File fileToDelete = new File(imageFileRepresentation.getPath());
		if(fileToDelete.exists()) {
			log.log(Level.INFO, "removing cache file from disk " + imageFileRepresentation.getPath());
			fileToDelete.delete();
		}
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}


	public long getHashkey() {
		return hashkey;
	}

	public ImageFile getImageFileRepresentation() {
		return imageFileRepresentation;
	}

	

}
