package de.netprojectev.Media;

import java.io.File;

import de.netprojectev.Misc.Constants;

/**
 * Datastructure for a themeslide. It holds a theme object and a formatted styled text as JTextPane.
 * @author samu
 */
public class Themeslide extends MediaFile {

	private static final long serialVersionUID = -1132804586378123305L;
	
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
				
		imageFileRepresentation.show();

	}
	
	public void createNewImageFileRepresentation() {
		this.imageFileRepresentation = new ImageFile(name, Constants.SAVE_PATH + Constants.FOLDER_THEMESLIDE_CACHE + hashkey + ".png", priority);
	}
	
	public void removeCacheFile() {
		File fileToDelete = new File(imageFileRepresentation.getPath());
		if(fileToDelete.exists()) {
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
