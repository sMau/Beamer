package de.netprojectev.Media;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;

/**
 * Datastructure for a themeslide. It holds a theme object and a formatted styled text as JTextPane.
 * @author samu
 */
public class Themeslide extends MediaFile {

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
	public Themeslide(String name,Priority priority, Theme theme,long hashKey) {
		super(name, priority);
		this.theme = theme;
		this.hashkey = hashKey;
		this.imageFileRepresentation = new ImageFile(name, Constants.SAVE_PATH + Constants.FOLDER_THEMESLIDE_CACHE + hashkey + ".png", priority);
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
